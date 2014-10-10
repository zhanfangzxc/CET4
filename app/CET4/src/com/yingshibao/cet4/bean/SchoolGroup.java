package com.yingshibao.cet4.bean;

import java.util.ArrayList;

public class SchoolGroup {
	private ArrayList<School> collegeList;
	private int errorCode;

	public ArrayList<School> getCollegeList() {
		return collegeList;
	}

	public void setCollegeList(ArrayList<School> collegeList) {
		this.collegeList = collegeList;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
