package com.driveit.driveit.reservationvehicle;


import com.driveit.driveit.vehicle.VehicleDto;

import java.time.LocalDateTime;

// DTO pour les réservations de véhicules
public record ReservationVehicleDto(LocalDateTime dateStart, LocalDateTime dateEnd, VehicleDto vehicleDto) {

}
