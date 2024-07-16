package com.driveit.driveit.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * Cette classe est une entité JPA qui représente un pays.
 * Un pays est caractérisé par les informations suivantes :
 * - Un identifiant unique (généré automatiquement)
 * - Un nom
 * - Une liste d'adresses
 *
 */
@Entity
@Table(name = "country")
public class Country {

    // Identifiant unique du pays
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Nom du pays
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    // Liste des adresses du pays
    @OneToMany(mappedBy = "country")
    private List<Address> addresses;

    // Constructors

    public Country() {}

    /**
     * Constructeur avec paramètres
     * @param id : l'identifiant du pays
     * @param name : le nom du pays
     * @param addresses : la liste des adresses du pays
     */
    public Country(int id, String name, List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.addresses = addresses;
    }

    // Getters and Setters

    /**
     * Retourne l'identifiant du pays.
     * @return L'identifiant du pays.
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du pays.
     * @param id Le nouvel identifiant du pays.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le nom du pays.
     * @return Le nom du pays.
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom du pays.
     * @param name Le nouveau nom du pays.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne la liste des adresses du pays.
     * @return La liste des adresses du pays.
     */
    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     * Modifie la liste des adresses du pays.
     * @param addresses La nouvelle liste des adresses du pays.
     */
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}