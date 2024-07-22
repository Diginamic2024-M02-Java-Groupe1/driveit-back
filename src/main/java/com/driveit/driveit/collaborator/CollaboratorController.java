package com.driveit.driveit.collaborator;

import com.driveit.driveit._exceptions.AppException;
import com.driveit.driveit._exceptions.NotFoundException;
import com.driveit.driveit.reservationcarpooling.ReservationCarpoolingDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collaborators")
public class CollaboratorController {

    private final CollaboratorService collaboratorService;

    public CollaboratorController(CollaboratorService collaboratorService) {
        this.collaboratorService = collaboratorService;
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity<List<ReservationCarpoolingDto>> getReservations(@PathVariable int id) throws NotFoundException {
        return ResponseEntity.ok(collaboratorService.getReservations(id));
    }

    @PostMapping()
    public ResponseEntity<Collaborator> save(@Valid @RequestBody Collaborator collaborator, BindingResult result) throws AppException {
        if (result.hasErrors()) {
            throw new AppException("Invalid collaborator data");
        }
        return ResponseEntity.ok(collaboratorService.save(collaborator));
    }
}
