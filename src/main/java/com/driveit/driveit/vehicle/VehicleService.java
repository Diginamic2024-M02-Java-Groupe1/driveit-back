package com.driveit.driveit.vehicle;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service de gestion des véhicules
 * Liste des opérations possibles :
 * - Récupérer tous les véhicules
 * - Récupérer un véhicule par son identifiant
 * - Sauvegarder un véhicule
 * - Supprimer un véhicule
 * @see Vehicle
 * @see VehicleRepository
 */
@Service
public class VehicleService {

    /**
     * Repository permettant d'effectuer des opérations sur les véhicules
     */
    private final VehicleRepository vehicleRepository;

    /**
     * Constructeur
     * @param vehicleRepository : le repository des véhicules
     */
    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Récupère tous les véhicules
     * @return la liste des véhicules
     */
    public Iterable<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    /**
     * Récupère un véhicule par son identifiant
     * @param id : l'identifiant du véhicule
     * @return le véhicule correspondant à {@code id}
     */
    public Vehicle getVehicleById(int id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    /**
     * Sauvegarde un véhicule
     * @param vehicle : le véhicule à sauvegarder
     */
    @Transactional
    public void saveVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    /**
     * Supprime un véhicule
     * @param vehicle : le véhicule à supprimer
     */
    @Transactional
    public void deleteVehicle(Vehicle vehicle) {
        vehicleRepository.delete(vehicle);
    }
}
