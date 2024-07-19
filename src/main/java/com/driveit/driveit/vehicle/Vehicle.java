package com.driveit.driveit.vehicle;

import com.driveit.driveit.brand.Brand;
import com.driveit.driveit.carpooling.Carpooling;
import com.driveit.driveit.category.Category;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.model.Model;
import com.driveit.driveit.motorization.Motorization;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe est une entité JPA qui représente un véhicule.
 * Un véhicule est caractérisé par les informations suivantes :
 * - Un identifiant unique (généré automatiquement)
 * - Une immatriculation (ex : 1234 AB 01)
 * - Un nombre de places assises
 * - Un service (location, transport, ...)
 * - Une URL (pour une image)
 * - Une émission de CO2 (en g/km)
 * - Un statut (disponible, en réparation, ...)
 * - Une liste de collaborateurs
 * - Une liste de covoiturages
 * - Une motorisation (essence, diesel, électrique, hybride)
 * - Un modèle (Renault, Peugeot, Citroën, ...)
 * - Une catégorie (citadine, berline, break, ...)
 */
@Entity
@Table(name = "vehicle")
public class Vehicle {

    /**
     * Identifiant unique du véhicule
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Immatriculation du véhicule
    @Pattern(regexp = "[A-Z]{2}-\\d{3}-[A-Z]{2}", message = "L'immatriculation doit être saisie au format XX-000-XX.")
    @NotNull(message = "L'immatriculation du véhicule doit être renseignée.")
    @Column(name = "registration", length = 50, nullable = false, unique = true)
    private String registration;

    @NotNull(message = "Le nombre de places du véhicule doit être renseigné.")
    @Column(name = "number_of_seats", nullable = false)
    @Min(value = 1, message = "Le nombre de places assises doit être supérieur ou égal à 1.")
    private int numberOfSeats;

    // Service du véhicule
    @NotNull(message = "Le service du véhicule doit être renseigné.")
    @Column(name = "service")
    private boolean isService;

    /**
     * URL de l'image du véhicule
     */
    @NotNull(message = "L'URL de l'image du véhicule ne peut pas être nulle.")
    private String url;

    // Émission de CO2 du véhicule
    @NotNull(message = "L'émission de CO2 du véhicule doit être renseignée.")
    @Column(name = "emission")
    private double emission;

    /**
     * Statut du véhicule
     */
    @NotNull(message = "Le statut du véhicule doit être renseigné.")
    @Enumerated(EnumType.STRING)
    private StatusVehicle status;

    /**
     * Liste des collaborateurs du véhicule
     * @ManyToMany : Un véhicule peut être utilisé par plusieurs collaborateurs et un collaborateur peut utiliser plusieurs véhicules
     */
    @ManyToMany(mappedBy = "vehicles")
    private List<Collaborator> collaborators = new ArrayList<>();

    /**
     * Liste des covoiturages du véhicule
     * @OneToMany : Un véhicule peut être utilisé pour plusieurs covoiturages
     */
    @OneToMany(mappedBy = "vehicle")
    private List<Carpooling> carpoolings = new ArrayList<>();

    /**
     * Motorisation du véhicule
     * @ManyToOne : Plusieurs véhicules peuvent avoir la même motorisation
     */
    @NotNull(message = "La motorisation du véhicule doit être renseignée.")
    @ManyToOne
    private Motorization motorization;

    /**
     * Marque du véhicule
     * @ManyToOne : Plusieurs véhicules peuvent avoir la même marque
     */
    @NotNull(message = "Le modèle du véhicule doit être renseigné.")
    @ManyToOne
    private Model model;

    /**
     * Catégorie du véhicule
     * @ManyToOne : Plusieurs véhicules peuvent être de la même catégorie
     */
    @NotNull(message = "La catégorie du véhicule doit être renseignée.")
    @ManyToOne
    private Category category;

    // Constructeur par défaut

    public Vehicle() {}

    /**
     * Constructeur avec paramètres
     *
     * @param registration : l'immatriculation du véhicule
     * @param numberOfSeats : le nombre de places assises du véhicule
     * @param isService : booleen indiquant si le véhicule est un vehicule de service
     * @param url : l'URL de l'image du véhicule
     * @param emission : l'émission de CO2 du véhicule
     * @param status : le statut du véhicule
     * @param motorization : la motorisation du véhicule
     * @param model : le modèle du véhicule
     * @param category : la catégorie du véhicule
     */
    public Vehicle(String registration, int numberOfSeats, boolean isService, String url, double emission, StatusVehicle status, Motorization motorization, Model model, Category category) {
        this.registration = registration;
        this.numberOfSeats = numberOfSeats;
        this.isService = isService;
        this.url = url;
        this.emission = emission;
        this.status = status;
        this.motorization = motorization;
        this.model = model;
        this.category = category;
    }

    /**
     * Retourne l'identifiant du véhicule.
     * @return {@link Integer}
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne l'immatriculation du véhicule.
     * @return {@link String}
     */
    public String getRegistration() {
        return registration;
    }

    /**
     * Modifie l'immatriculation du véhicule.
     * @param registration : immatriculation
     */
    public void setRegistration(String registration) {
        this.registration = registration;
    }

    /**
     * Retourne le nombre de places assises du véhicule.
     * @return {@link Integer}
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Modifie le nombre de places assises du véhicule.
     * @param numberOfSeats : nombre de places assises
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**

     * Retourne le boolean indiquant si le véhicule est un véhicule de service.
     *
     * @return boolean
     */
    public boolean getService() {
        return isService;
    }

    /**
     * Modifie le service du véhicule.
     * @param isService : Location, Transport, ...
     */
    public void setService(boolean isService) {
        this.isService = isService;
    }

    /**
     * Retourne l'URL de l'image du véhicule.
     * @return {@link String}
     */
    public String getUrl() {
        return url;
    }

    /**
     * Modifie l'URL de l'image du véhicule.
     * @param url : URL de l'image
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Retourne l'émission de CO2 du véhicule.
     * @return {@link BigDecimal}
     */
    public double getEmission() {
        return emission;
    }

    /**
     * Modifie l'émission de CO2 du véhicule.
     * @param emission : émission de CO2 (en g/km)
     */
    public void setEmission(double emission) {
        this.emission = emission;
    }

    /**
     * Retourne le statut du véhicule.
     * @return {@link StatusVehicle}
     */
    public StatusVehicle getStatus() {
        return status;
    }

    /**
     * Modifie le statut du véhicule.
     * @param status : Disponible, En réparation, ...
     */
    public void setStatus(StatusVehicle status) {
        this.status = status;
    }

    /**
     * Retourne la liste des collaborateurs du véhicule.
     * @return {@link List}<{@link Collaborator}>
     */
    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    /**
     * Modifie la liste des collaborateurs du véhicule.
     * @param collaborators : Liste des collaborateurs
     */
    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * Retourne la liste des covoiturages du véhicule.
     * @return {@link List}<{@link Carpooling}>
     */
    public List<Carpooling> getCarpoolings() {
        return carpoolings;
    }

    /**
     * Modifie la liste des covoiturages du véhicule.
     * @param carpoolings : Liste des covoiturages
     */
    public void setCarpoolings(List<Carpooling> carpoolings) {
        this.carpoolings = carpoolings;
    }

    /**
     * Retourne la motorisation du véhicule.
     * @return {@link Motorization}
     */
    public Motorization getMotorization() {
        return motorization;
    }

    /**
     * Modifie la motorisation du véhicule.
     * @param motorization : Essence, Diesel, Électrique, Hybride
     */
    public void setMotorization(Motorization motorization) {
        this.motorization = motorization;
    }

    /**
     * Retourne la marque du véhicule.
     * @return {@link Brand}
     */
    public Model getModel() {
        return model;
    }

    /**
     * Modifie le modèle du véhicule.
     * @param model : Renault, Peugeot, Citroën, ...
     */
    public void setModel(Model model) {
        this.model = model;
    }


    /**
     * Retourne la catégorie du véhicule.
     * @return {@link Category}
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Modifie la catégorie du véhicule.
     * @param category : Citadine, Berline, Break, ...
     */
    public void setCategory(Category category) {
        this.category = category;
    }
}