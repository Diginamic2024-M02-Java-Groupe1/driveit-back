package com.driveit.driveit.cityzipcode;

public class CityZipCodeDto {

    private String city;

    private String code;

    public CityZipCodeDto() {
    }

    public CityZipCodeDto(String city, String code) {
        this.city = city;
        this.code = code;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
