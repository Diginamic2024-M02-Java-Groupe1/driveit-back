package com.driveit.driveit.address;

import com.driveit.driveit.cityZipCode.CityZipCode;
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


    @ManyToOne
    @JoinColumn(name="city_zip_code_id",nullable = false)
    private CityZipCode cityZipCode;

    // Pays
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    // Constructeur par défaut
    public Address() {
    }

    /**
     * Constructeur avec paramètres
     *
     * @param streetNumber : le numéro de rue
     * @param streetName   : le nom de rue
     * @param cityZipCode  : la ville et le code postal
     * @param country      : le pays
     */
    public Address(String streetNumber, String streetName, CityZipCode cityZipCode, Country country) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.cityZipCode = cityZipCode;
        this.country = country;
    }

    // Getters and Setters

    /**
     * Retourne l'identifiant de l'adresse.
     *
     * @return L'identifiant de l'adresse.
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le numéro de rue.
     *
     * @return Le numéro de rue.
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     * Modifie le numéro de rue.
     *
     * @param streetNumber Le nouveau numéro de rue.
     */
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * Retourne le nom de rue.
     *
     * @return Le nom de rue.
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Modifie le nom de rue.
     *
     * @param streetName Le nouveau nom de rue.
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public CityZipCode getCityZipCode() {
        return cityZipCode;
    }

    public void setCityZipCode(CityZipCode cityZipCode) {
        this.cityZipCode = cityZipCode;
    }

    public Country getCountry() {
        return country;
    }

    /**
     * Modifie le pays.
     *
     * @param country Le nouveau pays.
     */
    public void setCountry(Country country) {
        this.country = country;
    }
}