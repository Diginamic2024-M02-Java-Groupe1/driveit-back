package com.driveit.driveit.reservationvehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationVehicleRepository extends JpaRepository<ReservationVehicle, Integer> {

    @Query("SELECT CASE WHEN COUNT(rv) > 0 THEN FALSE ELSE TRUE END FROM ReservationVehicle rv WHERE rv.vehicle.id = ?1 AND ?2 BETWEEN rv.startDate AND rv.endDate")
    boolean isVehicleAvailableBetweenDateTimes(int vehicleId, LocalDateTime startDateTime);

    List<ReservationVehicle> findByCollaboratorId(int collaboratorId);

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
