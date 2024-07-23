package com.driveit.driveit.carpooling;

import com.driveit.driveit.address.AddressDto;

import java.time.LocalDateTime;

/**
 * Cette classe est un DTO qui permet de manipuler les données des covoiturages
 * Elle est utilisée pour récupérer les données des covoiturages depuis le front-end
 * et les envoyer au back-end et vice-versa.
 *
 * @param departureDate    La date de départ du covoiturage
 * @param arrivalDate      La date d'arrivée du covoiturage
 * @param organizerId        L'identifiant de l'organisateur du covoiturage
 * @param departureAddress L'adresse de départ du covoiturage
 * @param arrivalAddress   L'adresse d'arrivée du covoiturage
 * @param vehicleId          Le véhicule du covoiturage
 */
public record BodyCarpoolingDto(
        LocalDateTime departureDate,
        LocalDateTime arrivalDate,
        int organizerId,
        AddressDto departureAddress,
        AddressDto arrivalAddress,
        int vehicleId)
{
}