package com.driveit.driveit._exceptions;

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

import java.security.SignatureException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionManager {

    @ExceptionHandler({AppException.class})
    public ResponseEntity<ApiError> traiterErreurs(AppException e) {
        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            e.getMessage(),
            "Erreur d'application"
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        ApiError apiError = new ApiError(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            errorMessage,
            "Violation de contrainte"
        );
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiError> manageNotFound(NotFoundException ex) {
        ApiError apiError = new ApiError(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            "Ressource non trouvée"
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException() {
        ApiError apiError = new ApiError(
            HttpStatus.UNAUTHORIZED.value(),
            "L'email ou le mot de passe est incorrect",
            "Authentification échouée"
        );
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<ApiError> handleAccountStatusException() {
        ApiError apiError = new ApiError(
            HttpStatus.UNAUTHORIZED.value(),
            "Le compte est verrouillé",
            "Compte bloqué"
        );
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
        ApiError apiError = new ApiError(
            HttpStatus.FORBIDDEN.value(),
            "Accès refusé: " + ex.getMessage(),
            "Accès interdit"
        );
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiError> handleSignatureException() {
        ApiError apiError = new ApiError(
            HttpStatus.UNAUTHORIZED.value(),
            "Token invalide",
            "Erreur de signature"
        );
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiError> handleNullPointerException() {
        ApiError apiError = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Exception de pointeur null",
            "Erreur serveur"
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}