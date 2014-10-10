package com.yingshibao.cet4.bean;

import java.util.ArrayList;

public class Colleges {
	private String colleges_json;
	private ArrayList<College> collegeList;
	protected Integer errorCode;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public ArrayList<College> getCollegeList() {
		return collegeList;
	}

	public void setCollegeList(ArrayList<College> collegeList) {
		this.collegeList = collegeList;
	}

	public String getColleges_json() {
		return colleges_json;
	}

	public void setColleges_json(String colleges_json) {
		this.colleges_json = colleges_json;
	}

}
