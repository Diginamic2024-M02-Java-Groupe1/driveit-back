package com.driveit.driveit.reservationvehicle;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationVehicleService {

    private final ReservationVehicleRepository reservationVehicleRepository;

    @Autowired
    public ReservationVehicleService(ReservationVehicleRepository reservationVehicleRepository) {
        this.reservationVehicleRepository = reservationVehicleRepository;
    }

    public List<ReservationVehicle> getAllReservationVehicles() {
        return reservationVehicleRepository.findAll();
    }

    public boolean isAvailableBetweenDateTimes(int vehicleId, LocalDateTime from) {
        return reservationVehicleRepository.isVehicleAvailableBetweenDateTimes(vehicleId, from);
    }

    @Transactional
    public void save(ReservationVehicle reservationVehicle) {
        reservationVehicleRepository.save(reservationVehicle);
    }

}
