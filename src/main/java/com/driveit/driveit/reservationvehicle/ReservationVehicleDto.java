package com.driveit.driveit.reservationvehicle;


import com.driveit.driveit.vehicle.VehicleDto;

import java.time.LocalDateTime;

// DTO pour les réservations de véhicules
public record ReservationVehicleDto(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, VehicleDto vehicle) {

}
