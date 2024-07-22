package com.driveit.driveit.brand;

import com.driveit.driveit.model.ModelDto;

public class BrandDto {

    private int id;
    private String name;

    public BrandDto() {
    }

    public BrandDto(int id, String name) {
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
