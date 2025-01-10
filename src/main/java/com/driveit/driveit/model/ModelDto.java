package com.driveit.driveit.model;

import com.driveit.driveit.brand.BrandDto;

public class ModelDto {

    private int id;
    private String name;
    private BrandDto brand;

    public ModelDto(int id, String name,BrandDto brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BrandDto getBrand() {return brand;}

}
