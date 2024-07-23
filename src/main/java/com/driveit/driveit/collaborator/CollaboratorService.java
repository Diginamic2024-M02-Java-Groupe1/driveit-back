package com.driveit.driveit.collaborator;


import com.driveit.driveit._exceptions.NotFoundException;
import com.driveit.driveit._utils.Mapper;
import com.driveit.driveit.reservationcarpooling.ReservationCarpoolingDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Cette classe est un service qui gère les opérations sur les collaborateurs.
 * Elle est utilisée pour supprimer ou ajouter un collaborateur etc ...
 *
 * @see CollaboratorService
 * @see CollaboratorRepository
 */
@Service
public class CollaboratorService {
    /**
     * Le repository des collaborateurs
     */
    private final CollaboratorRepository collaboratorRepository;

    /**
     * Constructeur du service des collaborateurs
     * @param collaboratorRepository le repository des collaborateurs
     */
    @Autowired
    public CollaboratorService(CollaboratorRepository collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;
    }

    /**
     * Méthode pour ajouter un collaborateur
     *
     * @param collaboratorDto le collaborateur à ajouter
     * @return le collaborateur ajouté
     */
    @Transactional
    public Collaborator save(CollaboratorDto collaboratorDto){
        Collaborator collaborator = new Collaborator();
        collaborator.setFirstName(collaboratorDto.firstName());
        collaborator.setLastName(collaboratorDto.lastName());
        collaborator.setRole(collaboratorDto.role());
        return collaboratorRepository.save(collaborator);
    }

    /**
     * Méthode pour supprimer un collaborateur
     * @param collaboratorService le collaborateur à supprimer
     */
    @Transactional
    public void delete(Collaborator collaboratorService) {
        collaboratorRepository.delete(collaboratorService);
    }

    /**
     * Méthode pour récupérer les réservations d'un collaborateur
     *
     * @param id l'id du collaborateur
     * @return la liste des réservations du collaborateur
     * @throws NotFoundException si le collaborateur n'existe pas
     */
    public List<ReservationCarpoolingDto> getReservations(int id) throws NotFoundException {
        Collaborator collaborator = collaboratorRepository.findById(id).orElseThrow(() -> new NotFoundException("Collaborator not found"));
        return collaborator.getReservationCollaborators().stream().map(Mapper::reservationCollaboratorToDto).toList();
    }
}
