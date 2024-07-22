package com.driveit.driveit.collaborator;

import com.driveit.driveit.reservationcollaborator.ReservationCollaboratorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/collaborators")
public class CollaboratorController {

    private final CollaboratorService collaboratorService;

    public CollaboratorController(CollaboratorService collaboratorService) {
        this.collaboratorService = collaboratorService;
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity<List<ReservationCollaboratorDto>> getReservations(@PathVariable int id) {
        return ResponseEntity.ok(collaboratorService.getReservations(id));
    }
}
