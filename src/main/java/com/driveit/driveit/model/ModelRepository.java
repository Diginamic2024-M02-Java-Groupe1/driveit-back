package com.driveit.driveit.model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Cette interface permet de gérer les modèles dans la base de données.

 */
@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

    Optional<Model> findByName(String name);
}
