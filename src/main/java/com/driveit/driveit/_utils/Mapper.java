package com.driveit.driveit._utils;

import com.driveit.driveit.address.Address;
import com.driveit.driveit.address.AddressDto;
import com.driveit.driveit.carpooling.Carpooling;
import com.driveit.driveit.carpooling.CarpoolingDto;
import com.driveit.driveit.cityZipCode.City;
import com.driveit.driveit.cityZipCode.CityZipCode;
import com.driveit.driveit.cityZipCode.ZipCode;
import com.driveit.driveit.cityZipCode.dto.CityDto;
import com.driveit.driveit.cityZipCode.dto.CityZipCodeDto;
import com.driveit.driveit.cityZipCode.dto.ZipCodeDto;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorDto;
import com.driveit.driveit.country.Country;
import com.driveit.driveit.country.CountryDto;
import com.driveit.driveit.reservationcollaborator.ReservationCollaborator;

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
        CarpoolingDto carpoolingDto = new CarpoolingDto();
        carpoolingDto.setDepartureDate(carpooling.getDepartureDate());
        carpoolingDto.setArrivalDate(carpooling.getArrivalDate());
        carpoolingDto.setOrganizer(collaboratorToDto(carpooling.getOrganizer()));
        carpoolingDto.setDepartureAddress(addressToDto(carpooling.getDepartureAddress()));
        carpoolingDto.setArrivalAddress(addressToDto(carpooling.getArrivalAddress()));
        List<Collaborator> participants = carpooling.getReservations().stream().map(ReservationCollaborator::getCollaborator).toList();
        carpoolingDto.setParticipants(participants.stream().map(Mapper::collaboratorToDto).toList());
        return carpoolingDto;
    }

    /**
     * Convertit un objet {@link Collaborator} en un objet {@link CollaboratorDto}
     *
     * @param collaborator : le collaborateur à convertir
     * @return le collaborateur converti
     */
    public static CollaboratorDto collaboratorToDto(Collaborator collaborator) {
        CollaboratorDto collaboratorDto = new CollaboratorDto();
        collaboratorDto.setFirstName(collaborator.getFirstName());
        collaboratorDto.setLastName(collaborator.getLastName());
        collaboratorDto.setRole(collaborator.getRole());
        return collaboratorDto;
    }

    /**
     * Convertit un objet {@link Address} en un objet {@link AddressDto}
     *
     * @param address : l'adresse à convertir
     * @return l'adresse convertie
     */
    public static AddressDto addressToDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setStreetNumber(address.getStreetNumber());
        addressDto.setStreetName(address.getStreetName());
        addressDto.setCityZipCode(cityZipCodeToDto(address.getCityZipCode()));
        addressDto.setCountry(countryToDto(address.getCountry()));
        return addressDto;
    }

    /**
     * Convertit un objet {@link Country} en un objet {@link CountryDto}
     *
     * @param country : le pays à convertir
     * @return le pays converti
     */
    public static CountryDto countryToDto(Country country) {
        CountryDto countryDto = new CountryDto();
        countryDto.setName(country.getName());
        return countryDto;
    }


    /**
     * Convertit un objet {@link CityZipCode} en un objet {@link CityZipCodeDto}
     *
     * @param cityZipCode : la ville à convertir
     * @return la ville convertie
     */
    public static CityZipCodeDto cityZipCodeToDto(CityZipCode cityZipCode) {
        CityZipCodeDto cityZipCodeDto = new CityZipCodeDto();
        cityZipCodeDto.setCity(cityToDto(cityZipCode.getCity()));
        cityZipCodeDto.setCode(zipCodeToDto(cityZipCode.getZipCode()));
        return cityZipCodeDto;
    }

    /**
     * Convertit un objet {@link City} en un objet {@link CityDto}
     *
     * @param city : la ville à convertir
     * @return la ville convertie
     */
    public static CityDto cityToDto(City city) {
        CityDto cityDto = new CityDto();
        cityDto.setName(city.getName());
        return cityDto;
    }

    /**
     * Convertit un objet {@link ZipCode} en un objet {@link ZipCodeDto}
     *
     * @param zipCode : le code postal à convertir
     * @return le code postal converti
     */
    public static ZipCodeDto zipCodeToDto(ZipCode zipCode) {
        ZipCodeDto zipCodeDto = new ZipCodeDto();
        zipCodeDto.setCode(zipCode.getCode());
        return zipCodeDto;
    }
}
