package com.driveit.driveit.address;

import com.driveit.driveit.cityzipcode.CityZipCodeDto;

public class AddressDto {

    private int id;
    private String streetNumber;
    private String streetName;
    private CityZipCodeDto cityZipCode;

    public AddressDto() {
    }

    public AddressDto(int id, String streetNumber, String streetName, CityZipCodeDto cityZipCode) {
        this.id = id;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.cityZipCode = cityZipCode;
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

    public CityZipCodeDto getCityZipCode() {
        return cityZipCode;
    }

    public void setCityZipCode(CityZipCodeDto cityZipCode) {
        this.cityZipCode = cityZipCode;
    }




}