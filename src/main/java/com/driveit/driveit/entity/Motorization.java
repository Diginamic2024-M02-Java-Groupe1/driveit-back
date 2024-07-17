package com.driveit.driveit.entity;

import jakarta.persistence.*;

import java.util.List;


/**
 * Cette classe est une entité JPA qui représente un type de motorisation.
 * Une motorisation est caractérisée par les informations suivantes :
 * - Un identifiant unique (généré automatiquement)
 * - Un nom (ex: essence, diesel, électrique, hybride)
 * - Une liste de véhicules ayant cette motorisation
 *
 */
@Entity
@Table(name = "motorization")
public class Motorization {

    // Identifiant unique de la motorisation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Nom de la motorisation
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    // Liste des véhicules ayant cette motorisation
    @OneToMany(mappedBy = "motorization")
    private List<Vehicle> vehicles;


    // Constructeur par défaut
    public Motorization() {}

    /**
     * Constructeur avec paramètres
     * @param name : le nom de la motorisation
     * @param vehicles : la liste des véhicules ayant cette motorisation
     */
    public Motorization(String name, List<Vehicle> vehicles) {
        this.name = name;
        this.vehicles = vehicles;
    }


    // Getters and Setters

    /**
     * Retourne l'identifiant de la motorisation.
     * @return L'identifiant de la motorisation.
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'identifiant de la motorisation.
     * @param id Le nouvel identifiant de la motorisation.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le nom de la motorisation.
     * @return Le nom de la motorisation.
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom de la motorisation.
     * @param name Le nouveau nom de la motorisation.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne la liste des véhicules ayant cette motorisation.
     * @return La liste des véhicules ayant cette motorisation.
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Modifie la liste des véhicules ayant cette motorisation.
     * @param vehicles La nouvelle liste des véhicules ayant cette motorisation.
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}