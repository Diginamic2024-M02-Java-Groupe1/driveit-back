package com.driveit.driveit.cityzipcode;

import com.driveit.driveit.address.Address;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="city_zipCode")
public class CityZipCode {

    @Id
    @OneToOne
    private ZipCode zipCode;

    @OneToOne
    private City city;

    @OneToMany(mappedBy = "cityZipCode")
    private List<Address> addresses = new ArrayList<Address>();

    public CityZipCode(){}

    public CityZipCode(City city, ZipCode zipCode) {
        this.city = city;
        this.zipCode = zipCode;
    }

    public ZipCode getZipCode() {
        return zipCode;
    }

    public void setZipCode(ZipCode zipCode) {
        this.zipCode = zipCode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
