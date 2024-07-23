package com.driveit.driveit.motorization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Cette interface permet de gérer les motorisations dans la base de données.
 */
@Repository
public interface MotorizationRepository extends JpaRepository<Motorization, Integer> {
    Motorization findByName(String name);

    Motorization findFirstByName(String name);
}
