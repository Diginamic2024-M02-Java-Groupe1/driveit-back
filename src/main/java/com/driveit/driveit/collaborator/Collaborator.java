package com.driveit.driveit.collaborator;

import com.driveit.driveit.carpooling.Carpooling;
import com.driveit.driveit.reservationcarpooling.ReservationCarpooling;
import com.driveit.driveit.vehicle.Vehicle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * Identifiant unique du collaborateur
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Nom de famille du collaborateur
     */
    @NotNull(message = "Le nom de famille est obligatoire")
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    /**
     * Prénom du collaborateur
     */
    @NotNull(message = "Le prénom est obligatoire")
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    /**
     * Rôle du collaborateur
     */
//    @NotNull(message = "Le rôle est obligatoire")
//    @Column(length = 50, nullable = false)
//    private String role;


    // Liste des covoiturages organisés par le collaborateur
    @OneToMany(mappedBy = "organizer")
    private List<Carpooling> organizedCarpoolings;

    // Liste des véhicules du collaborateur
    @ManyToMany
    @JoinTable(
            name = "collaborator_vehicle",
            joinColumns = @JoinColumn(name = "id_collaborator"),
            inverseJoinColumns = @JoinColumn(name = "id_vehicle")
    )
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "collaborator")
    private List<ReservationCarpooling> reservationCarpoolings = new ArrayList<>();

    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> authorities;

    // Constructeur par défaut

    public Collaborator() {}

    /**
     *
     * @param email
     * @param password
     * @param lastName
     * @param firstName
     * @param authorities
     */
    public Collaborator(String email, String password, String lastName, String firstName, String... authorities) {
        this.email = email;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.authorities = Arrays.stream(authorities).map(SimpleGrantedAuthority::new).map(GrantedAuthority.class::cast).toList();
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
     * Retourne la liste des covoiturages organisés par le collaborateur.
     * @return La liste des covoiturages organisés par le collaborateur.
     */
    public List<Carpooling> getOrganizedCarpoolings() {
        return organizedCarpoolings;
    }

    /**
     * Modifie la liste des covoiturages organisés par le collaborateur.
     * @param organizedCarpoolings La nouvelle liste des covoiturages organisés par le collaborateur.
     */
    public void setOrganizedCarpoolings(List<Carpooling> organizedCarpoolings) {
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

    public List<ReservationCarpooling> getReservationCollaborators() {
        return reservationCarpoolings;
    }

    public void setReservationCollaborators(List<ReservationCarpooling> reservationCarpoolings) {
        this.reservationCarpoolings = reservationCarpoolings;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
