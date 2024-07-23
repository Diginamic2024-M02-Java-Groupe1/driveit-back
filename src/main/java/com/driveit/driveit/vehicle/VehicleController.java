package com.driveit.driveit.vehicle;

import com.driveit.driveit._exceptions.NotFoundException;
import com.driveit.driveit._exceptions.appException;
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
    public ResponseEntity<List<VehicleDto>> getVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehiclesDto(vehicleService.getAllVehicles()));
    }

    /**
     * Get a vehicle by its id
     *
     * @param id
     * @return
     */
    @GetMapping("/service/{id}")
    public ResponseEntity<VehicleDto> getVehicles(@PathVariable int id) { //TODO compléter avec tous les cas de figure (not found, bad request...)
        return ResponseEntity.ok(vehicleService.getVehicleDtoById(id));
    }

    /**
     * Insert a vehicle
     *
     * @param vehicleDto
     * @return
     */
    @PostMapping("/service")
    public ResponseEntity<String> insertVehicle(@Valid @RequestBody VehicleDto vehicleDto, BindingResult controleQualite) throws appException {
        if (controleQualite.hasErrors()) {
            throw new appException(
                    controleQualite.getAllErrors()
                            .stream()
                            .map(error -> error.getDefaultMessage())
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
    public ResponseEntity<String> updateVehicle(@PathVariable int id, @Valid @RequestBody Vehicle vehicle, BindingResult controleQualite) throws appException {
        if (controleQualite.hasErrors()) {
            throw new appException(
                    controleQualite.getAllErrors()
                            .stream()
                            .map(error -> error.getDefaultMessage())
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