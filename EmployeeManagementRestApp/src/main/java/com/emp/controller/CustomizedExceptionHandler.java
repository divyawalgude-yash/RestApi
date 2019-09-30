package com.emp.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.emp.model.EmployeeNotFoundException;
import com.emp.model.ErrorMessage;


@ControllerAdvice
public class CustomizedExceptionHandler{

	@ExceptionHandler(EmployeeNotFoundException.class)
	public final ResponseEntity<ErrorMessage> handleEmployeeNotFoundException(EmployeeNotFoundException e) {
		ErrorMessage error = new ErrorMessage(e.getMessage(), "Not Found", LocalDate.now());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorMessage> handleAllException(Exception e) {
		ErrorMessage error = new ErrorMessage(e.getMessage(), "Bad Request", LocalDate.now());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//		ErrorMessage error = new ErrorMessage("Validation Failed", "Bad Request", LocalDate.now());
//		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//	}
}
