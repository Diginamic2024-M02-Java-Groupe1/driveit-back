package com.driveit.driveit._auth;

import com.driveit.driveit.collaborator.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByCollaborator(Collaborator collaborator);

    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.expiryDate < ?1 OR r.revoked = true")
    void deleteExpiredTokens(Instant now);

    void deleteByCollaborator(Collaborator collaborator);
}
