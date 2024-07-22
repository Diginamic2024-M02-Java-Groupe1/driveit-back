package com.driveit.driveit.cityzipcode.dto;

public class CityZipCodeDto {

    private CityDto city;

    private ZipCodeDto code;

    public CityZipCodeDto() {
    }

    public CityZipCodeDto(CityDto city, ZipCodeDto code) {
        this.city = city;
        this.code = code;
    }


    public CityDto getCity() {
        return city;
    }

    public void setCity(CityDto city) {
        this.city = city;
    }

    public ZipCodeDto getCode() {
        return code;
    }

    public void setCode(ZipCodeDto code) {
        this.code = code;
    }
}
