package com.driveit.driveit.vehicle;

import com.driveit.driveit.carpooling.CarpoolingDto;
import com.driveit.driveit.category.CategoryDto;
import com.driveit.driveit.collaborator.CollaboratorDto;
import com.driveit.driveit.model.ModelDto;
import com.driveit.driveit.motorization.MotorizationDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe est une entité JPA qui représente un véhicule.
 * Un véhicule est caractérisé par les informations suivantes :
 * - Un identifiant unique (généré automatiquement)
 * - Une immatriculation (ex : AB 123 CD)
 * - Un nombre de places assises
 * - Un booléen service (véhicule de service ou personnel
 * - Une URL (pour une image)
 * - Une émission de CO2 (en g/km)
 * - Un statut (disponible, en réparation, ...)
 * - Une liste de collaborateurs
 * - Une liste de covoiturages
 * - Une motorisation (essence, diesel, électrique, hybride)
 * - Un modèle (Renault, Peugeot, Citroën, ...)
 * - Une catégorie (citadine, berline, break, ...)
 */
public class VehicleDto {

    /**
     * Identifiant unique du véhicule
     */
    private int id;

    /**
     * Immatriculation unique du véhicule
     */
    private String registration;

    /**
     * Nombre de places assises du véhicule
     */
    private int numberOfSeats;

    /**
     * Service du véhicule
     */
    private boolean service;

    /**
     * URL de l'image du véhicule
     */
    private String url;

    /**
     * Emission de CO2 du véhicule
     */
    private Double emission;

    /**
     * Statut du véhicule
     */
    @Enumerated(EnumType.STRING)
    private StatusVehicle status;

    /**
     * Liste des collaborateurs du véhicule
     */
    private List<CollaboratorDto> collaborators = new ArrayList<>();

    /**
     * Liste des covoiturages du véhicule
     */
    private List<CarpoolingDto> carpoolings = new ArrayList<>();

    /**
     * Motorisation du véhicule
     */
    private MotorizationDto motorization;

    /**
     * Modèle du véhicule
     */
    private ModelDto model;

    /**
     * Catégorie du véhicule
     */
    private CategoryDto category;

    /**
     * Constructeur par défaut
     */
    public VehicleDto() {}

    /**
     * Constructeur avec paramètres
     *
     * @param registration : l'immatriculation du véhicule
     * @param numberOfSeats : le nombre de places assises du véhicule
     * @param service : booleen indiquant si le véhicule est un vehicule de service
     * @param url : l'URL de l'image du véhicule
     * @param emission : l'émission de CO2 du véhicule
     * @param status : le statut du véhicule
     * @param motorization : la motorisation du véhicule
     * @param model : le modèle du véhicule
     * @param category : la catégorie du véhicule
     */
    public VehicleDto(int id, String registration, int numberOfSeats, boolean service, String url, Double emission, StatusVehicle status, MotorizationDto motorization, ModelDto model, CategoryDto category) {
        this.id = id;
        this.registration = registration;
        this.numberOfSeats = numberOfSeats;
        this.service = service;
        this.url = url;
        this.emission = emission;
        this.status = status;
        this.motorization = motorization;
        this.model = model;
        this.category = category;
    }

    /**
     * Getter pour l'identifiant du véhicule
     *
     * @return l'identifiant du véhicule
     */
    public int getId() {
        return id;
    }

    /**
     * Setter pour l'identifiant du véhicule
     *
     * @param id : l'identifiant du véhicule
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter pour l'immatriculation du véhicule
     *
     * @return l'immatriculation du véhicule
     */
    public String getRegistration() {
        return registration;
    }

    /**
     * Setter pour l'immatriculation du véhicule
     *
     * @param registration : l'immatriculation du véhicule
     */
    public void setRegistration( String registration) {
        this.registration = registration;
    }

    /**
     * Getter pour le nombre de places assises du véhicule
     *
     * @return le nombre de places assises du véhicule
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Setter pour le nombre de places assises du véhicule
     *
     * @param numberOfSeats : le nombre de places assises du véhicule
     */
    public void setNumberOfSeats( int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Getter pour le service du véhicule
     *
     * @return le service du véhicule
     */
    public boolean getService() {
        return service;
    }

    /**
     * Setter pour le service du véhicule
     *
     * @param service : le service du véhicule
     */
    public void setService( boolean service) {
        this.service = service;
    }

    /**
     * Getter pour l'URL de l'image du véhicule
     *
     * @return l'URL de l'image du véhicule
     */
    public  String getUrl() {
        return url;
    }

    /**
     * Setter pour l'URL de l'image du véhicule
     *
     * @param url : l'URL de l'image du véhicule
     */
    public void setUrl( String url) {
        this.url = url;
    }

    /**
     * Getter pour l'émission de CO2 du véhicule
     *
     * @return l'émission de CO2 du véhicule
     */
    public  Double getEmission() {
        return emission;
    }

    /**
     * Setter pour l'émission de CO2 du véhicule
     *
     * @param emission : l'émission de CO2 du véhicule
     */
    public void setEmission( Double emission) {
        this.emission = emission;
    }

    /**
     * Getter pour le statut du véhicule
     *
     * @return le statut du véhicule
     */
    public StatusVehicle getStatus() {
        return status;
    }

    /**
     * Setter pour le statut du véhicule
     *
     * @param status : le statut du véhicule
     */
    public void setStatus(StatusVehicle status) {
        this.status = status;
    }

    public List<CollaboratorDto> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<CollaboratorDto> collaborators) {
        this.collaborators = collaborators;
    }

    public List<CarpoolingDto> getCarpoolings() {
        return carpoolings;
    }

    public void setCarpoolings(List<CarpoolingDto> carpoolings) {
        this.carpoolings = carpoolings;
    }

    public @NotNull(message = "La motorisation du véhicule doit être renseignée.") MotorizationDto getMotorization() {
        return motorization;
    }

    public void setMotorization(@NotNull(message = "La motorisation du véhicule doit être renseignée.") MotorizationDto motorization) {
        this.motorization = motorization;
    }

    public @NotNull(message = "Le modèle du véhicule doit être renseigné.") ModelDto getModel() {
        return model;
    }

    public void setModel(@NotNull(message = "Le modèle du véhicule doit être renseigné.") ModelDto model) {
        this.model = model;
    }

    public @NotNull(message = "La catégorie du véhicule doit être renseignée.") CategoryDto getCategory() {
        return category;
    }

    public void setCategory(@NotNull(message = "La catégorie du véhicule doit être renseignée.") CategoryDto category) {
        this.category = category;
    }
}
