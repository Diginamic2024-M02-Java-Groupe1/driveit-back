package com.driveit.driveit;

import com.driveit.driveit._auth.RegisterUserDto;
import com.driveit.driveit._utils.FakerUtils;
import com.driveit.driveit.collaborator.*;
import com.driveit.driveit.reservationcarpooling.ReservationCarpoolingDto;
import com.driveit.driveit.reservationcarpooling.StatusReservationCarpooling;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CollaboratorController.class)
public class CollaboratorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollaboratorService collaboratorService;

    @Test
    public void getReservationsCarpoolings() throws Exception {
        // Mock the behavior of collaboratorService to return a list of ReservationCollaboratorDto
        given(collaboratorService.getReservations(1)).willReturn(
                List.of(
                        new ReservationCarpoolingDto(
                                1, StatusReservationCarpooling.PENDING,
                                new CollaboratorDto(1,"Ayoub","Benziza","collaborator", List.of(new SimpleGrantedAuthority("ROLE_COLLABORATOR"))),
                                null
                        )
                ));

        mockMvc.perform(get("/api/collaborators/1/reservations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createCollaborator() throws Exception {
    // Generate a fake collaborator using FakerUtils
    Collaborator fakeCollaborator = FakerUtils.generateFakeCollaborator();

    // Mock the service layer to return the fake collaborator
    given(collaboratorService.saveCollaborator(any(RegisterUserDto.class))).willReturn(fakeCollaborator);

    // Create an AccountCreateDto object (you might need to adjust fields according to your actual DTO)
    RegisterUserDto registerUserDto = new RegisterUserDto(fakeCollaborator.getEmail(), "password", fakeCollaborator.getFirstName(), fakeCollaborator.getLastName());

    // Convert accountCreateDto to JSON
    ObjectMapper objectMapper = new ObjectMapper();
    String accountCreateDtoJson = objectMapper.writeValueAsString(registerUserDto);

    // Perform the POST request and assert the results
    mockMvc.perform(post("/api/collaborators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(accountCreateDtoJson))
            .andExpect(status().isCreated()) // Assuming the endpoint returns 201 on success
            .andExpect(jsonPath("$.email").value(fakeCollaborator.getEmail()))
            .andExpect(jsonPath("$.firstName").value(fakeCollaborator.getFirstName()))
            .andExpect(jsonPath("$.lastName").value(fakeCollaborator.getLastName()));
}
}
