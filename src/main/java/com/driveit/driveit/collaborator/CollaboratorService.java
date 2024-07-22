package com.driveit.driveit.collaborator;


import com.driveit.driveit._utils.Mapper;
import com.driveit.driveit.reservationcollaborator.ReservationCollaboratorDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Cette classe est un service qui gère les opérations sur les collaborateurs
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
     * Méthode pour supprimer un collaborateur
     * @param collaboratorService le collaborateur à supprimer
     */
    @Transactional
    public void deleteCollaborator(Collaborator collaboratorService) {
        collaboratorRepository.delete(collaboratorService);
    }

    /**
     * Méthode pour ajouter un collaborateur
     * @param collaborator le collaborateur à ajouter
     */
    @Transactional
    public void save(Collaborator collaborator) {
        collaboratorRepository.save(collaborator);
    }

    public List<ReservationCollaboratorDto> getReservations(int id) {
        Collaborator collaborator = collaboratorRepository.findById(id).orElseThrow();
        return collaborator.getReservationCollaborators().stream().map(Mapper::reservationCollaboratorToDto).toList();
    }
}
