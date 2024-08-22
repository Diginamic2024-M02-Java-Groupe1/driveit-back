package com.driveit.driveit.category;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * Cette classe est un service qui gère les opérations sur les catégories
 * Elle est utilisée pour supprimer ou ajouter une catégorie etc ...
 *
 * @see Category
 * @see CategoryRepository
 */
@Service
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

    @Transactional
    public Category findById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
