package com.driveit.driveit.entity;

import jakarta.persistence.*;

/**
 * Cette classe est une entité JPA qui représente un modèle de véhicule.
 * Un modèle est caractérisé par les informations suivantes :
 * - Un identifiant unique (généré automatiquement)
 * - Un nom (ex: Clio, 208, C3, ...)
 * - Une marque (Renault, Peugeot, Citroën, ...)
 */
@Entity
@Table(name = "model")
public class Model {

    // Identifiant unique du modèle
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    // Nom du modèle
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    // Marque du modèle
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    // Constructeur par défaut
    public Model() {}

    /**
     * Constructeur avec paramètres
     *
     * @param id : l'identifiant du modèle
     * @param name : le nom du modèle
     * @param brand : la marque du modèle
     */
    public Model(int id, String name, Brand brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
    }


    // Getters and Setters

    /**
     * Retourne l'identifiant du modèle.
     *
     * @return L'identifiant du modèle.
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du modèle.
     * @param id Le nouvel identifiant du modèle.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le nom du modèle.
     * @return Le nom du modèle.
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom du modèle.
     * @param name Le nouveau nom du modèle.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne la marque du modèle.
     * @return La marque du modèle.
     */
    public Brand getBrand() {
        return brand;
    }

    /**
     * Modifie la marque du modèle.
     * @param brand La nouvelle marque du modèle.
     */
    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}