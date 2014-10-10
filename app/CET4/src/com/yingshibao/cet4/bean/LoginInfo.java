package com.yingshibao.cet4.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author malinkang 登陆信息
 * 
 */
public class LoginInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String token;
	private int errorCode;
	private List<Course> courseJsons = new ArrayList<Course>();

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public List<Course> getCourseJsons() {
		return courseJsons;
	}

	public void setCourseJsons(List<Course> courseJsons) {
		this.courseJsons = courseJsons;
	}

	@Override
	public String toString() {
		return "LoginInfo [token=" + token + ", errorCode=" + errorCode
				+ ", courseJsons=" + courseJsons + "]";
	}

}
