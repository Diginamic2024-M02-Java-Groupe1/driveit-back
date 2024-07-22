package com.driveit.driveit.vehicle;

import com.driveit.driveit._exceptions.AppException;
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

    @GetMapping("/service")
    public ResponseEntity<List<VehicleDto>> getVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehiclesDto(vehicleService.getAllVehicles()));
    }

    @PostMapping("/service")
    public ResponseEntity<String> insertVehicle(@Valid @RequestBody Vehicle vehicle, BindingResult controleQualite) throws AppException {
        if (controleQualite.hasErrors()) {
            throw new AppException(
                    controleQualite.getAllErrors()
                            .stream()
                            .map(error -> error.getDefaultMessage())
                            .collect(Collectors.joining(", "))
            );
        }
        return vehicleService.insertVehicle(vehicle);
    }

    @PutMapping("/service/{id}")
    public ResponseEntity<String> updateVehicle(@PathVariable int id, @Valid @RequestBody Vehicle vehicle, BindingResult controleQualite) throws AppException {
        if (controleQualite.hasErrors()) {
            throw new AppException(
                    controleQualite.getAllErrors()
                            .stream()
                            .map(error -> error.getDefaultMessage())
                            .collect(Collectors.joining(", "))
            );
        }
        vehicleService.updateVehicle(id, vehicle);
        System.out.println("VehicleController : le véhicule a été mis à jour avec succès.");
        return ResponseEntity.ok(vehicle.toString());
    }

    @DeleteMapping("/service/{id}")
    public void deleteVehicle(@PathVariable int id) {
        vehicleService.deleteVehicle(id);
    }

}