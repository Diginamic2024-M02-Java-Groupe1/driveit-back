package com.driveit.driveit._auth.service;

import com.driveit.driveit._auth.RefreshToken;
import com.driveit.driveit._auth.RefreshTokenRepository;
import com.driveit.driveit._exceptions.AppException;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final CollaboratorRepository collaboratorRepository;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, CollaboratorRepository collaboratorRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.collaboratorRepository = collaboratorRepository;
    }

    public RefreshToken createRefreshToken(int userId) {
        RefreshToken refreshToken = new RefreshToken();

        Collaborator collaborator = collaboratorRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id: " + userId));

        revokeAllUserTokens(collaborator);

        refreshToken.setCollaborator(collaborator);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void revokeAllUserTokens(Collaborator collaborator) {
        var validTokens = refreshTokenRepository.findAllByCollaborator(collaborator);
        if (validTokens.isEmpty()) return;

        validTokens.forEach(token -> token.setRevoked(true));
        refreshTokenRepository.saveAll(validTokens);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) throws AppException {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0 || token.isRevoked()) {
            refreshTokenRepository.delete(token);
            throw new AppException("Le refresh token a expiré ou a été révoqué. Veuillez vous reconnecter.");
        }
        return token;
    }

    @Scheduled(cron = "0 0 */6 * * *")
    @Transactional
    public void purgeExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens(Instant.now());
    }
}
