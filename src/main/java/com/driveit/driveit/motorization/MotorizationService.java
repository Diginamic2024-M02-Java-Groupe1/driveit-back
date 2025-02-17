package com.driveit.driveit.motorization;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Cette classe est un service qui gère les opérations sur les motorisations
 * Elle est utilisée pour supprimer ou ajouter une motorisation etc ...
 *
 * @see Motorization
 * @see MotorizationRepository
 */
@Service
public class MotorizationService {

    /**
     * Le repository des motorisations
     */
    private final MotorizationRepository motorizationRepository;

    /**
     * Constructeur du service des motorisations
     *
     * @param motorizationRepository le repository des motorisations
     */
    @Autowired
    public MotorizationService(MotorizationRepository motorizationRepository) {
        this.motorizationRepository = motorizationRepository;
    }

    /**
     * Méthode pour supprimer une motorisation
     *
     * @param motorization la motorisation à supprimer
     */
    @Transactional
    public void deleteMotorization(Motorization motorization) {
        motorizationRepository.delete(motorization);
    }

    /**
     * Méthode pour sauvegarde une motorisation
     *
     * @param motorization la motorisation à ajouter
     */
    @Transactional
    public void save(Motorization motorization) {
        motorizationRepository.save(motorization);
    }

    @Transactional
    public Optional<Motorization> findByName(String name) {
        return motorizationRepository.findByName(name);
    }
}
