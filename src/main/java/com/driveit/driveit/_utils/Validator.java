package com.driveit.driveit._utils;

import com.driveit.driveit.address.Address;
import com.driveit.driveit.carpooling.Carpooling;

public class Validator {


    /**
     * Cette méthode permet de valider une adresse.
     * Elle vérifie si le numéro de rue, le nom de rue, la ville et le code postal sont renseignés.
     *
     * @param address l'adresse à valider
     * @throws IllegalArgumentException si l'adresse n'est pas valide
     */
    public static void validateAddress(Address address) throws IllegalArgumentException {
        if (address.getStreetNumber() == null || address.getStreetNumber().isEmpty()) {
            throw new IllegalArgumentException("Street number is required");
        }
        if (address.getStreetName() == null || address.getStreetName().isEmpty()) {
            throw new IllegalArgumentException("Street name is required");
        }
        if (address.getCityZipCode() == null) {
            throw new IllegalArgumentException("City zip code is required");
        }
        if (address.getCityZipCode().getCity() == null || address.getCityZipCode().getCity().isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }
    }

    /**
     * Cette méthode permet de valider un covoiturage.
     * Elle vérifie si la date de départ, la date d'arrivée, l'organisateur, l'adresse de départ et l'adresse d'arrivée sont renseignés.
     *
     * @param carpooling le covoiturage à valider
     * @throws IllegalArgumentException si le covoiturage n'est pas valide
     */
    public static void validateCarpooling(Carpooling carpooling) throws IllegalArgumentException {
        if (carpooling.getDepartureDate() == null) {
            throw new IllegalArgumentException("Departure date is required");
        }
        if (carpooling.getArrivalDate() == null) {
            throw new IllegalArgumentException("Arrival date is required");
        }
        if (carpooling.getOrganizer() == null) {
            throw new IllegalArgumentException("Organizer is required");
        }
        if (carpooling.getDepartureAddress() == null) {
            throw new IllegalArgumentException("Departure address is required");
        }
        if (carpooling.getArrivalAddress() == null) {
            throw new IllegalArgumentException("Arrival address is required");
        }
    }
}
