package com.driveit.driveit.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Cette interface permet de gérer les marques dans la base de données.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    /**
     * Cette méthode permet de trouver une marque par son nom et de limiter à 1 le nombre de résultats.
     */
    Optional<Brand> findByName(String name);
}
