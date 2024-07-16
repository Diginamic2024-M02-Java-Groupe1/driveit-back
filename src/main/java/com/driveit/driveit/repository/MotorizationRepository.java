package com.driveit.driveit.repository;

import com.driveit.driveit.entity.Motorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Cette interface permet de gérer les motorisations dans la base de données.
 */
@Repository
public interface MotorizationRepository extends JpaRepository<Motorization, Integer> {
}
