package com.driveit.driveit._auth;

import com.driveit.driveit._auth.dto.LoginRequest;
import com.driveit.driveit._auth.dto.TokenResponse;
import com.driveit.driveit._auth.service.JwtTokenService;
import com.driveit.driveit._auth.service.RefreshTokenService;
import com.driveit.driveit._email.EmailService;
import com.driveit.driveit._exceptions.AppException;
import com.driveit.driveit._utils.Mapper;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorDto;
import com.driveit.driveit.collaborator.CollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
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
    private final EmailService emailService;

    @Autowired
    public AuthService(CollaboratorRepository collaboratorRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenService jwtTokenService,
                       RefreshTokenService refreshTokenService,
                       PasswordResetTokenRepository resetTokenRepository,
                       EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenService = refreshTokenService;
        this.resetTokenRepository = resetTokenRepository;
        this.collaboratorRepository = collaboratorRepository;
        this.emailService = emailService;
    }

    @Transactional
    public CollaboratorDto register(RegisterUserDto registerUserDto) throws AppException {
        Collaborator user = new Collaborator(
                registerUserDto.email(),
                passwordEncoder.encode(registerUserDto.password()),
                registerUserDto.firstName(),
                registerUserDto.lastName()
        );
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpirationDate(LocalDateTime.now().plusMinutes(15));
        user.setEnabled(false);
        sendVerificationEmail(user);
        return Mapper.collaboratorToDto(collaboratorRepository.save(user));
    }

    public void verifyUser(VerifyUserDto verifyUserDto) throws AppException {
        Optional<Collaborator> optionalUser = collaboratorRepository.findByEmail(verifyUserDto.email());
        if(optionalUser.isPresent()){
            Collaborator user = optionalUser.get();
            if(user.getVerificationCodeExpirationDate().isBefore(LocalDateTime.now())) {
                throw new AppException("Verification code expired");
            }
            if (user.getVerificationCode().equals(verifyUserDto.verificationCode())) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationCodeExpirationDate(null);
                collaboratorRepository.save(user);
            } else {
                throw new AppException("Invalid verification code");
            }
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public void resendVerificationCode(String email) throws AppException {
        System.out.println(email);
        Optional<Collaborator> optionalUser = collaboratorRepository.findByEmail(email);
        System.out.println(optionalUser);
        if(optionalUser.isPresent()){
            Collaborator user = optionalUser.get();
            if(user.isEnabled()) {
                throw new AppException("Account already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpirationDate(LocalDateTime.now().plusMinutes(15));
            sendVerificationEmail(user);
            collaboratorRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public void sendVerificationEmail(Collaborator user) throws AppException {
        String subject = "DriveIt - Account Verification";
        String verificationCode = user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif; text-align: center;\">"
                + "<h2 style=\"color: #4CAF50;\">Welcome to DriveIt!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<p style=\"font-size: 24px; font-weight: bold; color: #333;\">" + verificationCode + "</p>"
                + "<p style=\"font-size: 14px; color: #777;\">This code will expire in 15 minutes.</p>"
                + "</body>"
                + "</html>";

        try{
            emailService.sendEmail(user.getEmail(), subject, htmlMessage);
        } catch (Exception e) {
            throw new AppException("Failed to send verification email");
        }
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
                            collaborator.getAuthorities().stream().findFirst().orElseThrow().toString(),
                            collaborator.getId()
                    );

                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(collaborator.getId());

                    return new TokenResponse(
                            accessToken,
                            newRefreshToken.getToken(),
                            collaborator.getAuthorities().stream().findFirst().orElseThrow().toString(),
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

    @Scheduled(cron = "0 0 */1 * * *")
    @Transactional
    public void purgeExpiredTokens() {
        resetTokenRepository.deleteByExpiryDateBefore(Instant.now());
        resetTokenRepository.deleteByUsedTrue();

    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}


