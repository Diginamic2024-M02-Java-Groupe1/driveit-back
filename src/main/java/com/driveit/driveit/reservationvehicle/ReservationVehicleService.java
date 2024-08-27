package com.driveit.driveit.reservationvehicle;

import com.driveit.driveit._exceptions.AppException;
import com.driveit.driveit._utils.Mapper;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorRepository;
import com.driveit.driveit.collaborator.CollaboratorService;
import com.driveit.driveit.vehicle.StatusVehicle;
import com.driveit.driveit.vehicle.Vehicle;
import com.driveit.driveit.vehicle.VehicleDto;
import com.driveit.driveit.vehicle.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    private final CollaboratorService collaboratorService;

    /**
     * Répertoire des véhicules
     */
    private final VehicleRepository vehicleRepository;

    /**
     * Constructeur
     *
     * @param reservationVehicleRepository répertoire de réservation des véhicules de service
     * @param collaboratorRepository       répertoire des collaborateurs
     * @param vehicleRepository            répertoire des véhicules
     */
    @Autowired
    public ReservationVehicleService(ReservationVehicleRepository reservationVehicleRepository, CollaboratorRepository collaboratorRepository, VehicleRepository vehicleRepository, CollaboratorService collaboratorService) {
        this.reservationVehicleRepository = reservationVehicleRepository;
        this.collaboratorRepository = collaboratorRepository;
        this.vehicleRepository = vehicleRepository;
        this.collaboratorService = collaboratorService;
    }

    /**
     * Méthode permettant de récupérer les réservations de véhicules de service d'un collaborateur
     *
     * @param collaboratorId identifiant du collaborateur
     * @param status         statut de la réservation
     * @return la liste des réservations de véhicules de service
     * @throws AppException retourne l'erreur sur la non récupération
     */
    public List<VehiculeServiceReservationDto> getMyReservationVehicleService(String status) throws AppException {
        List<VehiculeServiceReservationDto> reservationVehiclesDto = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<ReservationVehicle> reservationVehicles = new ArrayList<>();
        int collaboratorId = collaboratorService.getAuthenticatedCollaborator().id();
        if (status.equals(StatusFilter.PAST.toString().toLowerCase())) {
            reservationVehicles.addAll(reservationVehicleRepository.findByCollaboratorIdAndEndDateLessThanEqual(collaboratorId, currentDateTime));
        } else if (status.equals(StatusFilter.IN_PROGRESS.toString().toLowerCase())) {
            reservationVehicles.addAll(reservationVehicleRepository.findByCollaboratorIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(collaboratorId, currentDateTime, currentDateTime));
        } else if (status.equals(StatusFilter.INCOMING.toString().toLowerCase())) {
            reservationVehicles.addAll(reservationVehicleRepository.findByCollaboratorIdAndStartDateGreaterThanEqual(collaboratorId, currentDateTime));
        } else if (status.equals("all") || status.isEmpty()) {
            reservationVehicles.addAll(reservationVehicleRepository.findByCollaboratorId(collaboratorId));
        } else {
            throw new AppException("Le statut du filtre n'est pas répertorié");
        }

        for (ReservationVehicle reservationVehicle : reservationVehicles) {
            reservationVehiclesDto.add(Mapper.reservationVehicleToDto(reservationVehicle));
        }
        return reservationVehiclesDto;
    }

    public List<VehiculeServiceReservationDto> getAllReservationsForThisVehicle(int vehicleId) throws AppException {
        if (vehicleRepository.findById(vehicleId).isEmpty()) {
            throw new AppException("Le véhicule n'est pas trouvé");
        }
        if (!vehicleRepository.findServiceVehicleById(vehicleId).getStatus().equals(StatusVehicle.AVAILABLE)) {
            throw new AppException("Le véhicule ne peut pas être réservé, son statut est indisponible");
        }

        List<VehiculeServiceReservationDto> reservationVehiclesDto = new ArrayList<>();
        List<ReservationVehicle> reservationVehicles = reservationVehicleRepository.findByVehicleIdAndEndDateGreaterThanEqual(vehicleId, LocalDateTime.now());

        for (ReservationVehicle reservationVehicle : reservationVehicles) {
            if(reservationVehicle.getCollaborator().getId() != collaboratorService.getAuthenticatedCollaborator().id()){
                reservationVehiclesDto.add(Mapper.reservationVehicleToDto(reservationVehicle));
            }
        }
        return reservationVehiclesDto;
    }

    /**
     * Méthode faisant appel à la base de donnée pour vérifier la disponibilité d'un véhicule de service
     *
     * @param vehicleId identifiant du véhicule
     * @param from      date et heure de début de location
     * @return oui ou non en fonction de la disponiblité
     */
    public boolean isAvailableBetweenDateTimes(int vehicleId, LocalDateTime from, LocalDateTime to) {
        return reservationVehicleRepository.isVehicleAvailableBetweenDateTimes(vehicleId, from, to);
    }

    /**
     * Méthode permettant de vérifier la disponibilité des véhicules de service à partir d'une date donnée
     *
     * @param dateStart récupère les informations nécessaires à la réservation d'un véhicule de service
     * @return la liste des véhicules disponibles à partir de la date et heure de début souhaité (si la date de début nest pas comprise entre la dateHeureDebut et dateHeureFin de la réservation)
     */
    public List<VehicleDto> getAvailableService(LocalDateTime dateStart, LocalDateTime dateEnd) {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        List<Vehicle> vehicles = vehicleRepository.findAllAvailableVehicles();
        for (Vehicle v : vehicles) {
            if (isAvailableBetweenDateTimes(v.getId(),
                    dateStart, dateEnd)) {
                vehicleDtoList.add(Mapper.vehicleToDto(v));
            }
        }
        System.out.println(vehicleDtoList);
        return vehicleDtoList;
    }

    /**
     * Méthode utiliser pour réserver un véhicule de service entre une date et heure de début et une date et heure de fin
     *
     * @param reserveVehicle dto des données utiles pour enregistrer la location d'un véhicule de service
     * @return une chaine de caractère qui indique si la réservation a été effectuée ou non
     */
    @Transactional
    public String reserveVehicle(ReservationVehicleDto reserveVehicle) throws AppException {

        if (reserveVehicle == null) {
            throw new AppException("Les informations de réservation sont manquantes.");
        }

        Vehicle vehicleToBook = vehicleRepository.findByRegistration(reserveVehicle.vehicle().getRegistration());
        if (vehicleToBook == null) {
            throw new AppException("Le véhicule avec l'immatriculation " + reserveVehicle.vehicle().getRegistration() + " n'a pas été trouvé.");
        }

        if (!isAvailableBetweenDateTimes(vehicleToBook.getId(), reserveVehicle.dateTimeStart(), reserveVehicle.dateTimeEnd())) {
            throw new AppException("La réservation n'a pu avoir lieu, le véhicule n'est probablement plus disponible");
        }
        int collaboratorId = collaboratorService.getAuthenticatedCollaborator().id();
        Collaborator collaborator = collaboratorRepository.findById(collaboratorId).orElseThrow(() -> new AppException("Utilisateur non trouvé !"));

        ReservationVehicle reservation = new ReservationVehicle(reserveVehicle.dateTimeStart(), reserveVehicle.dateTimeEnd(), vehicleToBook, collaborator);
        reservationVehicleRepository.save(reservation);

        return "Réservation effectuée";


    }

    /**
     * Méthode permettant de mettre à jour une réservation de véhicule de service
     *
     * @param reserveId             identifiant de la réservation
     * @param reservationVehicleDto données de la réservation
     * @return une chaine de caractère affichant la réussite de la modification
     */
    @Transactional
    public String updateReservationVehicle(int reserveId, ReservationVehicleDto reservationVehicleDto) throws AppException {
        ReservationVehicle reservationFounded = reservationVehicleRepository.findById(reserveId).orElse(null);

        if (reservationVehicleDto == null || reservationFounded == null) {
            throw new AppException("Les informations de réservation sont manquantes.");
        }

        if(!reservationFounded.getVehicle().getStatus().equals(StatusVehicle.AVAILABLE)){
            throw new AppException("Le véhicule n'est plus disponible à la location");
        }

        LocalDateTime from = reservationVehicleDto.dateTimeStart();
        LocalDateTime to = reservationVehicleDto.dateTimeEnd();

        List<ReservationVehicle> rv = reservationVehicleRepository.findReservationsBetweenDates(reservationFounded.getVehicle().getId(), from, to);
        if(!rv.isEmpty()){
            for(ReservationVehicle r : rv){
                if(r.getId() != reserveId){
                    throw new AppException("Le véhicule est déjà réservé à cette période");
                }
            }
        }

        reservationFounded.setStartDate(from);
        reservationFounded.setEndDate(reservationVehicleDto.dateTimeEnd());
        reservationVehicleRepository.save(reservationFounded);
        return "La modification a été effectuée";

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
