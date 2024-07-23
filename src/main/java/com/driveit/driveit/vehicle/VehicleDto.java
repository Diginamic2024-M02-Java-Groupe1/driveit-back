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
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private boolean isService;

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
     * @param isService : booleen indiquant si le véhicule est un vehicule de service
     * @param url : l'URL de l'image du véhicule
     * @param emission : l'émission de CO2 du véhicule
     * @param status : le statut du véhicule
     * @param motorization : la motorisation du véhicule
     * @param model : le modèle du véhicule
     * @param category : la catégorie du véhicule
     */
    public VehicleDto(int id,String registration, int numberOfSeats, boolean isService, String url, Double emission, StatusVehicle status, MotorizationDto motorization, ModelDto model, CategoryDto category) {
        this.id = id;
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
     * Retourne le nombre de places assises du véhicule.
     * @return {@link Integer}
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
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
     * Retourne l'URL de l'image du véhicule.
     * @return {@link String}
     */
    public String getUrl() {
        return url;
    }

    /**
     * Retourne l'émission de CO2 du véhicule.
     * @return {@link Double}
     */
    public Double getEmission() {
        return emission;
    }

    /**
     * Retourne le statut du véhicule.
     * @return {@link StatusVehicle}
     */
    public StatusVehicle getStatus() {
        return status;
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
     * Retourne la marque du véhicule.
     * @return {@link Brand}
     */
    public ModelDto getModel() {
        return model;
    }

    /**
     * Retourne la catégorie du véhicule.
     * @return {@link Category}
     */
    public CategoryDto getCategory() {
        return category;
    }
}
