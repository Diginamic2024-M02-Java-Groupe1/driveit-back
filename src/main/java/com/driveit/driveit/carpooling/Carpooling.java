package com.driveit.driveit.carpooling;

import com.driveit.driveit.address.Address;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.reservationcarpooling.ReservationCarpooling;
import com.driveit.driveit.vehicle.Vehicle;
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

    /**
     * Identifiant du covoiturage
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Date de départ du covoiturage
     */
    @Column(name = "departure_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime departureDate;

    /**
     * Date d'arrivée du covoiturage
     */
    @Column(name = "arrival_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime arrivalDate;

    /**
     * Organisateur du covoiturage
     */
    @ManyToOne
    private Collaborator organizer;

    /**
     * Adresse de départ du covoiturage
     */
    @ManyToOne
    @JoinColumn(name = "departure_address_id", nullable = false)
    private Address departureAddress;

    /**
     * Adresse d'arrivée du covoiturage
     */
    @ManyToOne
    @JoinColumn(name = "arrival_address_id", nullable = false)
    private Address arrivalAddress;

    /**
     * Liste des réservations du covoiturage
     */
    @OneToMany(mappedBy = "carpooling")
    private List<ReservationCarpooling> reservationCarpoolings;

    // Véhicule du covoiturage
    @ManyToOne
    @JoinColumn(name = "vehicle_id",nullable = false)
    private Vehicle vehicle;



    /**
     * Constructeur par défaut
     */
    public Carpooling() {}

    /**
     * Constructeur avec paramètres
     *
     * @param departureDate : la date de départ du covoiturage
     * @param arrivalDate : la date d'arrivée du covoiturage
     * @param organizer : l'organisateur du covoiturage
     * @param departureAddress : l'adresse de départ du covoiturage
     * @param arrivalAddress : l'adresse d'arrivée du covoiturage
     * @param reservationCarpoolings : la liste des réservations du covoiturage
     */
    public Carpooling(LocalDateTime departureDate, LocalDateTime arrivalDate, Collaborator organizer, Address departureAddress, Address arrivalAddress, List<ReservationCarpooling> reservationCarpoolings, Vehicle vehicle) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.organizer = organizer;
        this.departureAddress = departureAddress;
        this.arrivalAddress = arrivalAddress;
        this.reservationCarpoolings = reservationCarpoolings;
        this.vehicle = vehicle;
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
    public List<ReservationCarpooling> getReservations() {
        return reservationCarpoolings;
    }

    /**
     * Modifie la liste des participants du covoiturage.
     * @param reservationCarpoolings La nouvelle liste des participants du covoiturage.
     */
    public void setReservations(List<ReservationCarpooling> reservationCarpoolings) {
        this.reservationCarpoolings = reservationCarpoolings;
    }

    /**
     * Retourne le véhicule utilisé pour le covoiturage.
     * @return Le véhicule utilisé pour le covoiturage.
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Modifie le véhicule utilisé pour le covoiturage.
     * @param vehicle Le nouveau véhicule utilisé pour le covoiturage.
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
