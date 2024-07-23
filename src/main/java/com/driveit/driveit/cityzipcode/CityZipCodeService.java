package com.driveit.driveit.cityzipcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityZipCodeService {

    @Autowired
    private CityZipcodeRepository cityZipCodeRepository;


    /**
     * Cette méthode permet de sauvegarder une ville et son code postal dans la base de données.
     * @param cityZipCode : la ville et son code postal.
     * @return la ville et son code postal sauvegardé.
     */
    public CityZipCode save(CityZipCode cityZipCode) {
        return cityZipCodeRepository.save(cityZipCode);
    }

    /**
     * Cette méthode permet de récupérer une ville et son code postal par son nom.
     * @param city : le nom de la ville.
     * @return la ville et son code postal.
     */
    public CityZipCode getCityZipCodeByCity(String city) {
        return cityZipCodeRepository.findByCity(city);
    }

    /**
     * Cette méthode permet de récupérer une ville et son code postal par son code postal.
     * @param zipcode : le code postal de la ville.
     * @return la ville et son code postal.
     */
    public CityZipCode getCityZipCodeByZipcode(String zipcode) {
        return cityZipCodeRepository.findByZipcode(zipcode);
    }

    /**
     * Cette méthode permet de récupérer une ville et son code postal par son nom et son code postal.
     * @param city : le nom de la ville.
     * @param zipcode : le code postal de la ville.
     * @return la ville et son code postal.
     */
    public CityZipCode getCityZipCodeByCityAndZipcodeOrCreate(String city, String zipcode) {
        CityZipCode cityZipCode = cityZipCodeRepository.findByCityAndZipcode(city, zipcode);
        if (cityZipCode == null) {
            cityZipCode = new CityZipCode(city, zipcode);
            cityZipCode = save(cityZipCode);
        }
        return cityZipCode;
    }





}
