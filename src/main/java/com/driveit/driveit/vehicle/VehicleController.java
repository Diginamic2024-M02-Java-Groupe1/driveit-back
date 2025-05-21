package com.driveit.driveit.vehicle;

import com.driveit.driveit._exceptions.AppException;
import com.driveit.driveit.brand.Brand;
import com.driveit.driveit.brand.BrandService;
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

    private final BrandService brandService;

    @GetMapping("")
    public ResponseEntity<List<VehicleDto>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllAvailableVehicles());
    }


    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService, BrandService brandService) {
        this.vehicleService = vehicleService;
        this.brandService = brandService;
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
    public ResponseEntity<String> insertVehicle(@Valid @RequestBody VehicleCreateDto vehicleCreateDto, BindingResult controleQualite) throws AppException { //@Valid
        if (controleQualite.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    controleQualite.getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.joining(", ")));

        }
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

    /**
     * Récupère les noms de toutes les marques ou filtre par nom.
     *
     * @param name paramètre facultatif permettant de filtrer les marques contenant cette chaîne (insensible à la casse)
     * @return ResponseEntity contenant une liste des noms de marques correspondant au critère de recherche,
     *         ou toutes les marques si aucun nom n'est spécifié
     */
    @GetMapping("/brands")
    public ResponseEntity<List<String>> getAllBrands(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(brandService.getBrandsByName(name).stream()
                .map(Brand::getName)
                .toList());
    }
}