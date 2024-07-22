package com.driveit.driveit.vehicle;

import com.driveit.driveit._utils.Mapper;
import com.driveit.driveit.category.Category;
import com.driveit.driveit.category.CategoryDto;
import com.driveit.driveit.model.Model;
import com.driveit.driveit.model.ModelDto;
import com.driveit.driveit.model.ModelRepository;
import com.driveit.driveit.motorization.Motorization;
import com.driveit.driveit.motorization.MotorizationDto;
import com.driveit.driveit.reservationvehicle.ReservationVehicle;
import com.driveit.driveit.reservationvehicle.ReservationVehicleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
 *
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

    /**
     * Cette méthode sauvegarde un vehicule
     *
     * @param vehicle : le vehicule à ajouter
     * @return
     */
    @Transactional
    public ResponseEntity<String> insertVehicle(Vehicle vehicle) {

        if (vehicle.getModel() == null) {
            return ResponseEntity.badRequest().body("Le modèle doit être renseigné.");
        }
        if (vehicle.getMotorization() == null) {
            return ResponseEntity.badRequest().body("La motorisation doit être renseignée.");
        }
        if (vehicle.getCategory() == null) {
            return ResponseEntity.badRequest().body("La catégorie doit être renseignée.");
        }

        Vehicle vehicleExistant = vehicleRepository.findByRegistration(vehicle.getRegistration());

        if (vehicleExistant == null) {

            Model model = vehicle.getModel();
            Motorization motorization = vehicle.getMotorization();
            Category category = vehicle.getCategory();

            vehicleRepository.save(vehicle);
            return ResponseEntity.ok(vehicle.toString());
        } else {
            return ResponseEntity.badRequest().body("Un véhicule avec la même immatriculation existe déjà.");
        }

    }

    @Transactional
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles;
    }

    /**
     * Converti une liste de {@link Vehicle} en {@link VehicleDto}
     * @param vehicles liste de véhicule à convertir
     * @return liste de véhicule convertie
     */
    public List<VehicleDto> getAllVehiclesDto(List<Vehicle> vehicles) {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        for (Vehicle v : vehicles) {
            vehicleDtoList.add(Mapper.vehicleToDto(v));
        }
        return vehicleDtoList;
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
