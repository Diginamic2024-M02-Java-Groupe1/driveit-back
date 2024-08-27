package com.driveit.driveit._utils;

import com.driveit.driveit.address.Address;
import com.driveit.driveit.carpooling.Carpooling;
import com.driveit.driveit.carpooling.CarpoolingCreateDto;

import java.util.ArrayList;
import java.util.Collection;

public class Validator {


    /**
     * Cette méthode permet de valider une adresse.
     * Elle vérifie si le numéro de rue, le nom de rue, la ville et le code postal sont renseignés.
     *
     * @param address l'adresse à valider
     * @return
     * @throws IllegalArgumentException si l'adresse n'est pas valide
     */
    public static ArrayList<String> validateAddress(CarpoolingCreateDto.Address address) throws IllegalArgumentException {
        ArrayList<String> errors = new ArrayList<>();
        if (address.number() == null || address.number().isEmpty()) {
            errors.add("Street number is required");
        }
        if (address.street() == null || address.street().isEmpty()) {
            errors.add("Street name is required");
        }
        if (address.city() == null || address.city().isEmpty()) {
            errors.add("City is required");
        }
        if (address.zipcode() == null || address.zipcode().isEmpty()) {
            errors.add("Zip code is required");
        }
        return errors;
    }

    /**
     * Cette méthode permet de valider deux adresses.
     * Elle vérifie si le numéro de rue, le nom de rue, la ville et le code postal sont renseignés.
     *
     * @param departureAddress l'adresse de départ à valider
     * @param arrivalAddress l'adresse d'arrivée à valider
     * @return ArrayList<String> errors
     * @throws IllegalArgumentException si l'une des adresses n'est pas valide
     */
    public static ArrayList<String> validateBothAddress(CarpoolingCreateDto.Address departureAddress, CarpoolingCreateDto.Address arrivalAddress) throws IllegalArgumentException {
        ArrayList<String> errors = new ArrayList<>();

        if (departureAddress.street() == null
                || arrivalAddress.street() == null
                || departureAddress.street().isEmpty()
                || arrivalAddress.street().isEmpty()
                || departureAddress.number() == null
                || arrivalAddress.number() == null
                || departureAddress.number().isEmpty()
                || arrivalAddress.number().isEmpty()
        )
        {
            errors.add("Some address fields are required");
        }
        if (departureAddress.city() == null
                || arrivalAddress.city() == null
                || departureAddress.city().isEmpty()
                || arrivalAddress.city().isEmpty()
                || departureAddress.zipcode() == null
                || arrivalAddress.zipcode() == null
                || departureAddress.zipcode().isEmpty()
                || arrivalAddress.zipcode().isEmpty()) {
            errors.add("City and zip code are required");
        }

        return errors;
    }


    /**
     * Cette méthode permet de valider un covoiturage.
     * Elle vérifie si la date de départ, la date d'arrivée, l'organisateur, l'adresse de départ et l'adresse d'arrivée sont renseignés.
     *
     * @param carpooling le covoiturage à valider
     * @throws IllegalArgumentException si le covoiturage n'est pas valide
     */
    public static ArrayList<String> validateCarpooling(CarpoolingCreateDto carpooling) throws IllegalArgumentException {
        ArrayList<String> errors = new ArrayList<>();

        if (carpooling.departureDateTime() == null || carpooling.arrivalDateTime() == null) {
            errors.add("Both departure and arrival dates are required");
        } else {
            if (carpooling.departureDateTime().isAfter(carpooling.arrivalDateTime())) {
                errors.add("Departure date must be before arrival date");
            } else if (carpooling.arrivalDateTime().isBefore(carpooling.departureDateTime())) {
                errors.add("Arrival date must be after departure date");
            }
        }
        if (carpooling.departureAddress() == null || carpooling.arrivalAddress() == null) {
            errors.add("Both departure and arrival addresses are required");
        } else {
            errors.addAll(validateBothAddress(carpooling.departureAddress(), carpooling.arrivalAddress()));
        }
        return errors;
    }


}
