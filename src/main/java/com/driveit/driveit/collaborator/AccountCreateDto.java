package com.driveit.driveit.collaborator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountCreateDto(
        @Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email,
        @NotBlank(message = "Password is required") @Size(min = 5, message = "Password must be at least 8 characters long") String password,
        @NotBlank(message = "First name is required") String firstName,
        @NotBlank(message = "Last name is required") String lastName
){
}
