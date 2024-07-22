package com.driveit.driveit.vehicle;

import com.driveit.driveit.brand.Brand;
import com.driveit.driveit.carpooling.Carpooling;
import com.driveit.driveit.carpooling.CarpoolingDto;
import com.driveit.driveit.category.Category;
import com.driveit.driveit.category.CategoryDto;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorDto;
import com.driveit.driveit.model.ModelDto;
import com.driveit.driveit.motorization.Motorization;
import com.driveit.driveit.motorization.MotorizationDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

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
public class VehicleDto {

    private int id;

    private String registration;

    private int numberOfSeats;

    private boolean isService;

    private String url;

    // Émission de CO2 du véhicule

    private double emission;

    /**
     * Statut du véhicule
     */
    @Enumerated(EnumType.STRING)
    private StatusVehicle status;

    private List<CollaboratorDto> collaborators = new ArrayList<>();

    private List<CarpoolingDto> carpoolings = new ArrayList<>();


    private MotorizationDto motorization;

    private ModelDto model;

    private CategoryDto category;



    // Constructeur par défaut

    public VehicleDto() {}

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
    public VehicleDto(String registration, int numberOfSeats, boolean isService, String url, double emission, StatusVehicle status, MotorizationDto motorization, ModelDto model, CategoryDto category) {
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
     * Modifie l'identifiant du véhicule.
     * @param id : identifiant
     */
    public void setId(int id) {
        this.id = id;
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
    public List<CollaboratorDto> getCollaborators() {
        return collaborators;
    }

    /**
     * Modifie la liste des collaborateurs du véhicule.
     * @param collaborators : Liste des collaborateurs
     */
    public void setCollaborators(List<CollaboratorDto> collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * Retourne la liste des covoiturages du véhicule.
     * @return {@link List}<{@link Carpooling}>
     */
    public List<CarpoolingDto> getCarpoolings() {
        return carpoolings;
    }

    /**
     * Modifie la liste des covoiturages du véhicule.
     * @param carpoolings : Liste des covoiturages
     */
    public void setCarpoolings(List<CarpoolingDto> carpoolings) {
        this.carpoolings = carpoolings;
    }

    /**
     * Retourne la motorisation du véhicule.
     * @return {@link Motorization}
     */
    public MotorizationDto getMotorization() {
        return motorization;
    }

    /**
     * Modifie la motorisation du véhicule.
     * @param motorization : Essence, Diesel, Électrique, Hybride
     */
    public void setMotorization(MotorizationDto motorization) {
        this.motorization = motorization;
    }

    /**
     * Retourne la marque du véhicule.
     * @return {@link Brand}
     */
    public ModelDto getModel() {
        return model;
    }

    /**
     * Modifie le modèle du véhicule.
     * @param model : Renault, Peugeot, Citroën, ...
     */
    public void setModel(ModelDto model) {
        this.model = model;
    }


    /**
     * Retourne la catégorie du véhicule.
     * @return {@link Category}
     */
    public CategoryDto getCategory() {
        return category;
    }

    /**
     * Modifie la catégorie du véhicule.
     * @param category : Citadine, Berline, Break, ...
     */
    public void setCategory(CategoryDto category) {
        this.category = category;
    }

}
