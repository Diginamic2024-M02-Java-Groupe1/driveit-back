package com.driveit.driveit.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Cette interface permet de gérer les véhicules dans la base de données.
 * Elle hérite de l'interface JpaRepository qui contient les méthodes CRUD.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    @Query("SELECT v FROM Vehicle v WHERE v.isService = true AND v.status = 'AVAILABLE'")
    List<Vehicle> findAllAvailableVehicles();
}
