package com.driveit.driveit.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * Cette entité JPA représente une marque de véhicules.
 * Une marque est caractérisée par les informations suivantes :
 * - Un identifiant unique (généré automatiquement)
 * - Un nom (ex: Peugeot, Renault, ...)
 * - Une liste de véhicules
 * - Une liste de modèles
 */
@Entity
@Table(name = "brand")
public class Brand {

    // Identifiant unique de la marque
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Nom de la marque
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    // Liste des véhicules de la marque
    @OneToMany(mappedBy = "brand")
    private List<Vehicle> vehicles;

    // Liste des modèles de la marque
    @OneToMany(mappedBy = "brand")
    private List<Model> models;



    // Constructeur par défaut
    public Brand() {}

    /**
     * Constructeur avec paramètres
     *
     * @param name : le nom de la marque
     */
    public Brand(String name) {
        this.name = name;
    }

    // Getters and Setters

    /**
     * Retourne l'identifiant de la marque.
     * @return L'identifiant de la marque.
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'identifiant de la marque.
     * @param id Le nouvel identifiant de la marque.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le nom de la marque.
     * @return Le nom de la marque.
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom de la marque.
     * @param name Le nouveau nom de la marque.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne la liste des véhicules de la marque.
     * @return La liste des véhicules de la marque.
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Modifie la liste des véhicules de la marque.
     * @param vehicles La nouvelle liste des véhicules de la marque.
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Retourne la liste des modèles de la marque.
     * @return La liste des modèles de la marque.
     */
    public List<Model> getModels() {
        return models;
    }

    /**
     * Modifie la liste des modèles de la marque.
     * @param models La nouvelle liste des modèles de la marque.
     */
    public void setModels(List<Model> models) {
        this.models = models;
    }
}