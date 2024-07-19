package com.driveit.driveit.vehicle;

import com.driveit.driveit.brand.Brand;
import com.driveit.driveit.carpooling.Carpooling;
import com.driveit.driveit.category.Category;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.motorization.Motorization;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe est une entité JPA qui représente un véhicule.
 * Un véhicule est caractérisé par les informations suivantes :
 * - Un identifiant unique (généré automatiquement)
 * - Une immatriculation (ex: 1234 AB 01)
 * - Un nombre de places assises
 * - Un service (location, transport, ...)
 * - Une URL (pour une image)
 * - Une émission de CO2 (en g/km)
 * - Un statut (disponible, en réparation, ...)
 * - Une liste de collaborateurs
 * - Une motorisation (essence, diesel, électrique, hybride)
 * - Une marque (Renault, Peugeot, Citroën, ...)
 * - Une catégorie (citadine, berline, break, ...)

 */
@Entity
@Table(name = "vehicle")
public class Vehicle {

    // Identifiant unique du véhicule
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Immatriculation du véhicule
    @Column(name = "registration", length = 50, nullable = false, unique = true)
    private String registration;


    // Nombre de places assises du véhicule
    @Column(name = "number_of_seats", nullable = false)
    @Min(value = 1, message = "Le nombre de places assises doit être supérieur ou égal à 1.")
    private int numberOfSeats;

    // Service du véhicule
    @Column(name = "service", length = 50)
    private String service;

    // URL de l'image du véhicule
    @Column(name = "url", length = 255)
    private String url;

    // Émission de CO2 du véhicule
    @Column(name = "emission", precision = 15, scale = 2)
    private BigDecimal emission;

    // Statut du véhicule
    @Column(name = "status", length = 50)
    private String status;

    // Liste des collaborateurs du véhicule
    @ManyToMany(mappedBy = "vehicles")
    private List<Collaborator> collaborators = new ArrayList<>();

    // Motorisation du véhicule
    @ManyToOne
    @JoinColumn(name = "motorization_id")
    private Motorization motorization;

    // Marque du véhicule
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    // Catégorie du véhicule
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Liste des covoiturages du véhicule
    @OneToMany(mappedBy = "vehicle")
    private List<Carpooling> carpoolings = new ArrayList<>();

    // Constructeur par défaut

    public Vehicle() {}


    // Constructeur avec paramètres

    /**
     * Constructeur avec paramètres
     *
     * @param registration : l'immatriculation du véhicule
     * @param numberOfSeats : le nombre de places assises du véhicule
     * @param service : le service du véhicule
     * @param url : l'URL de l'image du véhicule
     * @param emission : l'émission de CO2 du véhicule
     * @param status : le statut du véhicule
     * @param collaborators : la liste des collaborateurs du véhicule
     * @param motorization : la motorisation du véhicule
     * @param brand : la marque du véhicule
     * @param category : la catégorie du véhicule
     */
    public Vehicle(String registration, int numberOfSeats, String service, String url, BigDecimal emission, String status, List<Collaborator> collaborators, Motorization motorization, Brand brand, Category category) {
        this.registration = registration;
        this.numberOfSeats = numberOfSeats;
        this.service = service;
        this.url = url;
        this.emission = emission;
        this.status = status;
        this.collaborators = collaborators;
        this.motorization = motorization;
        this.brand = brand;
        this.category = category;
    }

    /**
     * Retourne l'identifiant du véhicule.
     *
     * @return int
     */
    public int getId() {
        return id;
    }
    

    /**
     * Retourne l'immatriculation du véhicule.
     *
     * @return String
     */
    public String getRegistration() {
        return registration;
    }

    /**
     * Modifie l'immatriculation du véhicule.
     *
     * @param registration : immatriculation
     */
    public void setRegistration(String registration) {
        this.registration = registration;
    }

    /**
     * Retourne le nombre de places assises du véhicule.
     *
     * @return int
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Modifie le nombre de places assises du véhicule.
     *
     * @param numberOfSeats : nombre de places assises
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Retourne le service du véhicule.
     *
     * @return String
     */
    public String getService() {
        return service;
    }

    /**
     * Modifie le service du véhicule.
     *
     * @param service : Location, Transport, ...
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * Retourne l'URL de l'image du véhicule.
     *
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * Modifie l'URL de l'image du véhicule.
     *
     * @param url : URL de l'image
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Retourne l'émission de CO2 du véhicule.
     *
     * @return BigDecimal
     */
    public BigDecimal getEmission() {
        return emission;
    }

    /**
     * Modifie l'émission de CO2 du véhicule.
     *
     * @param emission : en g/km
     */
    public void setEmission(BigDecimal emission) {
        this.emission = emission;
    }

    /**
     * Retourne le statut du véhicule.
     *
     * @return String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Modifie le statut du véhicule.
     *
     * @param status : Disponible, En réparation, ...
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retourne la liste des collaborateurs du véhicule.
     *
     * @return List<Collaborator>
     */
    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    /**
     * Modifie la liste des collaborateurs du véhicule.
     *
     * @param collaborators : Liste des collaborateurs
     */
    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * Retourne la motorisation du véhicule.
     *
     * @return Motorization
     */
    public Motorization getMotorization() {
        return motorization;
    }

    /**
     * Modifie la motorisation du véhicule.
     *
     * @param motorization : Essence, Diesel, Électrique, Hybride
     */
    public void setMotorization(Motorization motorization) {
        this.motorization = motorization;
    }

    /**
     * Retourne la marque du véhicule.
     *
     * @return Brand
     */
    public Brand getBrand() {
        return brand;
    }

    /**
     * Modifie la marque du véhicule.
     *
     * @param brand : Renault, Peugeot, Citroën, ...
     */
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Carpooling> getCarpoolings() {
        return carpoolings;
    }

    public void setCarpoolings(List<Carpooling> carpoolings) {
        this.carpoolings = carpoolings;
    }

    /**
     * Retourne la catégorie du véhicule.
     *
     * @return Category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Modifie la catégorie du véhicule.
     *
     * @param category : Citadine, Berline, Break, ...
     */
    public void setCategory(Category category) {
        this.category = category;
    }
}