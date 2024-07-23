package com.driveit.driveit.vehicle;

import com.driveit.driveit._exceptions.AppException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicule")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Get all vehicles
     *
     * @return
     */
    @GetMapping("/service")
    public ResponseEntity<?> getAllServiceVehicles() {
        return vehicleService.getAllServiceVehiclesDto();
    }

    /**
     * Get all available vehicles
     *
     * @return
     */
    @GetMapping("/service/{id}")
    public ResponseEntity<?> getAlServiceVehicleById(@PathVariable int id) {
        return vehicleService.getServiceVehicleDtoById(id);
    }

    /**
     * Insert a vehicle
     *
     * @param vehicleDto
     * @return
     */
    @PostMapping("/service")
    public ResponseEntity<String> insertVehicle(@Valid @RequestBody VehicleDto vehicleDto, BindingResult controleQualite) throws AppException {
        if (controleQualite.hasErrors()) {
            throw new AppException(
                    controleQualite.getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.joining(", "))
            );
        }
        return vehicleService.insertVehicle(vehicleDto);
    }

    /**
     * Update a vehicle
     *
     * @param id
     * @param vehicle
     * @return
     */
    @PutMapping("/service/{id}")
    public ResponseEntity<String> updateVehicle(@PathVariable int id, @Valid @RequestBody Vehicle vehicle, BindingResult controleQualite) throws AppException {
        if (controleQualite.hasErrors()) {
            throw new AppException(
                    controleQualite.getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.joining(", "))
            );
        }
        return vehicleService.updateVehicle(id, vehicle);
    }

    /**
     * Delete a vehicle
     *
     * @param id
     */
    @DeleteMapping("/service/{id}")
    public void deleteVehicle(@PathVariable int id) {
        vehicleService.deleteVehicle(id);
    }

}