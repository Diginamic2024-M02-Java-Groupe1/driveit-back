package com.driveit.driveit.model;

import com.driveit.driveit.brand.BrandDto;

public class ModelDto {

    private int id;
    private String name;
    private BrandDto brand;

    public ModelDto() {
    }

    public ModelDto(int id, String name) {
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

    public BrandDto getBrand() {return brand;}

    public void setBrand(BrandDto brand) {this.brand = brand;}
}
