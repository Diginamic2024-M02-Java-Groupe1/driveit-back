package com.driveit.driveit.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * Cette entité JPA représente une catégorie de véhicules.
 * Une catégorie est caractérisée par les informations suivantes :
 * - Un identifiant unique (généré automatiquement)
 * - Un nom (ex: citadine, berline, SUV, ...)
 * - Une liste de véhicules
 */
@Entity
@Table(name = "category")
public class Category {

    // Identifiant unique de la catégorie
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Nom de la catégorie
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    // Liste des véhicules de la catégorie
    @OneToMany(mappedBy = "category")
    private List<Vehicle> vehicles;

    // Constructeur par défaut
    public Category() {}

    /**
     * Constructeur avec paramètres
     *
     * @param name : le nom de la catégorie
     * @param vehicles : la liste des véhicules de la catégorie
     */
    public Category(String name, List<Vehicle> vehicles) {
        this.name = name;
        this.vehicles = vehicles;
    }

    // Getters and Setters

    /**
     * Retourne l'identifiant de la catégorie.
     * @return L'identifiant de la catégorie.
     */
    public int getId() {
        return id;
    }


    /**
     * Retourne le nom de la catégorie.
     * @return Le nom de la catégorie.
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom de la catégorie.
     * @param name Le nouveau nom de la catégorie.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne la liste des véhicules de la catégorie.
     * @return La liste des véhicules de la catégorie.
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Modifie la liste des véhicules de la catégorie.
     * @param vehicles La nouvelle liste des véhicules de la catégorie.
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}