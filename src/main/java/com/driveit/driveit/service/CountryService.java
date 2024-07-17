package com.driveit.driveit.service;


import com.driveit.driveit.entity.Country;
import com.driveit.driveit.repository.CountryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Cette classe est un service qui gère les opérations sur les pays
 * Elle est utilisée pour supprimer ou ajouter un pays etc ...
 *
 * @see Country
 * @see CountryRepository
 */
@Service
public class CountryService {
    /**
     * Le repository des pays
     */
    CountryRepository countryRepository;

    /**
     * Constructeur du service des pays
     * @param countryRepository le repository des pays
     */
    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * Méthode pour supprimer un pays
     * @param country le pays à supprimer
     */
    @Transactional
    public void deleteCountry(Country country) {
        countryRepository.delete(country);
    }

    /**
     * Méthode pour ajouter un pays
     * @param country le pays à ajouter
     */
    @Transactional
    public void save(Country country) {
        countryRepository.save(country);
    }

}
