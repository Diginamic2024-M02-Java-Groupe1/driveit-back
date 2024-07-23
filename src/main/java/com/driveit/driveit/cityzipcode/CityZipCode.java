package com.driveit.driveit.cityzipcode;

import com.driveit.driveit.address.Address;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="city_zip_code")
public class CityZipCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "city", nullable = false)
    private String city;

    @OneToMany(mappedBy = "cityZipCode")
    private List<Address> addresses = new ArrayList<>();

    public CityZipCode(){}

    public CityZipCode(String city, String zipCode) {
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "CityZipCode{" +
                "zipCode=" + zipCode +
                ", city=" + city +
                '}';
    }
}
