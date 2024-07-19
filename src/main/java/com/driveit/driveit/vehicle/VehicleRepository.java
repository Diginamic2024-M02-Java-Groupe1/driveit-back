package com.driveit.driveit.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Cette interface permet de gérer les véhicules dans la base de données.
 * Elle hérite de l'interface JpaRepository qui contient les méthodes CRUD.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    Vehicle findByRegistration(String registration);
}
