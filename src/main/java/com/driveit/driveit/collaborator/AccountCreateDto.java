package com.driveit.driveit.collaborator;

import jakarta.validation.constraints.NotNull;

public record AccountCreateDto(@NotNull String email, @NotNull String password, @NotNull String firstName, @NotNull String lastName){
}
