package com.driveit.driveit.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import java.util.List;

/**
 * Cette entité JPA représente un covoiturage.
 * Un covoiturage est caractérisé par les informations suivantes :
 * - Un identifiant unique (généré automatiquement)
 * - Une date de départ
 * - Une date d'arrivée
 * - Un organisateur
 * - Une adresse de départ
 * - Une adresse d'arrivée
 * - Une liste de participants
 */
@Entity
@Table(name = "carpooling")
public class Carpooling {

    // Identifiant unique du covoiturage
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Date de départ du covoiturage
    @Column(name = "departure_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime departureDate;

    // Date d'arrivée du covoiturage
    @Column(name = "arrival_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime arrivalDate;

    // Organisateur du covoiturage
    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private Collaborator organizer;

    // Adresse de départ du covoiturage
    @ManyToOne
    @JoinColumn(name = "departure_address_id", nullable = false)
    private Address departureAddress;

    // Adresse d'arrivée du covoiturage
    @ManyToOne
    @JoinColumn(name = "arrival_address_id", nullable = false)
    private Address arrivalAddress;

    // Liste des participants du covoiturage
    @OneToMany(mappedBy = "carpooling")
    private List<CarpoolingCollaborator> participants;


    // Constructeur par défaut

    public Carpooling() {}

    /**
     * Constructeur avec paramètres
     *
     * @param id : l'identifiant du covoiturage
     * @param departureDate : la date de départ du covoiturage
     * @param arrivalDate : la date d'arrivée du covoiturage
     * @param organizer : l'organisateur du covoiturage
     * @param departureAddress : l'adresse de départ du covoiturage
     * @param arrivalAddress : l'adresse d'arrivée du covoiturage
     * @param participants : la liste des participants du covoiturage
     */
    public Carpooling(int id, LocalDateTime departureDate, LocalDateTime arrivalDate, Collaborator organizer, Address departureAddress, Address arrivalAddress, List<CarpoolingCollaborator> participants) {
        this.id = id;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.organizer = organizer;
        this.departureAddress = departureAddress;
        this.arrivalAddress = arrivalAddress;
        this.participants = participants;
    }

    // Getters and Setters

    /**
     * Retourne l'identifiant du covoiturage.
     * @return L'identifiant du covoiturage.
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du covoiturage.
     * @param id Le nouvel identifiant du covoiturage.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne la date de départ du covoiturage.
     * @return La date de départ du covoiturage.
     */
    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    /**
     * Modifie la date de départ du covoiturage.
     * @param departureDate La nouvelle date de départ du covoiturage.
     */
    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * Retourne la date d'arrivée du covoiturage.
     * @return La date d'arrivée du covoiturage.
     */
    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Modifie la date d'arrivée du covoiturage.
     * @param arrivalDate La nouvelle date d'arrivée du covoiturage.
     */
    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    /**
     * Retourne l'organisateur du covoiturage.
     * @return L'organisateur du covoiturage.
     */
    public Collaborator getOrganizer() {
        return organizer;
    }

    /**
     * Modifie l'organisateur du covoiturage.
     * @param organizer Le nouvel organisateur du covoiturage.
     */
    public void setOrganizer(Collaborator organizer) {
        this.organizer = organizer;
    }

    /**
     * Retourne l'adresse de départ du covoiturage.
     * @return L'adresse de départ du covoiturage.
     */
    public Address getDepartureAddress() {
        return departureAddress;
    }

    /**
     * Modifie l'adresse de départ du covoiturage.
     * @param departureAddress La nouvelle adresse de départ du covoiturage.
     */
    public void setDepartureAddress(Address departureAddress) {
        this.departureAddress = departureAddress;
    }

    /**
     * Retourne l'adresse d'arrivée du covoiturage.
     * @return L'adresse d'arrivée du covoiturage.
     */
    public Address getArrivalAddress() {
        return arrivalAddress;
    }

    /**
     * Modifie l'adresse d'arrivée du covoiturage.
     * @param arrivalAddress La nouvelle adresse d'arrivée du covoiturage.
     */
    public void setArrivalAddress(Address arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
    }

    /**
     * Retourne la liste des participants du covoiturage.
     * @return La liste des participants du covoiturage.
     */
    public List<CarpoolingCollaborator> getParticipants() {
        return participants;
    }

    /**
     * Modifie la liste des participants du covoiturage.
     * @param participants La nouvelle liste des participants du covoiturage.
     */
    public void setParticipants(List<CarpoolingCollaborator> participants) {
        this.participants = participants;
    }
}
