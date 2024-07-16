package com.driveit.driveit.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * Cette classe représente un collaborateur de l'entreprise.
 * Un collaborateur est caractérisé par les informations suivantes :
 * - Un identifiant unique (généré automatiquement)
 * - Un nom de famille
 * - Un prénom
 * - Un rôle (ex: chauffeur, passager, ...)
 * - Une liste de covoiturages organisés
 * - Une liste de véhicules
 */
@Entity
@Table(name = "collaborator")
public class Collaborator {

    // Identifiant unique du collaborateur
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Nom de famille du collaborateur
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    // Prénom du collaborateur
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    // Rôle du collaborateur
    @Column(name = "role", length = 50, nullable = false)
    private String role;


    // Liste des covoiturages organisés par le collaborateur
    @OneToMany(mappedBy = "collaborator")
    private List<CarpoolingCollaborator> organizedCarpoolings;

    // Liste des véhicules du collaborateur
    @ManyToMany
    @JoinTable(
            name = "collaborator_vehicle",
            joinColumns = @JoinColumn(name = "id_collaborator"),
            inverseJoinColumns = @JoinColumn(name = "id_vehicle")
    )
    private List<Vehicle> vehicles;


    // Constructeur par défaut

    public Collaborator() {}

    /**
     * Constructeur avec paramètres
     *
     * @param id : l'identifiant du collaborateur
     * @param lastName : le nom de famille du collaborateur
     * @param firstName : le prénom du collaborateur
     * @param role : le rôle du collaborateur
     * @param organizedCarpoolings : la liste des covoiturages organisés par le collaborateur
     * @param vehicles : la liste des véhicules du collaborateur
     */
    public Collaborator(int id, String lastName, String firstName, String role, List<CarpoolingCollaborator> organizedCarpoolings, List<Vehicle> vehicles) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.role = role;
        this.organizedCarpoolings = organizedCarpoolings;
        this.vehicles = vehicles;
    }

    // Getters and Setters

    /**
     * Retourne l'identifiant du collaborateur.
     * @return L'identifiant du collaborateur.
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du collaborateur.
     * @param id Le nouvel identifiant du collaborateur.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le nom de famille du collaborateur.
     * @return Le nom de famille du collaborateur.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Modifie le nom de famille du collaborateur.
     * @param lastName Le nouveau nom de famille du collaborateur.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retourne le prénom du collaborateur.
     * @return Le prénom du collaborateur.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Modifie le prénom du collaborateur.
     * @param firstName Le nouveau prénom du collaborateur.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retourne le rôle du collaborateur.
     * @return Le rôle du collaborateur.
     */
    public String getRole() {
        return role;
    }

    /**
     * Modifie le rôle du collaborateur.
     * @param role Le nouveau rôle du collaborateur.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Retourne la liste des covoiturages organisés par le collaborateur.
     * @return La liste des covoiturages organisés par le collaborateur.
     */
    public List<CarpoolingCollaborator> getOrganizedCarpoolings() {
        return organizedCarpoolings;
    }

    /**
     * Modifie la liste des covoiturages organisés par le collaborateur.
     * @param organizedCarpoolings La nouvelle liste des covoiturages organisés par le collaborateur.
     */
    public void setOrganizedCarpoolings(List<CarpoolingCollaborator> organizedCarpoolings) {
        this.organizedCarpoolings = organizedCarpoolings;
    }

    /**
     * Retourne la liste des véhicules du collaborateur.
     * @return La liste des véhicules du collaborateur.
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Modifie la liste des véhicules du collaborateur.
     * @param vehicles La nouvelle liste des véhicules du collaborateur.
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
