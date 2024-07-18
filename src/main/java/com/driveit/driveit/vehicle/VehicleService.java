package com.driveit.driveit.vehicle;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
     * @param vehicleRepository : le repository des vehicules
     */
    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Cette méthode permet de supprimer un vehicule
     * @param vehicle : le vehicule à supprimer
     */
    @Transactional
    public void deleteVehicle(Vehicle vehicle) {
        vehicleRepository.delete(vehicle);
    }

    /**
     * Cette méthode sauvegarder un vehicule
     * @param vehicle : le vehicule à ajouter
     */
    @Transactional
    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    /**
     * Cette méthode permet de récupérer un vehicule par son ID
     * @param id : l'identifiant du vehicule
     * @return le vehicule correspondant à l'ID
     */
    public Vehicle getVehicleById(int id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    /**
     * Cette méthode permet de récupérer la liste des vehicules
     * @return la liste des vehicules
     */
    public Iterable<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}
