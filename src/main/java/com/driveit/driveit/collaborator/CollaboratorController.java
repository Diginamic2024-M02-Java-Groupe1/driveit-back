package com.driveit.driveit.collaborator;

import com.driveit.driveit._auth.RegisterUserDto;
import com.driveit.driveit._exceptions.AppException;
import com.driveit.driveit._exceptions.NotFoundException;
import com.driveit.driveit.reservationcarpooling.ReservationCarpoolingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collaborators")
@Tag(name = "Collaborators", description = "API pour les opérations sur les collaborateurs")
public class CollaboratorController {

    private final CollaboratorService collaboratorService;

    public CollaboratorController(CollaboratorService collaboratorService) {
        this.collaboratorService = collaboratorService;
    }

    @Operation(summary = "Récupère l'utilisateur authentifié")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retourne le collaborateur authentifié",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Collaborator.class)
                    )}
            )
    })
    @GetMapping("/me")
    public ResponseEntity<CollaboratorDto> authenticatedUser() {
        CollaboratorDto currentUser = collaboratorService.getAuthenticatedCollaborator();
        return ResponseEntity.ok(currentUser);
    }

    @Operation(summary = "Récupère les réservations d'un collaborateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retourne la liste des réservations d'un collaborateur",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationCarpoolingDto.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "Collaborateur non trouvé", content = @Content)
    })
    @GetMapping("/{id}/reservations")
    public ResponseEntity<List<ReservationCarpoolingDto>> getReservations(@PathVariable int id) throws NotFoundException {
        return ResponseEntity.ok(collaboratorService.getReservations(id));
    }

    @Operation(summary = "Ajoute un collaborateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retourne le collaborateur ajouté",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Collaborator.class)
                    )}
            ),
            @ApiResponse(responseCode = "400", description = "Données invalides", content = @Content)
    })
    @PostMapping()
    public ResponseEntity<Collaborator> createCollaborator(@Valid @RequestBody RegisterUserDto registerUserDto, BindingResult result) throws AppException {
        if (result.hasErrors()) {
            throw new AppException("Invalid collaborator data");
        }
        return ResponseEntity.ok(collaboratorService.saveCollaborator(registerUserDto));
    }

    @Operation(summary = "Mettre à jour un collaborateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retourne le collaborateur mis à jour",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Collaborator.class)
                    )}
            ),
            @ApiResponse(responseCode = "400", description = "Données invalides", content = @Content),
            @ApiResponse(responseCode = "404", description = "Collaborateur non trouvé", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Collaborator> updateCollaborator(@PathVariable int id, @Valid @RequestBody CollaboratorDto collaboratorDto, BindingResult result) throws AppException, NotFoundException {
        if (result.hasErrors()) {
            throw new AppException("Invalid collaborator data");
        }
        return ResponseEntity.ok(collaboratorService.update(id, collaboratorDto));
    }

    @Operation(summary = "Supprimer un collaborateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Collaborateur supprimé", content = @Content),
            @ApiResponse(responseCode = "404", description = "Collaborateur non trouvé", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCollaborator(@PathVariable int id) throws NotFoundException {
        collaboratorService.delete(id);
        return ResponseEntity.ok("Collaborator deleted");
    }
}
