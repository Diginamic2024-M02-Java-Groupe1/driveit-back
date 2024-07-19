package com.driveit.driveit._exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GestionnaireException {

	@ExceptionHandler({AnomalieException.class})
	public ResponseEntity<String> traiterErreurs(AnomalieException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
