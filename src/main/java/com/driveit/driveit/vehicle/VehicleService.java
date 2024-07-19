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
     * Cette méthode sauvegarder un vehicule
     *
     * @param vehicle : le vehicule à ajouter
     */
    @Transactional
    public void insertVehicle(Vehicle vehicle) {
        Vehicle v = vehicleRepository.findAll().stream().filter(v1 -> v1.getRegistration().equals(vehicle.getRegistration())).findFirst().orElse(null);
        if (v != null) {
            throw new IllegalArgumentException("Vehicle with registration " + vehicle.getRegistration() + " already exists");
        }
        vehicleRepository.save(vehicle);
    }

    @Transactional
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles;
    }

    @Transactional
    public void updateVehicle(int id, Vehicle vehicle) {
        Vehicle v = vehicleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid vehicle Id:" + id));
        v.setRegistration(vehicle.getRegistration());
        v.setNumberOfSeats(vehicle.getNumberOfSeats());
        v.setService(vehicle.getService());
        v.setUrl(vehicle.getUrl());
        v.setEmission(vehicle.getEmission());
        v.setStatus(vehicle.getStatus());
        v.setCollaborators(vehicle.getCollaborators());
        v.setMotorization(vehicle.getMotorization());
        v.setBrand(vehicle.getBrand());
        v.setCategory(vehicle.getCategory());

        vehicleRepository.save(v);
    }

    /**
     * Cette méthode permet de supprimer un vehicule
     *
     * @param id : le vehicule à supprimer
     */
    @Transactional
    public void deleteVehicle(int id) {
        vehicleRepository.deleteById(id);
    }
}
