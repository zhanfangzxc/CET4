package com.yingshibao.cet4.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class TreeCourseInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private int errorCode;
	private ArrayList<Course> courseJsons;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public ArrayList<Course> getCourseJsons() {
		return courseJsons;
	}

	public void setCourseJsons(ArrayList<Course> courseJsons) {
		this.courseJsons = courseJsons;
	}

	@Override
	public String toString() {
		return "TreeCourseInfo [errorCode=" + errorCode + ", courseJsons="
				+ courseJsons + "]";
	}

}
