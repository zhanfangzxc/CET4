package com.rey.app.model;

import java.io.Serializable;

public class Info implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	
	private String description;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
