package com.driveit.driveit.vehicle;

import com.driveit.driveit._utils.Converter;
import com.driveit.driveit._utils.Mapper;
import com.driveit.driveit.category.CategoryDto;
import com.driveit.driveit.model.ModelDto;
import com.driveit.driveit.model.ModelRepository;
import com.driveit.driveit.motorization.MotorizationDto;
import com.driveit.driveit.reservationvehicle.ReservationVehicle;
import com.driveit.driveit.reservationvehicle.ReservationVehicleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;


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
    private final ReservationVehicleService reservationVehicleService;

    /**
     * Constructeur
     *
     * @param vehicleRepository : le repository des vehicules
     */
    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, ReservationVehicleService reservationVehicleService) {
        this.vehicleRepository = vehicleRepository;
        this.reservationVehicleService = reservationVehicleService;
    }

    /**
     * Cette méthode sauvegarde un vehicule
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

    /**
     * Méthode permettant de vérifier la disponibilité des véhicules de service à partir d'une date donnée
     * @param dateStart date de début au format string
     * @param timeStart heure de début au format string
     * @param dateEnd date de fin au format string
     * @param timeEnd heure de fin au format string
     * @return la liste des véhicules disponibles à partir de la date et heure de début souhaité (si la date de début nest pas comprise entre la dateHeureDebut et dateHeureFin de la réservation)
     */
    public List<VehicleDto> getAvailableService(String dateStart,String timeStart,String dateEnd,String timeEnd) {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        List<Vehicle> vehicles = vehicleRepository.findAllAvailableVehicles();
        for (Vehicle v : vehicles) {
            if(!reservationVehicleService.isAvailableBetweenDateTimes(v.getId(),
                    Converter.stringToLocalDateTime(dateStart,timeStart))) {
                vehicleDtoList.add(Mapper.vehicleToDto(v));
            }
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
