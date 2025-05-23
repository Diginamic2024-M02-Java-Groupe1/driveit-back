package com.driveit.driveit.carpooling;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Cette interface permet de gérer les covoiturages dans la base de données.
 */
@Repository
public interface CarpoolingRepository extends JpaRepository<Carpooling, Integer> {

    List<Carpooling> findByDepartureAddress_CityZipCode_CityIgnoreCaseAndArrivalAddress_CityZipCode_CityIgnoreCaseAndDepartureDateBetween(
            String departureCity,
            String arrivalCity,
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );
}
