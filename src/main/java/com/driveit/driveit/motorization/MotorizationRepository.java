package com.driveit.driveit.motorization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Cette interface permet de gérer les motorisations dans la base de données.
 */
@Repository
public interface MotorizationRepository extends JpaRepository<Motorization, Integer> {

    Optional<Motorization> findByName(String name);

}
