package com.driveit.driveit.collaborator;


import com.driveit.driveit._exceptions.NotFoundException;
import com.driveit.driveit._utils.Mapper;
import com.driveit.driveit.reservationcarpooling.ReservationCarpoolingDto;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        Collaborator collaborator = new Collaborator("admin@admin.com",passwordEncoder.encode("admin"),"admin","admin");
        collaboratorRepository.save(collaborator);
    }

    /**
     * Méthode pour récupérer un collaborateur
     *
     * @param id l'id du collaborateur
     * @return le collaborateur
     * @throws NotFoundException si le collaborateur n'existe pas
     */
    public Collaborator getById(int id) throws NotFoundException {
        return collaboratorRepository.findById(id).orElseThrow(() -> new NotFoundException("Collaborator with id " + id + " not found"));
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
//        collaborator.setRole(collaboratorDto.role());
        return collaboratorRepository.save(collaborator);
    }

    /**
     * Méthode pour mettre à jour un collaborateur
     * @param id l'id du collaborateur à mettre à jour
     * @param collaboratorPatchDto les informations à mettre à jour
     * @return le collaborateur mis à jour
     * @throws NotFoundException si le collaborateur n'existe pas
     */
    @Transactional
    public Collaborator update(int id, CollaboratorDto collaboratorPatchDto) throws NotFoundException {
        Collaborator existingCollaborator = getById(id);
        if (collaboratorPatchDto.firstName() != null) {
            existingCollaborator.setFirstName(collaboratorPatchDto.firstName());
        }
        if (collaboratorPatchDto.lastName() != null) {
            existingCollaborator.setLastName(collaboratorPatchDto.lastName());
        }
        if (collaboratorPatchDto.role() != null) {
//            existingCollaborator.setRole(collaboratorPatchDto.role());
        }
        return collaboratorRepository.save(existingCollaborator);
    }

    /**
     * Méthode pour supprimer un collaborateur
     * @param id l'id du collaborateur à supprimer
     *
     */
    @Transactional
    public void delete(int id) throws NotFoundException {
        Collaborator collaborator = getById(id);
        collaboratorRepository.delete(collaborator);
    }

    /**
     * Méthode pour récupérer les réservations d'un collaborateur
     *
     * @param id l'id du collaborateur
     * @return la liste des réservations du collaborateur
     * @throws NotFoundException si le collaborateur n'existe pas
     */
    public List<ReservationCarpoolingDto> getReservations(int id) throws NotFoundException {
        Collaborator collaborator = getById(id);
        return collaborator.getReservationCollaborators().stream().map(Mapper::reservationCollaboratorToDto).toList();
    }

    public Collaborator getCollaboratorById(int id) {
        return collaboratorRepository.findById(id).get();
    }
}
