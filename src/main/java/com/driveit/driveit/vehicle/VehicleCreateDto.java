package com.driveit.driveit.vehicle;

public record VehicleCreateDto(String registration, int numberOfSeats, boolean service, String url, Double emission,
                               int motorizationId, String model, int categoryId, int brandId) {

    @Override
    public String toString() {
        return "VehicleCreateDto{" +
                "registration='" + registration + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", service=" + service +
                ", url='" + url + '\'' +
                ", emission=" + emission +
                ", motorizationId=" + motorizationId +
                ", model='" + model + '\'' +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                '}';
    }

}
