package com.driveit.driveit.reservationvehicle;

import com.driveit.driveit._exceptions.AppException;
import com.driveit.driveit.collaborator.CollaboratorDto;
import com.driveit.driveit.collaborator.CollaboratorService;
import com.driveit.driveit.vehicle.VehicleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    private final CollaboratorService collaboratorService;

    /**
     * Constructeur
     *
     * @param reservationVehicleService service de réservation de véhicules
     */
    public ReservationVehicleController(ReservationVehicleService reservationVehicleService, CollaboratorService collaboratorService) {
        this.reservationVehicleService = reservationVehicleService;
        this.collaboratorService = collaboratorService;
    }

    /**
     * Méthode permettant d'afficher toutes les réservations de véhicules de service pour un utilisateur
     *
     * @param status statut de la réservation
     * @return la liste des réservations
     * @throws AppException retourne l'erreur sur la non récupération
     */
    @GetMapping("/reservation")
    public ResponseEntity<List<VehiculeServiceReservationDto>> getMyReservation(@RequestParam String status) throws AppException {
        return ResponseEntity.ok(reservationVehicleService.getMyReservationVehicleService(collaboratorService.getAuthenticatedCollaborator().id(),status.toLowerCase()));
    }

    /**
     * Méthode permettant d'afficher tous les véhicules de location disponibles aux dates et heures renseignées
     *
     * @param dateStart date et heure de début de la location
     * @param dateEnd   date et heure de fin de la location
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
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateEnd
    ) {
        System.out.println(dateStart.toString() + "   " +  dateEnd.toString());
        return ResponseEntity.ok(reservationVehicleService.getAvailableService(dateStart,dateEnd));
    }

    /**
     * Méthode permettant de réserver un véhicule de service aux dates et heures souhaitées
     *
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
            @RequestBody ReservationVehicleDto reservationVehicleDto) throws AppException {
        return ResponseEntity.ok(reservationVehicleService.reserveVehicle(collaboratorService.getAuthenticatedCollaborator().id(), reservationVehicleDto));
    }

    /**
     * Méthode permettant de modifier la réservation d'un véhicule de service
     *
     * @param reservationVehicleDto donnée de la réservation
     * @return une chaine de caractère affichant la réussite de la modification
     * @throws AppException une info sur l'erreur de la non-modification
     */
    @PutMapping("/modifier")
    public ResponseEntity<String> updateReservationVehicle(@RequestBody ReservationVehicleDto reservationVehicleDto) throws AppException {
        return ResponseEntity.ok(reservationVehicleService.updateReservationVehicle(collaboratorService.getAuthenticatedCollaborator().id(), reservationVehicleDto));
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
