package com.driveit.driveit.vehicle;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Cette classe est un service qui gère les opérations sur les vehicules
 * Elle est utilisée pour supprimer ou ajouter un vehicule etc ...
 *
 * @see Vehicle
 * @see VehicleRepository
 */
@Service
public class VehicleService {

    /**
     * Repository permettant d'effectuer des opérations sur les vehicules
     */
    private final VehicleRepository vehicleRepository;

    /**
     * Constructeur
     *
     * @param vehicleRepository : le repository des vehicules
     */
    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Cette méthode permet de supprimer un vehicule
     *
     * @param vehicle : le vehicule à supprimer
     */
    @Transactional
    public void deleteVehicle(Vehicle vehicle) {
        vehicleRepository.delete(vehicle);
    }

    /**
     * Cette méthode sauvegarder un vehicule
     *
     * @param vehicle : le vehicule à ajouter
     */
    @Transactional
    public void insertVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    @Transactional
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles;
    }

    @Transactional
    public Vehicle updateVehicle(int id, Vehicle vehicle) { // à compléter pour mettre à jour les données

        return vehicleRepository.save(vehicle);
    }
}
