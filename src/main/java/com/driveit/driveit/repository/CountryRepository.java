package com.driveit.driveit.repository;

import com.driveit.driveit.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Cette interface permet de gérer les pays dans la base de données.

 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
}
