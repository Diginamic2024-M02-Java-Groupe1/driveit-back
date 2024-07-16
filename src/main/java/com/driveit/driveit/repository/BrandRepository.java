package com.driveit.driveit.repository;
import com.driveit.driveit.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Cette interface permet de gérer les marques dans la base de données.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
