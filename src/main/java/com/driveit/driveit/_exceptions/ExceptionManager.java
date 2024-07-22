package com.driveit.driveit._exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionManager {

	@ExceptionHandler({AppException.class})
	public ResponseEntity<String> traiterErreurs(AppException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler({NotFoundException.class})
	public ResponseEntity<String> manageNotFound() {
		return ResponseEntity.notFound().build();
	}
}
