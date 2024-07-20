package com.driveit.driveit.vehicle;

import com.driveit.driveit._exceptions.AnomalieException;
import com.driveit.driveit.reservationvehicle.ReservationVehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicule")
public class VehicleController {

    private final VehicleService vehicleService;

    private final ReservationVehicleService reservationVehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService, ReservationVehicleService reservationVehicleService) {
        this.vehicleService = vehicleService;
        this.reservationVehicleService = reservationVehicleService;
    }

    @GetMapping("/service")
    public ResponseEntity<List<VehicleDto>> getVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehiclesDto(vehicleService.getAllVehicles()));
    }

    /**
     * requete : http://localhost:8081/api/vehicule/service/location?dateStart=2024-07-20&timeStart=10:00:00&dateEnd=2024-07-21&timeEnd=18:00:00
     *
     * @return
     * @throws AnomalieException
     */
    @GetMapping("/service/location")
    public ResponseEntity<List<VehicleDto>> getVehiclesLocation (
            @RequestParam String dateStart,
            @RequestParam String timeStart,
            @RequestParam String dateEnd,
            @RequestParam String timeEnd) {
        return ResponseEntity.ok(vehicleService.getAvailableService(dateStart, timeStart, dateEnd, timeEnd));
    }

    /**
     * requete : http://localhost:8081/api/vehicule/service/location?userId=1&dateStart=2024-07-20&timeStart=10:00:00&dateEnd=2024-07-21&timeEnd=18:00:00
     *
     * @return
     * @throws AnomalieException
     */
    @PostMapping("/service/location")
    public ResponseEntity<String> insertReservationVehiculeService (
            @RequestParam int userId,
            @RequestParam String dateStart,
            @RequestParam String timeStart,
            @RequestParam String dateEnd,
            @RequestParam String timeEnd,
            @RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(reservationVehicleService.reserveVehicle(userId,dateStart, timeStart, dateEnd, timeEnd,vehicle));
    }


    @PostMapping("/service")
    public ResponseEntity<String> insertVehicle(@Valid @RequestBody Vehicle vehicle, BindingResult controleQualite) throws AnomalieException {
        if (controleQualite.hasErrors()) {
            throw new AnomalieException(
                    controleQualite.getAllErrors()
                            .stream()
                            .map(error -> error.getDefaultMessage())
                            .collect(Collectors.joining(", "))
            );
        }
        vehicleService.insertVehicle(vehicle);
        return ResponseEntity.ok(vehicle.toString());
    }

    @PutMapping("/service/{id}")
    public ResponseEntity<String> updateVehicle(@PathVariable int id, @Valid @RequestBody Vehicle vehicle, BindingResult controleQualite) throws AnomalieException {
        if (controleQualite.hasErrors()) {
            throw new AnomalieException(
                    controleQualite.getAllErrors()
                            .stream()
                            .map(error -> error.getDefaultMessage())
                            .collect(Collectors.joining(", "))
            );
        }
        vehicleService.updateVehicle(id, vehicle);
        return ResponseEntity.ok(vehicle.toString());
    }

    @DeleteMapping("/service/{id}")
    public void deleteVehicle(@PathVariable int id) {
        vehicleService.deleteVehicle(id);
    }

}