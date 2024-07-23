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
 * Elle est utilisée pour supprimer ou ajouter un collaborateur, etc.
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
     * L'encodeur de mot de passe
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructeur du service des collaborateurs
     * @param collaboratorRepository le repository des collaborateurs
     */
    @Autowired
    public CollaboratorService(CollaboratorRepository collaboratorRepository, PasswordEncoder passwordEncoder) {
        this.collaboratorRepository = collaboratorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init(){
        Admin admin = new Admin("admin@admin.com",passwordEncoder.encode("admin"),"admin","admin");
        collaboratorRepository.save(admin);
    }

    /**
     * Méthode pour récupérer un collaborateur
     *
     * @param id l'id du collaborateur
     * @return le collaborateur
     * @throws NotFoundException si le collaborateur n'existe pas
     */
    public Collaborator getCollaboratorById(int id) throws NotFoundException {
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
        collaborator.setEmail(collaboratorDto.email());
        collaborator.setFirstName(collaboratorDto.firstName());
        collaborator.setLastName(collaboratorDto.lastName());
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
        Collaborator existingCollaborator = getCollaboratorById(id);
        if (collaboratorPatchDto.email() != null) {
            existingCollaborator.setEmail(collaboratorPatchDto.email());
        }
        if (collaboratorPatchDto.firstName() != null) {
            existingCollaborator.setFirstName(collaboratorPatchDto.firstName());
        }
        if (collaboratorPatchDto.lastName() != null) {
            existingCollaborator.setLastName(collaboratorPatchDto.lastName());
        }
        if (collaboratorPatchDto.authorities() != null) {
            existingCollaborator.setAuthorities(collaboratorPatchDto.authorities());
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
        Collaborator collaborator = getCollaboratorById(id);
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
        Collaborator collaborator = getCollaboratorById(id);
        return collaborator.getReservationCollaborators().stream().map(Mapper::reservationCollaboratorToDto).toList();
    }
}
