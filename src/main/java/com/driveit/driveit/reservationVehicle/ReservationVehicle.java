package com.driveit.driveit.reservationVehicle;


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

    @Column(name="date_heure_debut")
    private LocalDateTime startDate;
    @Column(name="date_heure_fin")
    private LocalDateTime endDate;



    @ManyToOne
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicle vehicle;


    @ManyToOne
    @JoinColumn(name = "collaborator_id", nullable = false)
    private Collaborator collaborator;

    // Constructeurs

    /**
     * Constructeur par défaut
     */
    public ReservationVehicle() {
    }

    public ReservationVehicle(int id, LocalDateTime startDate, LocalDateTime endDate, Vehicle vehicle, Collaborator collaborator) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.vehicle = vehicle;
        this.collaborator = collaborator;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }
}

