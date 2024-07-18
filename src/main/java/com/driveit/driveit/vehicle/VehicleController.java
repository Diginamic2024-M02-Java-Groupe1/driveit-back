package com.driveit.driveit.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public List<Vehicle> getVehicles() {
        return vehicleService.getAllVehicles();
    }

    @PostMapping
    public Vehicle insertVehicle(@RequestBody Vehicle vehicle) {
        vehicleService.insertVehicle(vehicle);
        return vehicle;
    }

    @PutMapping("/{id}")
    public Vehicle updateVehicle(int id, @RequestBody Vehicle vehicle) {
        vehicleService.updateVehicle(id, vehicle);
        return vehicle;
    }
}