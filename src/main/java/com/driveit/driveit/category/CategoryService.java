package com.driveit.driveit.category;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Cette classe est un service qui gère les opérations sur les catégories
 * Elle est utilisée pour supprimer ou ajouter une catégorie etc ...
 *
 * @see Category
 * @see CategoryRepository
 */
public class CategoryService {

    /**
     * Le repository des catégories
     */
    private final CategoryRepository categoryRepository;

    /**
     * Constructeur du service des catégories
     *
     * @param categoryRepository le repository des catégories
     */
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Méthode pour supprimer une catégorie
     *
     * @param category la catégorie à supprimer
     */
    @Transactional
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    /**
     * Méthode pour sauvegarde une catégorie
     *
     * @param category la catégorie à ajouter
     */
    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }
}
