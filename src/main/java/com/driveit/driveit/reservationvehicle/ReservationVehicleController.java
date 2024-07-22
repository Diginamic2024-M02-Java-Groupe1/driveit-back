package com.driveit.driveit.reservationvehicle;

import com.driveit.driveit._exceptions.AppException;
import com.driveit.driveit.vehicle.VehicleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vehicules/location")
public class ReservationVehicleController {

    private final ReservationVehicleService reservationVehicleService;

    public ReservationVehicleController(ReservationVehicleService reservationVehicleService) {
        this.reservationVehicleService = reservationVehicleService;
    }

    /**
     * Méthode permettant d'afficher tous les véhicules de location disponibles aux dates et heures renseignées
     *
     * @param reservationVehicleDto date et heure de début et de fin + infos du véhicule de service
     * @return Liste de véhicules filtrés
     */
    @Operation(summary = "Affiche la liste des véhicules de service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retourne la liste des véhicules de service disponibles entre une date et heure de début et une date et heure de fin",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationVehicleDto.class)) })})
    @GetMapping("/filtrer")
    public ResponseEntity<List<VehicleDto>> getVehiclesLocation (
            @RequestBody ReservationVehicleDto reservationVehicleDto) {
        return ResponseEntity.ok(reservationVehicleService.getAvailableService(reservationVehicleDto));
    }


    /**
     * Méthode permettant de réserver un véhicule de service aux dates et heures souhaitées
     *
     * @param userId identifiant de l'utilisateur
     * @param reservationVehicleDto date et heure de début et de fin + infos du véhicule de service
     * @return un message indiquant si la réservation a été effectuée ou non
     */
    @Operation(summary = "Effectue la réservation d'un véhicule de service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retroune un message de validation de réservation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationVehicleDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Si une règle métier n'est pas respectée.",
                    content = @Content)})
    @PostMapping("/reserver")
    public ResponseEntity<String> doReservationVehicle(
            @RequestParam int userId,
            @RequestBody ReservationVehicleDto reservationVehicleDto) throws AppException {
        return ResponseEntity.ok(reservationVehicleService.reserveVehicle(userId, reservationVehicleDto));
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<String> deleteReservationVehicle(@PathVariable int id) throws AppException {
        reservationVehicleService.delete(id);
        return ResponseEntity.ok("La réservation a été supprimée" );
    }

}
