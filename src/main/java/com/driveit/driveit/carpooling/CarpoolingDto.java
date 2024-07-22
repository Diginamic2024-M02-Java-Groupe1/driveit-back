package com.driveit.driveit.carpooling;


import com.driveit.driveit.address.AddressDto;
import com.driveit.driveit.collaborator.CollaboratorDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Cette classe est un DTO qui permet de manipuler les données des covoiturages
 * Elle est utilisée pour récupérer les données des covoiturages depuis le front-end
 * et les envoyer au back-end et vice-versa.
 *
 * @param id               L'identifiant du covoiturage
 * @param departureDate    La date de départ du covoiturage
 * @param arrivalDate      La date d'arrivée du covoiturage
 * @param organizer        L'identifiant de l'organisateur du covoiturage
 * @param departureAddress L'adresse de départ du covoiturage
 * @param arrivalAddress   L'adresse d'arrivée du covoiturage
 * @param participants     La liste des participants du covoiturage
 */
public record CarpoolingDto(
        int id,
        LocalDateTime departureDate,
        LocalDateTime arrivalDate,
        CollaboratorDto organizer,
        AddressDto departureAddress,
        AddressDto arrivalAddress,
        List<CollaboratorDto> participants)
{
}
