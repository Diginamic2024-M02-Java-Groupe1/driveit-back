package com.driveit.driveit.utils;

import com.driveit.driveit.address.Address;
import com.driveit.driveit.address.AddressDto;
import com.driveit.driveit.carpooling.Carpooling;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.country.Country;
import com.driveit.driveit.carpooling.CarpoolingDto;
import com.driveit.driveit.collaborator.CollaboratorDto;
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
//    public static CarpoolingDto carpoolingToDto(Carpooling carpooling) {
//        CarpoolingDto carpoolingDto = new CarpoolingDto();
//        carpoolingDto.setId(carpooling.getId());
//        carpoolingDto.setDepartureDate(carpooling.getDepartureDate());
//        carpoolingDto.setArrivalDate(carpooling.getArrivalDate());
//        carpoolingDto.setOrganizer(collaboratorToDto(carpooling.getOrganizer()));
//        carpoolingDto.setDepartureAddress(addressToDto(carpooling.getDepartureAddress()));
//        carpoolingDto.setArrivalAddress(addressToDto(carpooling.getArrivalAddress()));
//        List<Collaborator> participants = carpooling.getReservations().stream().map(ReservationCollaborator::getCollaborator).toList();
//        carpoolingDto.setParticipants(participants.stream().map(Mapper::collaboratorToDto).toList());
//        return carpoolingDto;
//    }

    /**
     * Convertit un objet {@link Collaborator} en un objet {@link CollaboratorDto}
     * @param collaborator : le collaborateur à convertir
     * @return le collaborateur converti
     */
    public static CollaboratorDto collaboratorToDto(Collaborator collaborator) {
        CollaboratorDto collaboratorDto = new CollaboratorDto();
        collaboratorDto.setId(collaborator.getId());
        collaboratorDto.setFirstName(collaborator.getFirstName());
        collaboratorDto.setLastName(collaborator.getLastName());
        collaboratorDto.setRole(collaborator.getRole());
        return collaboratorDto;
    }

    /**
     * Convertit un objet {@link Address} en un objet {@link AddressDto}
     * @param address : l'adresse à convertir
     * @return l'adresse convertie
//     */
//    public static AddressDto addressToDto(Address address) {
//        AddressDto addressDto = new AddressDto();
//        addressDto.setId(address.getId());
//        addressDto.setStreetNumber(address.getStreetNumber());
//        addressDto.setStreetName(address.getStreetName());
//        addressDto.setCity(address.getCity());
//        addressDto.setPostalCode(address.getPostalCode());
//        addressDto.setCountry(countryToDto(address.getCountry()));
//        return addressDto;
//    }

    /**
     * Convertit un objet {@link Country} en un objet {@link CountryDto}
     * @param country : le pays à convertir
     * @return le pays converti
     */
    public static CountryDto countryToDto(Country country) {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(country.getId());
        countryDto.setName(country.getName());
        return countryDto;
    }
}
