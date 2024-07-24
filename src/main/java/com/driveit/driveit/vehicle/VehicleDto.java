package com.driveit.driveit.vehicle;

import com.driveit.driveit.carpooling.CarpoolingDto;
import com.driveit.driveit.category.CategoryDto;
import com.driveit.driveit.collaborator.CollaboratorDto;
import com.driveit.driveit.model.ModelDto;
import com.driveit.driveit.motorization.MotorizationDto;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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
    @Pattern(regexp = "[A-Z]{2}-\\d{3}-[A-Z]{2}", message = "L'immatriculation doit être saisie au format XX-000-XX.")
    @NotNull(message = "L'immatriculation du véhicule doit être renseignée.")
    @Column(name = "registration", length = 50, nullable = false, unique = true)
    private String registration;

    /**
     * Nombre de places assises du véhicule
     */
    @NotNull(message = "Le nombre de places du véhicule doit être renseigné.")
    @Column(name = "number_of_seats", nullable = false)
    @Min(value = 1, message = "Le nombre de places assises doit être supérieur ou égal à 1.")
    private int numberOfSeats;

    /**
     * Service du véhicule
     */
    @NotNull(message = "Le service du véhicule doit être renseigné.")
    @Column(name = "service")
    private boolean service;

    /**
     * URL de l'image du véhicule
     */
    @NotNull(message = "L'URL de l'image du véhicule ne peut pas être nulle.")
    @Column(nullable = false)
    private String url;

    /**
     * Emission de CO2 du véhicule
     */
    @NotNull(message = "L'émission de CO2 du véhicule doit être renseignée.")
    @Min(value = 0, message = "L'émission de CO2 doit être supérieure ou égale à 0.")
    @Column(name = "emission", nullable = false)
    private Double emission;

    /**
     * Statut du véhicule
     */
    @NotNull(message = "Le statut du véhicule doit être renseigné.")
    @Column(nullable = false)
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
    @NotNull(message = "La motorisation du véhicule doit être renseignée.")
    private MotorizationDto motorization;

    /**
     * Modèle du véhicule
     */
    @NotNull(message = "Le modèle du véhicule doit être renseigné.")
    private ModelDto model;

    /**
     * Catégorie du véhicule
     */
    @NotNull(message = "La catégorie du véhicule doit être renseignée.")
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
    public @Pattern(regexp = "[A-Z]{2}-\\d{3}-[A-Z]{2}", message = "L'immatriculation doit être saisie au format XX-000-XX.") @NotNull(message = "L'immatriculation du véhicule doit être renseignée.") String getRegistration() {
        return registration;
    }

    /**
     * Setter pour l'immatriculation du véhicule
     *
     * @param registration : l'immatriculation du véhicule
     */
    public void setRegistration(@Pattern(regexp = "[A-Z]{2}-\\d{3}-[A-Z]{2}", message = "L'immatriculation doit être saisie au format XX-000-XX.") @NotNull(message = "L'immatriculation du véhicule doit être renseignée.") String registration) {
        this.registration = registration;
    }

    /**
     * Getter pour le nombre de places assises du véhicule
     *
     * @return le nombre de places assises du véhicule
     */
    @NotNull(message = "Le nombre de places du véhicule doit être renseigné.")
    @Min(value = 1, message = "Le nombre de places assises doit être supérieur ou égal à 1.")
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Setter pour le nombre de places assises du véhicule
     *
     * @param numberOfSeats : le nombre de places assises du véhicule
     */
    public void setNumberOfSeats(@NotNull(message = "Le nombre de places du véhicule doit être renseigné.") @Min(value = 1, message = "Le nombre de places assises doit être supérieur ou égal à 1.") int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Getter pour le service du véhicule
     *
     * @return le service du véhicule
     */
    @NotNull(message = "Le service du véhicule doit être renseigné.")
    public boolean getService() {
        return service;
    }

    /**
     * Setter pour le service du véhicule
     *
     * @param service : le service du véhicule
     */
    public void setService(@NotNull(message = "Le service du véhicule doit être renseigné.") boolean service) {
        this.service = service;
    }

    /**
     * Getter pour l'URL de l'image du véhicule
     *
     * @return l'URL de l'image du véhicule
     */
    public @NotNull(message = "VehiculeDto : L'URL de l'image du véhicule ne peut pas être nulle.") String getUrl() {
        return url;
    }

    /**
     * Setter pour l'URL de l'image du véhicule
     *
     * @param url : l'URL de l'image du véhicule
     */
    public void setUrl(@NotNull(message = "L'URL de l'image du véhicule ne peut pas être nulle.") String url) {
        this.url = url;
    }

    /**
     * Getter pour l'émission de CO2 du véhicule
     *
     * @return l'émission de CO2 du véhicule
     */
    public @NotNull(message = "L'émission de CO2 du véhicule doit être renseignée.") @Min(value = 0, message = "L'émission de CO2 doit être supérieure ou égale à 0.") Double getEmission() {
        return emission;
    }

    /**
     * Setter pour l'émission de CO2 du véhicule
     *
     * @param emission : l'émission de CO2 du véhicule
     */
    public void setEmission(@NotNull(message = "L'émission de CO2 du véhicule doit être renseignée.") @Min(value = 0, message = "L'émission de CO2 doit être supérieure ou égale à 0.") Double emission) {
        this.emission = emission;
    }

    /**
     * Getter pour le statut du véhicule
     *
     * @return le statut du véhicule
     */
    public @NotNull(message = "Le statut du véhicule doit être renseigné.") StatusVehicle getStatus() {
        return status;
    }

    /**
     * Setter pour le statut du véhicule
     *
     * @param status : le statut du véhicule
     */
    public void setStatus(@NotNull(message = "Le statut du véhicule doit être renseigné.") StatusVehicle status) {
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
