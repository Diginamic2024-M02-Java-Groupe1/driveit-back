package com.driveit.driveit._auth;


import com.driveit.driveit._auth.dto.ForgotPasswordRequest;
import com.driveit.driveit._auth.dto.LoginRequest;
import com.driveit.driveit._auth.dto.TokenResponse;
import com.driveit.driveit._auth.service.JwtTokenService;
import com.driveit.driveit._auth.service.RefreshTokenService;
import com.driveit.driveit._exceptions.AppException;
import com.driveit.driveit._exceptions.ConflictException;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Value("${admin.password-reset.expiration}")
    private long resetTokenExpirationMs;

    @Value("${client.url}")
    private String appUrl;

    @Value("${admin.password-reset.cooldown}")
    private long resetTokenCooldownMs;

    private final CollaboratorRepository collaboratorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordResetTokenRepository resetTokenRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public AuthService(CollaboratorRepository collaboratorRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenService jwtTokenService,
                       RefreshTokenService refreshTokenService,
                       PasswordResetTokenRepository resetTokenRepository,
                       JavaMailSender mailSender) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenService = refreshTokenService;
        this.resetTokenRepository = resetTokenRepository;
        this.collaboratorRepository = collaboratorRepository;
        this.mailSender = mailSender;
    }

    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        try {
            Collaborator utilisateur = collaboratorRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Identifiants invalides"));

            if (!passwordEncoder.matches(loginRequest.getPassword(), utilisateur.getPassword())) {
                throw new BadCredentialsException("Identifiants invalides");
            }

            String role = utilisateur.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            String accessToken = jwtTokenService.generateAccessToken(utilisateur.getEmail(), role, utilisateur.getId());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(utilisateur.getId());

            return new TokenResponse(
                    accessToken,
                    refreshToken.getToken(),
                    role,
                    utilisateur.getId(),
                    utilisateur.getLastName(),
                    utilisateur.getFirstName()
            );
        } catch (UsernameNotFoundException ex) {
            throw new BadCredentialsException("Identifiants invalides");
        }
    }

    @Transactional
    public void requestPasswordReset(ForgotPasswordRequest data) {

        collaboratorRepository.findByEmail(data.getEmail())
                .orElseThrow(() -> new RuntimeException("Aucun utilisateur trouvé avec ces données"));

        Optional<PasswordResetToken> recentToken = resetTokenRepository
                .findFirstByEmailOrderByExpiryDateDesc(data.getEmail());

        if (recentToken.isPresent()) {
            Instant tokenCreationTime = recentToken.get().getExpiryDate().minusMillis(resetTokenExpirationMs);
            if (tokenCreationTime.plusMillis(resetTokenCooldownMs).isAfter(Instant.now())) {
                throw new RuntimeException("Une demande de réinitialisation a déjà été envoyée récemment. " +
                        "Veuillez attendre avant de faire une nouvelle demande.");
            }
        }

        Optional<PasswordResetToken> existingToken = resetTokenRepository
                .findByEmailAndUsedFalseAndExpiryDateAfter(data.getEmail(), Instant.now());
        existingToken.ifPresent(token -> {
            token.setUsed(true);
            resetTokenRepository.save(token);
        });

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setEmail(data.getEmail());
        resetToken.setToken(UUID.randomUUID().toString());
        resetToken.setResetId(generateResetId());
        resetToken.setExpiryDate(Instant.now().plusMillis(resetTokenExpirationMs));
        resetTokenRepository.save(resetToken);

        sendResetEmail(data.getEmail(), resetToken.getResetId());

    }

    private void sendResetEmail(String email, String resetId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Avoline - Réinitialisation de votre Mot de Passe");
        String resetUrl = appUrl + "/auth/reset-password?id=" + resetId;

        message.setText("Bonjour, \n\n" +
                "Une demande de réinitialisation de mot de passe a été effectuée pour votre compte. " +
                "Veuillez cliquer sur le lien suivant pour réinitialiser votre mot de passe : \n\n" +
                resetUrl + "\n\n" +
                "Ce lien est valide pendant 1 heure. \n\n" +
                "Si vous n'avez pas demandé cette réinitialisation, ignorez cet email.\n\n" +
                "Cordialement,\n" +
                "L'équipe Avoline");

        mailSender.send(message);
    }

    @Transactional
    public void updateAdminPassword(String newPassword) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Collaborator admin = collaboratorRepository.findByEmail(email)
                .orElseThrow(() -> new ConflictException("Aucun utilisateur trouvé avec cet email"));

        if (!admin.getAuthorities().contains("ROLE_ADMIN")) {
            throw new ConflictException("Seul l'administrateur peut réinitialiser son mot de passe par cette méthode");
        }

        admin.setPassword(passwordEncoder.encode(newPassword));
        collaboratorRepository.save(admin);
    }

    @Transactional
    public void resetPasswordWithToken(String resetId, String newPassword) {

        PasswordResetToken resetToken = resetTokenRepository.findByResetId(resetId)
                .orElseThrow(() -> new RuntimeException("Code de réinitialisation invalide ou expiré"));

        if (resetToken.isUsed() || resetToken.getExpiryDate().isBefore(Instant.now())) {
            resetTokenRepository.delete(resetToken);
            throw new RuntimeException("Ce lien de réinitialisation a expiré ou a déjà été utilisé");
        }

        Collaborator admin = collaboratorRepository.findByEmail(resetToken.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        admin.setPassword(passwordEncoder.encode(newPassword));
        collaboratorRepository.save(admin);

        resetTokenRepository.delete(resetToken);
    }

    private String generateResetId() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
    }


   @Transactional
    public TokenResponse refreshToken(String refreshToken) {
        return refreshTokenService.findByToken(refreshToken)
                .map(token -> {
                    try {
                        return refreshTokenService.verifyExpiration(token);
                    } catch (AppException e) {
                        throw new BadCredentialsException("Token expiré ou révoqué: " + e.getMessage());
                    }
                })
                .map(RefreshToken::getCollaborator)
                .map(collaborator -> {
                    String accessToken = jwtTokenService.generateAccessToken(
                            collaborator.getEmail(),
                            collaborator.getAuthorities().getFirst().toString(),
                            collaborator.getId()
                    );

                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(collaborator.getId());

                    return new TokenResponse(
                            accessToken,
                            newRefreshToken.getToken(),
                            collaborator.getAuthorities().getFirst().toString(),
                            collaborator.getId(),
                            collaborator.getLastName(),
                            collaborator.getFirstName()
                    );
                })
                .orElseThrow(() -> new BadCredentialsException("Refresh token invalide"));
    }

    @Transactional
    public void logout(String refreshToken) {
        refreshTokenService.findByToken(refreshToken)
                .ifPresent(token -> {
                    token.setRevoked(true);
                    refreshTokenService.revokeAllUserTokens(token.getCollaborator());
                });
    }

    @Scheduled(cron = "0 0 */1 * * *")
    @Transactional
    public void purgeExpiredTokens() {
        resetTokenRepository.deleteByExpiryDateBefore(Instant.now());
        resetTokenRepository.deleteByUsedTrue();

    }
}
