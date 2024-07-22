package com.driveit.driveit;

import com.driveit.driveit.collaborator.CollaboratorController;
import com.driveit.driveit.collaborator.CollaboratorDto;
import com.driveit.driveit.collaborator.CollaboratorService;
import com.driveit.driveit.reservationcollaborator.ReservationCollaboratorDto;
import com.driveit.driveit.reservationcollaborator.StatusReservationCollaborator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CollaboratorController.class)
public class CollaboratorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollaboratorService collaboratorService;

    @Test
    public void testGetReservations() throws Exception {
        // Mock the behavior of collaboratorService to return a list of ReservationCollaboratorDto
        given(collaboratorService.getReservations(1)).willReturn(
                List.of(
                        new ReservationCollaboratorDto(
                                1, StatusReservationCollaborator.PENDING,
                                new CollaboratorDto(1,"Ayoub","Benziza","collaborator"),
                                null
                        )
                ));

        mockMvc.perform(get("/api/collaborators/1/reservations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
}
