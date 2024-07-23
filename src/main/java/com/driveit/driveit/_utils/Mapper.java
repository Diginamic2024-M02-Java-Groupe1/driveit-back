package com.driveit.driveit._utils;

import com.driveit.driveit.address.Address;
import com.driveit.driveit.address.AddressDto;
import com.driveit.driveit.brand.Brand;
import com.driveit.driveit.brand.BrandDto;
import com.driveit.driveit.carpooling.Carpooling;
import com.driveit.driveit.carpooling.CarpoolingDto;
import com.driveit.driveit.category.Category;
import com.driveit.driveit.category.CategoryDto;
import com.driveit.driveit.cityzipcode.CityZipCode;
import com.driveit.driveit.cityzipcode.CityZipCodeDto;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorDto;
import com.driveit.driveit.model.Model;
import com.driveit.driveit.model.ModelDto;
import com.driveit.driveit.motorization.Motorization;
import com.driveit.driveit.motorization.MotorizationDto;
import com.driveit.driveit.reservationcarpooling.ReservationCarpooling;
import com.driveit.driveit.reservationcarpooling.ReservationCarpoolingDto;
import com.driveit.driveit.reservationvehicle.ReservationVehicle;
import com.driveit.driveit.reservationvehicle.VehiculeServiceReservationDto;
import com.driveit.driveit.vehicle.Vehicle;
import com.driveit.driveit.vehicle.VehicleDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * Classe utilitaire permettant de convertir des objets en objets DTO
 */
public class Mapper {
    /**
     * Convertit un objet {@link Carpooling} en un objet {@link CarpoolingDto}
     * @param carpooling : le covoiturage à convertir
     * @return le covoiturage converti
     */
    public static CarpoolingDto carpoolingToDto(Carpooling carpooling) {
        List<Collaborator> participants = carpooling.getReservations().stream().map(ReservationCarpooling::getCollaborator).toList();
        return new CarpoolingDto(
                carpooling.getId(),
                carpooling.getDepartureDate(),
                carpooling.getArrivalDate(),
                collaboratorToDto(carpooling.getOrganizer()),
                addressToDto(carpooling.getDepartureAddress()),
                addressToDto(carpooling.getArrivalAddress()),
                participants.stream().map(Mapper::collaboratorToDto).toList(),
                vehicleToDto(carpooling.getVehicle())
        );
    }

    /**
     * Convertit un objet {@link Address} en un objet {@link AddressDto}
     *
     * @param address : l'adresse à convertir
     *                @return l'adresse convertie
     */
    public static AddressDto addressToDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setStreetNumber(address.getStreetNumber());
        addressDto.setStreetName(address.getStreetName());
        addressDto.setCityZipCode(cityZipCodeToDto(address.getCityZipCode()));
        return addressDto;
    }

    /**
     * Convertit un objet {@link CityZipCode} en un objet {@link CityZipCodeDto}
     *
     * @param cityZipCode : la ville à convertir
     * @return la ville convertie
     */
    public static CityZipCodeDto cityZipCodeToDto(CityZipCode cityZipCode) {
        CityZipCodeDto cityZipCodeDto = new CityZipCodeDto();
        cityZipCodeDto.setCity(cityZipCode.getCity());
        cityZipCodeDto.setCode(cityZipCode.getZipCode());
        return cityZipCodeDto;
    }

    /**
     * Convertit un objet {@link Collaborator} en un objet {@link CollaboratorDto}
     *
     * @param collaborator : le collaborateur à convertir
     * @return le collaborateur converti
     */
    public static CollaboratorDto collaboratorToDto(Collaborator collaborator) {
        return new CollaboratorDto(
                collaborator.getId(),
                collaborator.getEmail(),
                collaborator.getFirstName(),
                collaborator.getLastName(),
                collaborator.getAuthorities()
        );
    }




    public static ReservationCarpoolingDto reservationCollaboratorToDto(ReservationCarpooling reservationCarpooling) {
        return new ReservationCarpoolingDto(
                reservationCarpooling.getId(),
                reservationCarpooling.getStatus(),
                collaboratorToDto(reservationCarpooling.getCollaborator()),
                carpoolingToDto(reservationCarpooling.getCarpooling())
        );
    }



    /**
     * Convertit un objet {@link Vehicle} en un objet {@link VehicleDto}
     *
     * @param vehicle : le véhicule à convertir
     * @return le véhicule converti
     */
    public static VehicleDto vehicleToDto(Vehicle vehicle) {
        return new VehicleDto(
                vehicle.getId(),
                vehicle.getRegistration(),
                vehicle.getNumberOfSeats(),
                vehicle.getService(),
                vehicle.getUrl(),
                vehicle.getEmission(),
                vehicle.getStatus(),
                motorizationToDto(vehicle.getMotorization()),
                modelToDto(vehicle.getModel()),
                categoryToDto(vehicle.getCategory()));

    }

    /**
     * Convertit un objet {@link Motorization} en un objet {@link MotorizationDto}
     * @param motorization : la motorisation à convertir
     * @return la motorisation converti
     */
    public static MotorizationDto motorizationToDto(Motorization motorization){
        return new MotorizationDto(motorization.getId(),motorization.getName());
    }

    /**
     * Convertit un objet {@link Model} en un objet {@link ModelDto}
     * @param model : le modèle à convertir
     * @return le modèle converti
     */
    public static ModelDto modelToDto(Model model){
        return new ModelDto(model.getId(),model.getName(),brandToDto(model.getBrand()));
    }

    /**
     * Convertit un objet {@link Brand} en un objet {@link BrandDto}
     * @param brand : la marque à convertir
     * @return la marque converti
     */
    public static BrandDto brandToDto(Brand brand){
        return new BrandDto(brand.getId(), brand.getName());
    }

    /**
     * Convertit un objet {@link Category} en un objet {@link CategoryDto}
     * @param category : la catégorie à convertir
     * @return la catégorie converti
     */
    public static CategoryDto categoryToDto(Category category){
       return new CategoryDto(category.getId(),category.getName());
    }

    /**
     * Convertit un objet {@link VehicleDto} en un objet {@link Vehicle}
     * @param vehicleDto
     * @return
     */
    public static Vehicle vehicleDtoToEntity(VehicleDto vehicleDto) {
        return new Vehicle(
                vehicleDto.getRegistration(),
                vehicleDto.getNumberOfSeats(),
                vehicleDto.getService(),
                vehicleDto.getUrl(),
                vehicleDto.getEmission(),
                vehicleDto.getStatus(),
                motorizationDtoToEntity(vehicleDto.getMotorization()),
                modelDtoToEntity(vehicleDto.getModel()),
                categoryDtoToEntity(vehicleDto.getCategory()));
    }

    /**
     * Convertit un objet {@link MotorizationDto} en un objet {@link Motorization}
     * @param motorizationDto
     * @return
     */
    public static Motorization motorizationDtoToEntity(MotorizationDto motorizationDto){
        return new Motorization(
                motorizationDto.getName());
    }

    /**
     * Convertit un objet {@link ModelDto} en un objet {@link Model}
     * @param modelDto
     * @return
     */
    public static Model modelDtoToEntity(ModelDto modelDto){
        return new Model(
                modelDto.getName(),
                brandDtoToEntity(modelDto.getBrand()));
    }

    /**
     * Convertit un objet {@link CategoryDto} en un objet {@link Category}
     * @param categoryDto
     * @return
     */
    public static Category categoryDtoToEntity(CategoryDto categoryDto){
        return new Category(
                categoryDto.getName());
    }

    /**
     * Convertit un objet {@link BrandDto} en un objet {@link Brand}
     * @param brandDto
     * @return
     */
    public static Brand brandDtoToEntity(BrandDto brandDto){
        return new Brand(
                brandDto.getName());
    }

    public static VehiculeServiceReservationDto reservationVehicleToDto(ReservationVehicle reserveVehicle){
        return new VehiculeServiceReservationDto(
                reserveVehicle.getId(),
                reserveVehicle.getStartDate(),
                reserveVehicle.getEndDate(),
                vehicleToDto(reserveVehicle.getVehicle()));
    }

    public static UserDetails toUserDetails (Collaborator userAccount){
        System.out.println(userAccount.getEmail());
        return User.builder().username(userAccount.getEmail()).password(userAccount.getPassword()).authorities(userAccount.getAuthorities()).build();
    }
}
