package com.driveit.driveit.reservationvehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Répertoire des réservations de véhicules de service
 */
@Repository
public interface ReservationVehicleRepository extends JpaRepository<ReservationVehicle, Integer> {

    /**
     * Récupère les réservations de véhicules de service d'un collaborateur
     * @param collaboratorId l'identifiant du collaborateur
     * @return la liste des réservations de véhicules de service
     */
    List<ReservationVehicle> findByCollaboratorId(int collaboratorId);

    /**
     * Vérifie si un véhicule est disponible entre deux dates
     * @param vehicleId l'identifiant du véhicule
     * @param startDateTime la date de début
     * @return true si le véhicule est disponible, false sinon
     */
    @Query("SELECT CASE WHEN COUNT(rv) > 0 THEN FALSE ELSE TRUE END FROM ReservationVehicle rv WHERE rv.vehicle.id = ?1 AND ?2 BETWEEN rv.startDate AND rv.endDate")
    boolean isVehicleAvailableBetweenDateTimes(int vehicleId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    /**
     * Passée : filtre les réservations du collaborateur ou la date est postérieure à la date de fin
     * @param collaboratorId l'identifiant du collaborateur
     * @param date date du jour
     * @return la liste des réservations de véhicule de service du collaborateur
     */
    List<ReservationVehicle> findByCollaboratorIdAndEndDateLessThanEqual(int collaboratorId, LocalDateTime date);

    /**
     * En cours : filtre les réservations du collaborateur ou la date est comprise entre la date de début et la date de fin
     * @param collaboratorId l'identifiant du collaborateur
     * @param date date du jour
     * @return la liste des réservations de véhicule de service du collaborateur
     */
    List<ReservationVehicle> findByCollaboratorIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(int collaboratorId, LocalDateTime date,LocalDateTime sameDate);

    /**
     * À venir : filtre les réservations du collaborateur ou la date est antérieure à la date de début
     * @param collaboratorId l'identifiant du collaborateur
     * @param date date du jour
     * @return la liste des réservations de véhicule de service du collaborateur
     */
    List<ReservationVehicle> findByCollaboratorIdAndStartDateGreaterThanEqual(int collaboratorId, LocalDateTime date);
}
