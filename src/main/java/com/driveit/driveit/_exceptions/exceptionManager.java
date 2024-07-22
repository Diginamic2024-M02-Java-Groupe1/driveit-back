package com.driveit.driveit._exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class exceptionManager {

	@ExceptionHandler({appException.class})
	public ResponseEntity<String> traiterErreurs(appException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
