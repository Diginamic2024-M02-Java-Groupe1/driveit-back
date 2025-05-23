package com.driveit.driveit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JacksonConfigTest {


    @Test
    void testJavaTimeModuleRegistration() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        LocalDateTime now = LocalDateTime.now();
        String serialized = objectMapper.writeValueAsString(now);
        LocalDateTime deserialized = objectMapper.readValue(serialized, LocalDateTime.class);

        assertEquals(now, deserialized, "Le module JavaTime n'est pas correctement enregistré.");
    }
}
