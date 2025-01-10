package com.driveit.driveit.collaborator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Cette interface permet de gérer les collaborateurs dans la base de données.
 */
@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Integer> {
    Optional<Collaborator> findByEmail(String email);
    Optional<Collaborator> findByVerificationCode(String verificationCode);
}
