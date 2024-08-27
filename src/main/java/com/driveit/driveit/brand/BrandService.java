package com.driveit.driveit.brand;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Cette classe est un service qui gère les opérations sur les marques
 * Elle est utilisée pour supprimer ou ajouter une marque etc ...
 *
 * @see Brand
 * @see BrandRepository
 */
@Service
public class BrandService {

    /**
     * Le repository des marques
     */
    @Autowired
    private final BrandRepository brandRepository;


    /**
     * Constructeur du service des marques
     * @param brandRepository le repository des marques
     */
    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    /**
     * Méthode pour supprimer une marque
     * @param brand la marque à supprimer
     */
    @Transactional
    public void deleteBrand(Brand brand) {
        brandRepository.delete(brand);
    }

    /**
     * Méthode pour ajouter une marque
     * @param brand la marque à ajouter
     */
    @Transactional
    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    @Transactional
    public Brand findByName(String name) {
        return brandRepository.findByName(name).orElse(null);
    }
}
