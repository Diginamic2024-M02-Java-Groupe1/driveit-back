package com.driveit.driveit.reservationvehicle;

import com.driveit.driveit._exceptions.AppException;
import com.driveit.driveit._utils.Converter;
import com.driveit.driveit._utils.Mapper;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorRepository;
import com.driveit.driveit.vehicle.Vehicle;
import com.driveit.driveit.vehicle.VehicleDto;
import com.driveit.driveit.vehicle.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationVehicleService {

    private final ReservationVehicleRepository reservationVehicleRepository;

    private final CollaboratorRepository collaboratorRepository;

    private final VehicleRepository vehicleRepository;

    @Autowired
    public ReservationVehicleService(ReservationVehicleRepository reservationVehicleRepository, CollaboratorRepository collaboratorRepository, VehicleRepository vehicleRepository) {
        this.reservationVehicleRepository = reservationVehicleRepository;
        this.collaboratorRepository = collaboratorRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<ReservationVehicle> getAllReservationVehicles() {
        return reservationVehicleRepository.findAll();
    }

    /**
     * Méthode faisant appel à la base de donnée pour vérifier la disponibilité d'un véhicule de service
     *
     * @param vehicleId identifiant du véhicule
     * @param from date et heure de début de location
     * @return oui ou non en fonction de la disponiblité
     */
    public boolean isAvailableBetweenDateTimes(int vehicleId, LocalDateTime from) {
        return reservationVehicleRepository.isVehicleAvailableBetweenDateTimes(vehicleId, from);
    }

    /**
     * Méthode permettant de vérifier la disponibilité des véhicules de service à partir d'une date donnée
     *
     * @param reserveVehicle récupère les informations nécessaires à la réservation d'un véhicule de service
     * @return la liste des véhicules disponibles à partir de la date et heure de début souhaité (si la date de début nest pas comprise entre la dateHeureDebut et dateHeureFin de la réservation)
     */
    public List<VehicleDto> getAvailableService(ReservationVehicleDto reserveVehicle) {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        List<Vehicle> vehicles = vehicleRepository.findAllAvailableVehicles();
        for (Vehicle v : vehicles) {
            if(isAvailableBetweenDateTimes(v.getId(),
                    Converter.stringToLocalDateTime(reserveVehicle.dateStart(),reserveVehicle.timeStart()))) {
                vehicleDtoList.add(Mapper.vehicleToDto(v));
            }
        }
        return vehicleDtoList;
    }

    /**
     * Méthode utiliser pour réserver un véhicule de service entre une date et heure de début et une date et heure de fin
     *
     * @param userId identifiant de l'utilisateur
     * @param reserveVehicle dto des données utiles pour enregistrer la location d'un véhicule de service
     * @return une chaine de caractère qui indique si la réservation a été effectuée ou non
     */
    public String reserveVehicle(int userId, ReservationVehicleDto reserveVehicle) throws AppException {
        LocalDateTime from = Converter.stringToLocalDateTime(reserveVehicle.dateStart(),reserveVehicle.timeStart());
        if(isAvailableBetweenDateTimes(reserveVehicle.vehicleDto().getId(), from)) {
            LocalDateTime to = Converter.stringToLocalDateTime(reserveVehicle.dateEnd(),reserveVehicle.timeEnd());
            Collaborator collaborator = collaboratorRepository.findById(userId).get();
            Vehicle vehicleToBook = vehicleRepository.findByRegistration(reserveVehicle.vehicleDto().getRegistration());
            ReservationVehicle reservation = new ReservationVehicle(from,to,vehicleToBook,collaborator);
            save(reservation);
            return "Réservation effectuée";
        }
        throw new AppException("La réservation n'a pu avoir lieu, le véhicule n'est probablement plus disponible");

    }

    @Transactional
    public void save(ReservationVehicle reservationVehicle) {
        reservationVehicleRepository.save(reservationVehicle);
    }

    @Transactional
    public void delete(int id) throws AppException {
        if (!reservationVehicleRepository.existsById(id)) { throw new AppException("La réservation est introuvable");}
        reservationVehicleRepository.deleteById(id);
    }

}
