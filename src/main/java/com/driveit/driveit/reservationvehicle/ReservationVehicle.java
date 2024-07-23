package com.driveit.driveit.reservationvehicle;


import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.vehicle.Vehicle;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Cette classe est une entité JPA qui représente un collaborateur participant à un covoiturage.
 * /!\ Cette classe est une table de jointure entre les tables "carpooling" et "collaborator".
 * car elle contient des clés étrangères vers ces deux tables + une colonne "status". (obligation de créer une classe pour attacher une propriété à une table de jointure)
 * Un collaborateur participant à un covoiturage est caractérisé par les informations suivantes :
 * - Un identifiant unique (généré automatiquement)
 * - Un covoiturage
 * - Un collaborateur
 * - Un statut (acceptée, en attente, refusée)
 * **/
@Entity
@Table(name = "reservation_vehicule")
public class ReservationVehicle {

    /**
     * Identifiant unique de la table de jointure
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Date et heure de début de la réservation
     */
    @Column(name="date_heure_debut")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startDate;

    /**
     * Date et heure de fin de la réservation
     */
    @Column(name="date_heure_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endDate;

    /**
     * Véhicule de service
     */
    @ManyToOne
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicle vehicle;

    /**
     * Collaborateur
     */
    @ManyToOne
    @JoinColumn(name = "collaborator_id", nullable = false)
    private Collaborator collaborator;


    /**
     * Constructeur par défaut
     */
    public ReservationVehicle() {
    }

    /**
     * Constructeur avec paramètres
     *
     * @param startDate date et heure de début de la réservation
     * @param endDate date et heure de fin de la réservation
     * @param vehicle véhicule de service
     * @param collaborator collaborateur
     */
    public ReservationVehicle(LocalDateTime startDate, LocalDateTime endDate, Vehicle vehicle, Collaborator collaborator) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.vehicle = vehicle;
        this.collaborator = collaborator;
    }

    /**
     * Getters et Setters
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getters et Setters
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Getters et Setters
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Getters et Setters
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Getters et Setters
     */
    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    /**
     * Getters et Setters
     */
    public int getId() {
        return id;
    }

    /**
     * Getters et Setters
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Getters et Setters
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Getters et Setters
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Getters et Setters
     */
    public Collaborator getCollaborator() {
        return collaborator;
    }
}

