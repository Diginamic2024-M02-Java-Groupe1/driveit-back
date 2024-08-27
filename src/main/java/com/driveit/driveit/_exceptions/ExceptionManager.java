package com.driveit.driveit._exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequest;

import java.security.SignatureException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionManager {

    private final WebApplicationContext webApplicationContext;

    public ExceptionManager(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

	@ExceptionHandler({AppException.class})
	public ResponseEntity<String> traiterErreurs(AppException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(errorMessage);
    }

	@ExceptionHandler({NotFoundException.class})
	public ResponseEntity<String> manageNotFound() {
		return ResponseEntity.notFound().build();
	}

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        return new ResponseEntity<>("The email or password is incorrect", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<String> handleAccountStatusException(AccountStatusException ex, WebRequest request) {
        return new ResponseEntity<>("The account is locked", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return new ResponseEntity<>("Access denied: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException(SignatureException ex, WebRequest request) {
        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        return new ResponseEntity<>("Token expired", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex, WebRequest request) {
        return new ResponseEntity<>("Null pointer exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
