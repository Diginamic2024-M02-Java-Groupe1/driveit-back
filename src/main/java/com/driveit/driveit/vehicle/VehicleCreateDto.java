package com.driveit.driveit.vehicle;

import com.driveit.driveit.category.CategoryDto;
import com.driveit.driveit.model.ModelDto;
import com.driveit.driveit.motorization.MotorizationDto;

public record VehicleCreateDto(String registration, int numberOfSeats, boolean service, String url, Double emission,
                               MotorizationDto motorization, ModelDto model, CategoryDto category) {

    @Override
    public String toString() {
        return
                "VehicleCreateDto{" +
                "registration='" + registration + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", service=" + service +
                ", url='" + url + '\'' +
                ", emission=" + emission +
                ", motorization=" + motorization.getName() +
                ", model='" + model + '\'' +
                ", brand='" + model.getBrand().getName() + '\'' +
                ", category=" + category.getName() +
                '}';
    }

}
