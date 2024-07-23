package com.driveit.driveit._exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Collectors;

@ControllerAdvice
public class exceptionManager {

    private final WebApplicationContext webApplicationContext;

    public exceptionManager(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @ExceptionHandler({appException.class})
    public ResponseEntity<String> traiterErreurs(appException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(errorMessage);
    }

	@ExceptionHandler({NotFoundException.class})
	public ResponseEntity<String> manageNotFound() {
		return ResponseEntity.notFound().build();
	}
}
