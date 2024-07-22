package com.driveit.driveit.reservationcollaborator;

import com.driveit.driveit.carpooling.CarpoolingDto;
import com.driveit.driveit.collaborator.CollaboratorDto;

public record ReservationCollaboratorDto(
        int id,
        StatusReservationCollaborator status,
        CollaboratorDto collaborator,
        CarpoolingDto carpooling)
{ }
