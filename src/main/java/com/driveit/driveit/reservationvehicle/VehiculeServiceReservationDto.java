package com.driveit.driveit.reservationvehicle;

import com.driveit.driveit.vehicle.VehicleDto;

import java.time.LocalDateTime;

public record VehiculeServiceReservationDto(int id,LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, VehicleDto vehicle) {
}
