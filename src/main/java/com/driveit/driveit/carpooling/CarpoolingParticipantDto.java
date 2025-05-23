package com.driveit.driveit.carpooling;

import com.driveit.driveit.address.AddressDto;
import com.driveit.driveit.collaborator.CollaboratorDto;
import com.driveit.driveit.vehicle.VehicleDto;
import java.time.LocalDateTime;
import java.util.List;

public record CarpoolingParticipantDto(
        int id,
        LocalDateTime departureDate,
        LocalDateTime arrivalDate,
        CollaboratorDto organizer,
        AddressDto departureAddress,
        AddressDto arrivalAddress,
        List<CollaboratorDto> participants,
        VehicleDto vehicle,
        String status
) {}

