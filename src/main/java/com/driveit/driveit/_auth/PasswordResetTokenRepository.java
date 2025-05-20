package com.driveit.driveit._auth;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByResetId(String resetId);

    Optional<PasswordResetToken> findByEmailAndUsedFalseAndExpiryDateAfter(String email, Instant now);

    Optional<PasswordResetToken> findFirstByEmailOrderByExpiryDateDesc(String email);

    void deleteByExpiryDateBefore(Instant now);

    void deleteByUsedTrue();
}
