package com.driveit.driveit.address;

import com.driveit.driveit.country.CountryDto;

public class AddressDto {

    private int id;
    private String streetNumber;
    private String streetName;
    private String city;
    private int postalCode;
    private CountryDto country;

    public AddressDto() {
    }

    public AddressDto(int id, String streetNumber, String streetName, String city, int postalCode, CountryDto country) {
        this.id = id;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public CountryDto getCountry() {
        return country;
    }

    public void setCountry(CountryDto country) {
        this.country = country;
    }

}

//@Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)
//@Column(name = "id")
//private int id;
//
//// Numéro de rue
//@Column(name = "street_number", length = 50, nullable = false)
//private String streetNumber;
//
//// Nom de rue
//@Column(name = "street_name", length = 50, nullable = false)
//private String streetName;
//
//// Ville
//@Column(name = "city", length = 50, nullable = false)
//private String city;
//
//// Code postal
//@Column(name = "postal_code", nullable = false)
//private int postalCode;
//
//// Pays
//@ManyToOne
//@JoinColumn(name = "country_id", nullable = false)
//private Country country;