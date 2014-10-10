package com.rey.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String token;
	private String errorCode;
	private List<Course> courseJsons = new ArrayList<Course>();

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public List<Course> getCourseJsons() {
		return courseJsons;
	}

	public void setCourseJsons(List<Course> courseJsons) {
		this.courseJsons = courseJsons;
	}

}
