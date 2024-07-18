package com.driveit.driveit.address;

import com.driveit.driveit.country.Country;
import jakarta.persistence.*;

/**
 * Cette classe est une entité JPA qui représente une adresse.
 * Une adresse est caractérisée par les informations suivantes :
 * - Un identifiant unique (généré automatiquement)
 * - Un numéro de rue
 * - Un nom de rue
 * - Une ville
 * - Un code postal
 * - Un pays
 */
@Entity
@Table(name = "address")
public class Address {

    // Identifiant unique de l'adresse
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Numéro de rue
    @Column(name = "street_number", length = 50, nullable = false)
    private String streetNumber;

    // Nom de rue
    @Column(name = "street_name", length = 50, nullable = false)
    private String streetName;

    // Ville
    @Column(name = "city", length = 50, nullable = false)
    private String city;

    // Code postal
    @Column(name = "postal_code", nullable = false)
    private int postalCode;

    // Pays
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    // Constructeur par défaut
    public Address() {}

    /**
     * Constructeur avec paramètres
     * @param streetNumber : le numéro de rue
     * @param streetName : le nom de rue
     * @param city : la ville
     * @param postalCode : le code postal
     * @param country : le pays
     */
    public Address(String streetNumber, String streetName, String city, int postalCode, Country country) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    // Getters and Setters

    /**
     * Retourne l'identifiant de l'adresse.
     * @return L'identifiant de l'adresse.
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le numéro de rue.
     * @return Le numéro de rue.
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     * Modifie le numéro de rue.
     * @param streetNumber Le nouveau numéro de rue.
     */
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * Retourne le nom de rue.
     * @return Le nom de rue.
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Modifie le nom de rue.
     * @param streetName Le nouveau nom de rue.
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Retourne la ville.
     * @return La ville.
     */
    public String getCity() {
        return city;
    }

    /**
     * Modifie la ville.
     * @param city La nouvelle ville.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Retourne le code postal.
     * @return Le code postal.
     */
    public int getPostalCode() {
        return postalCode;
    }

    /**
     * Modifie le code postal.
     * @param postalCode Le nouveau code postal.
     */
    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Retourne le pays.
     * @return Le pays.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Modifie le pays.
     * @param country Le nouveau pays.
     */
    public void setCountry(Country country) {
        this.country = country;
    }
}