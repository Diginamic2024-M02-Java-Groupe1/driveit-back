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
import net.datafaker.providers.base.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service qui gère les opérations de réservation des véhicules de service
 */
@Service
public class ReservationVehicleService {

    /**
     * Répertoire de réservation des véhicules de service
     */
    private final ReservationVehicleRepository reservationVehicleRepository;

    /**
     * Répertoire des collaborateurs
     */
    private final CollaboratorRepository collaboratorRepository;

    /**
     * Répertoire des véhicules
     */
    private final VehicleRepository vehicleRepository;

    /**
     * Constructeur
     *
     * @param reservationVehicleRepository répertoire de réservation des véhicules de service
     * @param collaboratorRepository répertoire des collaborateurs
     * @param vehicleRepository répertoire des véhicules
     */
    @Autowired
    public ReservationVehicleService(ReservationVehicleRepository reservationVehicleRepository, CollaboratorRepository collaboratorRepository, VehicleRepository vehicleRepository) {
        this.reservationVehicleRepository = reservationVehicleRepository;
        this.collaboratorRepository = collaboratorRepository;
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Méthode permettant de récupérer les réservations de véhicules de service d'un collaborateur
     *
     * @param collaboratorId identifiant du collaborateur
     * @param status statut de la réservation
     * @return la liste des réservations de véhicules de service
     * @throws AppException retourne l'erreur sur la non récupération
     */
    public List<VehiculeServiceReservationDto> getMyReservationVehicleService(int collaboratorId,String status) throws AppException {
        List<VehiculeServiceReservationDto> reservationVehicleDtos = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<ReservationVehicle> reservationVehicles = new ArrayList<>();

        if(status.equals(StatusFilter.PAST.toString().toLowerCase())){
           reservationVehicles.addAll(reservationVehicleRepository.findByCollaboratorIdAndEndDateLessThanEqual(collaboratorId,currentDateTime));
        }else if(status.equals(StatusFilter.IN_PROGRESS.toString().toLowerCase())){
            reservationVehicles.addAll(reservationVehicleRepository.findByCollaboratorIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(collaboratorId,currentDateTime,currentDateTime));
        }else if(status.equals(StatusFilter.INCOMING.toString().toLowerCase())){
            reservationVehicles.addAll(reservationVehicleRepository.findByCollaboratorIdAndStartDateGreaterThanEqual(collaboratorId,currentDateTime));
        }else{
            throw new AppException("Le statut du filtre n'est pas répertorié");
        }

        for (ReservationVehicle reservationVehicle : reservationVehicles) {
            reservationVehicleDtos.add(Mapper.reservationVehicleToDto(reservationVehicle));
        }
        return reservationVehicleDtos;
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
            if (isAvailableBetweenDateTimes(v.getId(),
                    Converter.stringToLocalDateTime(reserveVehicle.dateStart(), reserveVehicle.timeStart()))) {
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
    @Transactional
    public String reserveVehicle(int userId, ReservationVehicleDto reserveVehicle) throws AppException {
        LocalDateTime from = Converter.stringToLocalDateTime(reserveVehicle.dateStart(), reserveVehicle.timeStart());
        if (!isAvailableBetweenDateTimes(reserveVehicle.vehicleDto().getId(), from)) {
            throw new AppException("La réservation n'a pu avoir lieu, le véhicule n'est probablement plus disponible");
        }
        LocalDateTime to = Converter.stringToLocalDateTime(reserveVehicle.dateEnd(), reserveVehicle.timeEnd());
        Collaborator collaborator = collaboratorRepository.findById(userId).orElseThrow(()-> new AppException("Utilisateur non trouvé !"));
        Vehicle vehicleToBook = vehicleRepository.findByRegistration(reserveVehicle.vehicleDto().getRegistration());
        ReservationVehicle reservation = new ReservationVehicle(from, to, vehicleToBook, collaborator);
        reservationVehicleRepository.save(reservation);

        return "Réservation effectuée";


    }

    /**
     * Méthode permettant de mettre à jour une réservation de véhicule de service
     *
     * @param id                 identifiant de la réservation
     * @param reservationVehicleDto données de la réservation
     * @return une chaine de caractère affichant la réussite de la modification
     */
    @Transactional
    public String updateReservationVehicle(int id, ReservationVehicleDto reservationVehicleDto) throws AppException {
        ReservationVehicle reserveFounded = reservationVehicleRepository.findById(id).orElse(null);
        if (reserveFounded == null) {
            throw new AppException("La réservation n'est pas trouvée");
        }
        LocalDateTime from = Converter.stringToLocalDateTime(reservationVehicleDto.dateStart(), reservationVehicleDto.timeStart());
        if (reserveFounded.getCollaborator().getId() == id && !isAvailableBetweenDateTimes(reservationVehicleDto.vehicleDto().getId(), from)) {
            throw new AppException("La modification n'a pu avoir lieu, le véhicule n'est probablement plus disponible");
        }
        reserveFounded.setStartDate(from);
        reserveFounded.setEndDate(Converter.stringToLocalDateTime(reservationVehicleDto.dateEnd(), reservationVehicleDto.timeEnd()));
        reservationVehicleRepository.save(reserveFounded);
        return "La réservation a été effectuée";
    }

    /**
     * Méthode permettant de supprimer une réservation de véhicule de service
     *
     * @param id identifiant de la réservation
     * @throws AppException retourne l'erreur sur la non suppression
     */
    @Transactional
    public void delete(int id) throws AppException {
        if (!reservationVehicleRepository.existsById(id)) {
            throw new AppException("La réservation est introuvable");
        }
        reservationVehicleRepository.deleteById(id);
    }
}
