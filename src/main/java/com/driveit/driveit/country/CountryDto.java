package com.driveit.driveit.country;

public class CountryDto {

    private int id;
    private String name;

    public CountryDto() {
    }

    public CountryDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
