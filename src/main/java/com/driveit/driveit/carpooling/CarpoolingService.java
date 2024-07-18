package com.driveit.driveit.carpooling;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     *
     * @param carpooling le covoiturage à ajouter
     * @return
     */
    @Transactional
    public Carpooling save(Carpooling carpooling) {
        carpoolingRepository.save(carpooling);
        return carpooling;
    }

    /**
     * Méthode pour obtenir un covoiturage par son ID
     *
     * @param id l'identifiant du covoiturage
     * @return le covoiturage
     */
    public Carpooling getCarpoolingById(int id) {
        return carpoolingRepository.findById(id).orElse(null);
    }

    /**
     * Méthode pour obtenir la liste des covoiturages
     *
     * @return la liste des covoiturages
     */
    public List<Carpooling> getCarpoolings() {
        return carpoolingRepository.findAll();
    }
}
