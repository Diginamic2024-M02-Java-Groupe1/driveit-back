package com.driveit.driveit.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Cette interface permet de gérer les véhicules dans la base de données.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> { }
