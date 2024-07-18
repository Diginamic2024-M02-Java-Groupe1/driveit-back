package com.driveit.driveit.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Cette interface permet de gérer les adresses dans la base de données.

 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
