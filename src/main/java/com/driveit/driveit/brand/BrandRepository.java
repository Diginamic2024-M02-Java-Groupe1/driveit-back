package com.driveit.driveit.brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Cette interface permet de gérer les marques dans la base de données.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
