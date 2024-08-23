package com.driveit.driveit.model;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Cette classe est un service qui gère les opérations sur les modèles de vehicules
 * Elle est utilisée pour supprimer ou ajouter un modèle de vehicule etc ...
 *
 * @see Model
 * @see ModelRepository
 */
@Service
public class ModelService {


    /**
     * Le repository des modèles de vehicules
     */
    private final ModelRepository modelRepository;


    /**
     * Constructeur du service des modèles de vehicules
     * @param modelRepository le repository des modèles de vehicules
     */
    @Autowired
    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    /**
     * Méthode pour supprimer un modèle de vehicule
     * @param model le modèle de vehicule à supprimer
     */
    @Transactional
    public void deleteModel(Model model) {
        modelRepository.delete(model);
    }

    /**
     * Méthode pour ajouter un modèle de vehicule
     * @param model le modèle de vehicule à ajouter
     */
    @Transactional
    public void save(Model model) {
        modelRepository.save(model);
    }

    @Transactional
    public Model findById(int id) {
        return modelRepository.findById(id).orElse(null);
    }

}
