package com.driveit.driveit.cityZipCode;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="city")
public class City {

    @Id
    private String name;

    public City(){    }

    public City(String name){this.name = name;}

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}
}
