package com.driveit.driveit.category;

public class CategoryDto {

    private int id;
    private String name;

    public CategoryDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
