package com.driveit.driveit.vehicle;

import com.driveit.driveit.category.CategoryDto;
import com.driveit.driveit.model.ModelDto;
import com.driveit.driveit.motorization.MotorizationDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
     *
     * @param vehicleRepository : le repository des vehicules
     */
    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<VehicleDto> getAllVehiclesDto(List<Vehicle> vehicles) {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        for (Vehicle v : vehicles) {
            VehicleDto dto = new VehicleDto(v.getRegistration(),v.getNumberOfSeats(),v.getService(),v.getUrl(),v.getEmission(),v.getStatus(), new MotorizationDto(v.getMotorization().getId(),v.getMotorization().getName()),new ModelDto(v.getModel().getId(),v.getModel().getName()),new CategoryDto(v.getCategory().getId(),v.getCategory().getName()));
            vehicleDtoList.add(dto);
        }
        return vehicleDtoList;
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
        v.setModel(vehicle.getModel());
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
