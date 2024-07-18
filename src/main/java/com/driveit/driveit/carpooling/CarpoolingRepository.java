package com.driveit.driveit.carpooling;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Cette interface permet de gérer les covoiturages dans la base de données.
 */
@Repository
public interface CarpoolingRepository extends JpaRepository<Carpooling, Integer> {
}
