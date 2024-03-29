package com.emp.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
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

	public LocalDate getDate() {
		return date;
	}

	public String getStatus() {
		return status;
	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
	
	
}
