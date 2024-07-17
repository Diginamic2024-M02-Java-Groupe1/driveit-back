package com.driveit.driveit.service;


import com.driveit.driveit.entity.Carpooling;
import com.driveit.driveit.repository.CarpoolingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Cette classe est un service qui gère les opérations sur les covoiturages
 * Elle est utilisée pour supprimer ou ajouter un covoiturage etc ...
 *
 * @see Carpooling
 * @see CarpoolingRepository
 */
@Service
public class CarpoolingService {

    /**
     * Le repository des covoiturages
     */
    CarpoolingRepository carpoolingRepository;

    /**
     * Constructeur du service des covoiturages
     * @param carpoolingRepository le repository des covoiturages
     */
    @Autowired
    public CarpoolingService(CarpoolingRepository carpoolingRepository) {
        this.carpoolingRepository = carpoolingRepository;
    }

    /**
     * Méthode pour supprimer un covoiturage
     * @param carpooling le covoiturage à supprimer
     */
    @Transactional
    public void deleteCarpooling(Carpooling carpooling) {
        carpoolingRepository.delete(carpooling);
    }

    /**
     * Méthode pour ajouter un covoiturage
     * @param carpooling le covoiturage à ajouter
     */
    @Transactional
    public void save(Carpooling carpooling) {
        carpoolingRepository.save(carpooling);
    }
}
