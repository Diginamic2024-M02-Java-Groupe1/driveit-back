package com.driveit.driveit.cityzipcode;

import com.driveit.driveit.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityZipcodeRepository extends JpaRepository<CityZipCode, Integer> {

    @Query("SELECT DISTINCT c.city FROM CityZipCode c")
    List<String> findAllCities();

    @Query("SELECT c FROM CityZipCode c WHERE c.city = ?1")
    CityZipCode findByCity(String city);

    @Query("SELECT c FROM CityZipCode c WHERE c.zipCode = ?1")
    CityZipCode findByZipcode(String zipcode);

    @Query("SELECT c FROM CityZipCode c WHERE c.city = ?1 AND c.zipCode = ?2")
    CityZipCode findByCityAndZipcode(String city, String zipcode);
}

