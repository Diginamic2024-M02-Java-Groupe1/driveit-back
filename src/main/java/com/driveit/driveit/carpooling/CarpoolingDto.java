package com.driveit.driveit.carpooling;


import com.driveit.driveit.address.AddressDto;
import com.driveit.driveit.collaborator.CollaboratorDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Cette classe est un DTO qui permet de manipuler les données des covoiturages
 * Elle est utilisée pour récupérer les données des covoiturages depuis le front-end
 * et les envoyer au back-end et vice-versa.
 */
public class CarpoolingDto {

    /**
     * L'identifiant du covoiturage
     */
    private int id;

    /**
     * La date de départ du covoiturage
     */
    private LocalDateTime departureDate;

    /**
     * La date d'arrivée du covoiturage
     */
    private LocalDateTime arrivalDate;

    /**
     * L'identifiant de l'organisateur du covoiturage
     */
    private CollaboratorDto organizer;

    /**
     * L'adresse de départ du covoiturage
     */
    private AddressDto departureAddress;

    /**
     * L'adresse d'arrivée du covoiturage
     */
    private AddressDto arrivalAddress;

    /**
     * La liste des participants du covoiturage
     */
    private List<CollaboratorDto> participants;

    /**
     * Constructeur par défaut
     */
    public CarpoolingDto() {
    }

    /**
     * Constructeur avec paramètres
     *
     * @param id               : l'identifiant du covoiturage
     * @param departureDate    : la date de départ du covoiturage
     * @param arrivalDate      : la date d'arrivée du covoiturage
     * @param organizer        : l'identifiant de l'organisateur du covoiturage
     * @param departureAddress : l'adresse de départ du covoiturage
     * @param arrivalAddress   : l'adresse d'arrivée du covoiturage
     * @param participants     : la liste des participants du covoiturage
     */
    public CarpoolingDto(int id, LocalDateTime departureDate, LocalDateTime arrivalDate, CollaboratorDto organizer, AddressDto departureAddress, AddressDto arrivalAddress, List<CollaboratorDto> participants) {
        this.id = id;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.organizer = organizer;
        this.departureAddress = departureAddress;
        this.arrivalAddress = arrivalAddress;
        this.participants = participants;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public CollaboratorDto getOrganizer() {
        return organizer;
    }

    public void setOrganizer(CollaboratorDto organizer) {
        this.organizer = organizer;
    }

    public AddressDto getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(AddressDto departureAddress) {
        this.departureAddress = departureAddress;
    }

    public List<CollaboratorDto> getParticipants() {
        return participants;
    }

    public void setParticipants(List<CollaboratorDto> participants) {
        this.participants = participants;
    }

    public AddressDto getArrivalAddress() {
        return arrivalAddress;
    }

    public void setArrivalAddress(AddressDto arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
    }


}
