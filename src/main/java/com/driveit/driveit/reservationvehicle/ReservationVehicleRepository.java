package com.driveit.driveit.reservationvehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservationVehicleRepository extends JpaRepository<ReservationVehicle, Integer> {

    @Query("SELECT CASE WHEN COUNT(rv) > 0 THEN TRUE ELSE FALSE END FROM ReservationVehicle rv WHERE rv.vehicle.id = ?1 AND ?2 BETWEEN rv.startDate AND rv.endDate")
    boolean isVehicleAvailableBetweenDateTimes(int vehicleId, LocalDateTime startDateTime);
}
