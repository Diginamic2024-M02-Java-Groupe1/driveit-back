package com.driveit.driveit.vehicle;

public record VehicleCreateDto(String registration, int numberOfSeats, boolean service, String url, Double emission,
                               String motorization, String model, String category, String brand) {

    @Override
    public String toString() {
        return "VehicleCreateDto{" +
                "registration='" + registration + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", service=" + service +
                ", url='" + url + '\'' +
                ", emission=" + emission +
                ", motorization=" + motorization +
                ", model='" + model + '\'' +
                ", category=" + category +
                ", brand=" + brand +
                '}';
    }

}
