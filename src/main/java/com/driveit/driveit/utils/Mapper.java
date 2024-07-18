package com.driveit.driveit.utils;

import com.driveit.driveit.address.Address;
import com.driveit.driveit.address.AddressDto;
import com.driveit.driveit.carpooling.Carpooling;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.country.Country;
import com.driveit.driveit.carpooling.CarpoolingDto;
import com.driveit.driveit.collaborator.CollaboratorDto;
import com.driveit.driveit.country.CountryDto;
import com.driveit.driveit.reservation.Reservation;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    public static CarpoolingDto CarpoolingToDto(Carpooling carpooling) {
        CarpoolingDto carpoolingDto = new CarpoolingDto();
        carpoolingDto.setId(carpooling.getId());
        carpoolingDto.setDepartureDate(carpooling.getDepartureDate());
        carpoolingDto.setArrivalDate(carpooling.getArrivalDate());
        carpoolingDto.setOrganizer(collaboratorToDto(carpooling.getOrganizer()));
        carpoolingDto.setDepartureAddress(addressToDto(carpooling.getDepartureAddress()));
        carpoolingDto.setArrivalAddress(addressToDto(carpooling.getArrivalAddress()));
        List<Reservation> participants = carpooling.getParticipants();
        List<CollaboratorDto> participantsDto = carpooling.getReservations().stream().map(participant -> collaboratorToDto(participant.getCollaborator())).collect(Collectors.toList());
        carpoolingDto.setParticipants(participantsDto);
        return carpoolingDto;
    }

    public static CollaboratorDto collaboratorToDto(Collaborator collaborator) {
        CollaboratorDto collaboratorDto = new CollaboratorDto();
        collaboratorDto.setId(collaborator.getId());
        collaboratorDto.setFirstName(collaborator.getFirstName());
        collaboratorDto.setLastName(collaborator.getLastName());
        collaboratorDto.setRole(collaborator.getRole());
        return collaboratorDto;
    }

    public static AddressDto addressToDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setStreetNumber(address.getStreetNumber());
        addressDto.setStreetName(address.getStreetName());
        addressDto.setCity(address.getCity());
        addressDto.setPostalCode(address.getPostalCode());
        addressDto.setCountry(countryToDto(address.getCountry()));
        return addressDto;
    }

    public static CountryDto countryToDto(Country country) {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(country.getId());
        countryDto.setName(country.getName());
        return countryDto;
    }

}
