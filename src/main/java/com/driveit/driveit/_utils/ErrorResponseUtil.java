package com.driveit.driveit._utils;

import com.driveit.driveit._exceptions.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class ErrorResponseUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message, String error) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiError apiError = new ApiError(status.value(), message, error);

        String jsonResponse = objectMapper.writeValueAsString(apiError);
        response.getWriter().write(jsonResponse);
    }
}