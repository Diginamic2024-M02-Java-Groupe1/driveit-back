package com.driveit.driveit.carpooling;

import com.driveit.driveit.collaborator.CollaboratorDto;

public record ParticipantStatusDto(CollaboratorDto collaborator,String status) {
}
