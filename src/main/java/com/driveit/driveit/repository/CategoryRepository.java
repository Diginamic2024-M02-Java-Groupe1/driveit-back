package com.driveit.driveit.repository;

import com.driveit.driveit.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Cette interface permet de gérer les catégories des véhicules dans la base de données.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
