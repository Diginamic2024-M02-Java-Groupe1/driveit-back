package com.driveit.driveit.reservationvehicle;

import com.driveit.driveit._utils.Converter;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorRepository;
import com.driveit.driveit.vehicle.Vehicle;
import com.driveit.driveit.vehicle.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationVehicleService {

    private final ReservationVehicleRepository reservationVehicleRepository;

    private final CollaboratorRepository collaboratorRepository;

    private final VehicleRepository vehicleRepository;

    @Autowired
    public ReservationVehicleService(ReservationVehicleRepository reservationVehicleRepository, CollaboratorRepository collaboratorRepository, VehicleRepository vehicleRepository) {
        this.reservationVehicleRepository = reservationVehicleRepository;
        this.collaboratorRepository = collaboratorRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<ReservationVehicle> getAllReservationVehicles() {
        return reservationVehicleRepository.findAll();
    }

    public boolean isAvailableBetweenDateTimes(int vehicleId, LocalDateTime from) {
        return reservationVehicleRepository.isVehicleAvailableBetweenDateTimes(vehicleId, from);
    }

    public String reserveVehicle(int userId, String dateStart, String timeStart, String dateEnd, String timeEnd, Vehicle vehicle) {
        LocalDateTime from = Converter.stringToLocalDateTime(dateStart,timeStart);
        if(isAvailableBetweenDateTimes(vehicle.getId(), from)) {
            LocalDateTime to = Converter.stringToLocalDateTime(dateEnd,timeEnd);
            Collaborator collaborator = collaboratorRepository.findById(userId).get();
            Vehicle vehicleAdded = vehicleRepository.findByRegistration(vehicle.getRegistration());
            ReservationVehicle reservation = new ReservationVehicle(from,to,vehicleAdded,collaborator);
            save(reservation);
            return "Réservation effectuée";
        }
        return "Echec de la réservation";

    }

    @Transactional
    public void save(ReservationVehicle reservationVehicle) {
        reservationVehicleRepository.save(reservationVehicle);
    }

}
