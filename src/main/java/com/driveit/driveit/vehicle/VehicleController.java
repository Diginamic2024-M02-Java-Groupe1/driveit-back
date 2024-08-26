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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicules")
public class VehicleController {

    @GetMapping("")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllAvailableVehicles());
    }


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
    @PostMapping( "/service") //, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    public ResponseEntity<String> insertVehicle(@RequestBody VehicleCreateDto vehicleCreateDto, BindingResult controleQualite) throws AppException { //@Valid
        System.out.println("Je passe par le back avant le contrôle qualité !");
//        if (controleQualite.hasErrors()) {
//            System.out.println("Je passe par le back dans le contrôle qualité !");
//            System.out.println(controleQualite.getAllErrors());
//
//            Response response = new Response();
//            response.setMessage("Erreur de validation");
//            return ResponseEntity.badRequest().body(response);
////                        controleQualite.getAllErrors()
////                                .stream()
////                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
////                                .collect(Collectors.joining(", "))
////                );
//
//        }
        return vehicleService.insertVehicle(vehicleCreateDto);

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
    public ResponseEntity<String> updateVehicle(@Valid @PathVariable int id,
                                                @RequestBody Vehicle vehicle, BindingResult controleQualite) throws AppException {
        if (controleQualite.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    controleQualite.getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.joining(", "))
            );
        }

        System.out.println("Je passe dans le controller avant le service !");
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
    public ResponseEntity<String> deleteVehicle(@PathVariable int id, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return vehicleService.deleteVehicle(id, startDateTime, endDateTime);
    }




}