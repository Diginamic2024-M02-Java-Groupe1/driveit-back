package com.driveit.driveit.vehicle;

import com.driveit.driveit._utils.Mapper;
import com.driveit.driveit.brand.BrandDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
public class VehicleController {

    @GetMapping("")
    public ResponseEntity<List<VehicleDto>> getAllVehicles() {
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
    public ResponseEntity<List<VehicleDto>> getAllServiceVehicles() {
        return ResponseEntity.ok(vehicleService.getAllServiceVehiclesDto());
    }

    /**
     * Get a vehicle by id
     *
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/service/{id}")
    public ResponseEntity<VehicleDto> getServiceVehicleById(@PathVariable int id) {
        return ResponseEntity.ok(vehicleService.getServiceVehicleDtoById(id));
    }

    /**
     * Insert a vehicle
     *
     * @param vehicleCreateDto
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/service")
    public VehicleDto insertVehicle(@Valid @RequestBody VehicleCreateDto vehicleCreateDto) {
        if(vehicleCreateDto == null) {
            throw new IllegalArgumentException("Le véhicule ne peut pas être nul");
        }
        return vehicleService.insertVehicle(vehicleCreateDto);
    }

    /**
     * Update a vehicle
     *
     * @param vehicle
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/service")
    public VehicleDto updateVehicle(@Valid @RequestBody VehicleDto vehicleDto) {
        return vehicleService.updateVehicle(vehicleDto);
    }

    /**
     * Delete a vehicle
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/service/{id}")
    public ResponseEntity<VehicleDto> deleteVehicle(@PathVariable int id, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ResponseEntity.ok(vehicleService.deleteVehicle(id, startDateTime, endDateTime));
    }


    /**
     * Récupère les noms de toutes les marques ou filtre par nom.
     *
     * @param name paramètre facultatif permettant de filtrer les marques contenant cette chaîne (insensible à la casse)
     * @return ResponseEntity contenant une liste des noms de marques correspondant au critère de recherche,
     *         ou toutes les marques si aucun nom n'est spécifié
     */
    @GetMapping("/brands")
    public ResponseEntity<List<BrandDto>> getAllBrands(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(vehicleService.getBrandsByName(name).stream()
                .map(Mapper::brandToDto).toList());
    }
}