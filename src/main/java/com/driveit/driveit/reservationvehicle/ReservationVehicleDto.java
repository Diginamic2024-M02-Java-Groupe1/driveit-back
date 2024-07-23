package com.driveit.driveit.reservationvehicle;


import com.driveit.driveit.vehicle.VehicleDto;

// DTO pour les réservations de véhicules
public record ReservationVehicleDto(String dateStart, String dateEnd, String timeStart, String timeEnd, VehicleDto vehicleDto) {

}
