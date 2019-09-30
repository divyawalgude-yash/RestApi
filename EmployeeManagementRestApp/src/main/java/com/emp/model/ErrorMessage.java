package com.emp.model;

import java.time.LocalDate;


public class ErrorMessage {

	private String Message;
	private String status;
	private LocalDate date;
	
	
	
	public ErrorMessage(String message, String status, LocalDate date) {
		super();
		Message = message;
		this.status = status;
		this.date = date;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
