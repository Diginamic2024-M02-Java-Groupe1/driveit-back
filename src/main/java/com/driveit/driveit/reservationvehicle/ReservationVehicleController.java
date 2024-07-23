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

/**
 * Contrôleur de réservation de véhicules
 */
@RestController
@RequestMapping("api/vehicules/location")
public class ReservationVehicleController {

    /**
     * Service de réservation de véhicules
     */
    private final ReservationVehicleService reservationVehicleService;

    /**
     * Constructeur
     *
     * @param reservationVehicleService service de réservation de véhicules
     */
    public ReservationVehicleController(ReservationVehicleService reservationVehicleService) {
        this.reservationVehicleService = reservationVehicleService;
    }

    /**
     * Méthode permettant d'afficher toutes les réservations de véhicules de service pour un utilisateur
     *
     * @param idCollabo id du collaborateur
     * @param status statut de la réservation
     * @return la liste des réservations
     * @throws appException retourne l'erreur sur la non récupération
     */
    @GetMapping("/reservation/{idCollabo}")
    public ResponseEntity<List<VehiculeServiceReservationDto>> getMyReservation(@PathVariable int idCollabo,@RequestParam String status) throws appException {
        return ResponseEntity.ok(reservationVehicleService.getMyReservationVehicleService(idCollabo,status));
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
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationVehicleDto.class))})})
    @GetMapping("/filtrer")
    public ResponseEntity<List<VehicleDto>> getVehiclesLocation(
            @RequestBody ReservationVehicleDto reservationVehicleDto) {
        return ResponseEntity.ok(reservationVehicleService.getAvailableService(reservationVehicleDto));
    }

    /**
     * Méthode permettant de réserver un véhicule de service aux dates et heures souhaitées
     *
     * @param userId                identifiant de l'utilisateur
     * @param reservationVehicleDto date et heure de début et de fin + infos du véhicule de service
     * @return un message indiquant si la réservation a été effectuée ou non
     * @throws AppException retourne l'erreur sur la non insertion
     */
    @Operation(summary = "Effectue la réservation d'un véhicule de service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retourne un message de validation de réservation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationVehicleDto.class))}),
            @ApiResponse(responseCode = "400", description = "Si une règle métier n'est pas respectée.",
                    content = @Content)})
    @PostMapping("/reserver")
    public ResponseEntity<String> doReservationVehicle(
            @RequestParam int userId,
            @RequestBody ReservationVehicleDto reservationVehicleDto) throws AppException {
        return ResponseEntity.ok(reservationVehicleService.reserveVehicle(userId, reservationVehicleDto));
    }

    /**
     * Méthode permettant de modifier la réservation d'un véhicule de service
     *
     * @param id id de la réservation
     * @param reservationVehicleDto donnée de la réservation
     * @return une chaine de caractère affichant la réussite de la modification
     * @throws AppException une info sur l'erreur de la non-modification
     */
    @PutMapping("/modifier/{id}")
    public ResponseEntity<String> updateReservationVehicle(@PathVariable int id, @RequestBody ReservationVehicleDto reservationVehicleDto) throws AppException {
        return ResponseEntity.ok(reservationVehicleService.updateReservationVehicle(id, reservationVehicleDto));
    }

    /**
     * Méthode permettant de supprimer la réservation pour un véhicule de service
     *
     * @param id id de la réservation
     * @return un message d'erreur indiquant la suppréssion réussie
     * @throws AppException retourne l'erreur sur la non suppression
     */
    @Operation(summary = "Supprime une réservation de véhicule de service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retourne un message de validation de suppression",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationVehicleDto.class))}),
            @ApiResponse(responseCode = "400", description = "Si la réservation n'est pas trouvée.",
                    content = @Content)})
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<String> deleteReservationVehicle(@PathVariable int id) throws AppException {
        reservationVehicleService.delete(id);
        return ResponseEntity.ok("La réservation a été supprimée");
    }

}
