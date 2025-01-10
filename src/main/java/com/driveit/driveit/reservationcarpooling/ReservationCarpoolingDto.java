package com.driveit.driveit.reservationcarpooling;

import com.driveit.driveit.carpooling.CarpoolingDto;
import com.driveit.driveit.collaborator.CollaboratorDto;

public record ReservationCarpoolingDto(
        int id,
        StatusReservationCarpooling status,
        CollaboratorDto collaborator,
        CarpoolingDto carpooling
)
{ }
