package com.driveit.driveit.vehicle;

import com.driveit.driveit.category.CategoryDto;
import com.driveit.driveit.model.ModelDto;
import com.driveit.driveit.motorization.MotorizationDto;

public class VehicleDto {

    private String registration;
    private int numberOfSeats;
    private boolean service;
    private String url;
    private double emission;
    private StatusVehicle status;
    private MotorizationDto motorizationDto;
    private ModelDto modelDto;
    private CategoryDto categoryDto;

    public VehicleDto(String registration, int numberOfSeats, boolean service, String url, double emission, StatusVehicle status, MotorizationDto motorizationDto, ModelDto modelDto, CategoryDto categoryDto) {
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public boolean isService() {
        return service;
    }

    public void setService(boolean service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getEmission() {
        return emission;
    }

    public void setEmission(double emission) {
        this.emission = emission;
    }

    public StatusVehicle getStatus() {
        return status;
    }

    public void setStatus(StatusVehicle status) {
        this.status = status;
    }

    public MotorizationDto getMotorizationDto() {
        return motorizationDto;
    }

    public void setMotorizationDto(MotorizationDto motorizationDto) {
        this.motorizationDto = motorizationDto;
    }

    public ModelDto getModelDto() {
        return modelDto;
    }

    public void setModelDto(ModelDto modelDto) {
        this.modelDto = modelDto;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    @Override
    public String toString() {
        return "VehicleDto{" +
                "registration='" + registration + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", service=" + service +
                ", url='" + url + '\'' +
                ", emission=" + emission +
                ", status=" + status +
                ", motorizationDto=" + motorizationDto +
                ", modelDto=" + modelDto +
                ", categoryDto=" + categoryDto +
                '}';
    }
}
