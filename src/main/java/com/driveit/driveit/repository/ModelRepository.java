package com.driveit.driveit.repository;
import com.driveit.driveit.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Cette interface permet de gérer les modèles dans la base de données.

 */
@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {
}
