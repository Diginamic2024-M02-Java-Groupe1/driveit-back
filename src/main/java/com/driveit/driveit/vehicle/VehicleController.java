package com.driveit.driveit.vehicle;

import com.driveit.driveit._exceptions.AppException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/service")
    public ResponseEntity<?> getAllServiceVehicles() {
        return vehicleService.getAllServiceVehiclesDto();
    }

    /**
     * Get all available vehicles
     *
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/service/{id}")
    public ResponseEntity<?> getAlServiceVehicleById(@PathVariable int id) {
        return vehicleService.getServiceVehicleDtoById(id);
    }

    /**
     * Insert a vehicle
     *
     * @param vehicleCreateDto
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/service")
    public ResponseEntity<String> insertVehicle(@RequestBody VehicleCreateDto vehicleCreateDto, BindingResult controleQualite) throws AppException { //@Valid
        System.out.println("Je passe par le back avant le contrôle qualité !");
//        if (controleQualite.hasErrors()) {
//            System.out.println("Je passe par le back dans le contrôle qualité !");
//            System.out.println(controleQualite.getAllErrors());
//            return ResponseEntity.badRequest().body(
//                    controleQualite.getAllErrors()
//                            .stream()
//                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                            .collect(Collectors.joining(", "))
//            );
//        }
        return vehicleService.insertVehicle(vehicleCreateDto);
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/service/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable int id, LocalDateTime startDate) {
        return vehicleService.deleteVehicle(id, startDate);
    }

}