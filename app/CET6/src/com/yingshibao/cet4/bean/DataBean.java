package com.yingshibao.cet4.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class DataBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String AssureTimeLength;
	private String Brief;
	private String Cost;
	private String ClassHour;
	private String CourseID;
	private String CourseName;
	private String CreateTime;
	private String ExpiryTime;
	private String LiveStudentLimit;
	private String PayStudentLimit;
	private String PhotoURL;
	private String SID;
	private String SaleStatus;
	private String Status;
	private String StudentNumber;
	private String TrialNumber;
	private ArrayList<ChapterList> ChapterList;

	public ArrayList<ChapterList> getChapterList() {
		return ChapterList;
	}

	public void setChapterList(ArrayList<ChapterList> chapterList) {
		ChapterList = chapterList;
	}

	public String getAssureTimeLength() {
		return AssureTimeLength;
	}

	public void setAssureTimeLength(String assureTimeLength) {
		AssureTimeLength = assureTimeLength;
	}

	public String getBrief() {
		return Brief;
	}

	public void setBrief(String brief) {
		Brief = brief;
	}

	public String getCost() {
		return Cost;
	}

	public void setCost(String cost) {
		Cost = cost;
	}

	public String getClassHour() {
		return ClassHour;
	}

	public void setClassHour(String classHour) {
		ClassHour = classHour;
	}

	public String getCourseID() {
		return CourseID;
	}

	public void setCourseID(String courseID) {
		CourseID = courseID;
	}

	public String getCourseName() {
		return CourseName;
	}

	public void setCourseName(String courseName) {
		CourseName = courseName;
	}

	@Override
	public String toString() {
		return "DataBean [AssureTimeLength=" + AssureTimeLength + ", Brief="
				+ Brief + ", Cost=" + Cost + ", ClassHour=" + ClassHour
				+ ", CourseID=" + CourseID + ", CourseName=" + CourseName
				+ ", CreateTime=" + CreateTime + ", ExpiryTime=" + ExpiryTime
				+ ", LiveStudentLimit=" + LiveStudentLimit
				+ ", PayStudentLimit=" + PayStudentLimit + ", PhotoURL="
				+ PhotoURL + ", SID=" + SID + ", SaleStatus=" + SaleStatus
				+ ", Status=" + Status + ", StudentNumber=" + StudentNumber
				+ ", TrialNumber=" + TrialNumber + "]";
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getExpiryTime() {
		return ExpiryTime;
	}

	public void setExpiryTime(String expiryTime) {
		ExpiryTime = expiryTime;
	}

	public String getLiveStudentLimit() {
		return LiveStudentLimit;
	}

	public void setLiveStudentLimit(String liveStudentLimit) {
		LiveStudentLimit = liveStudentLimit;
	}

	public String getPayStudentLimit() {
		return PayStudentLimit;
	}

	public void setPayStudentLimit(String payStudentLimit) {
		PayStudentLimit = payStudentLimit;
	}

	public String getPhotoURL() {
		return PhotoURL;
	}

	public void setPhotoURL(String photoURL) {
		PhotoURL = photoURL;
	}

	public String getSID() {
		return SID;
	}

	public void setSID(String sID) {
		SID = sID;
	}

	public String getSaleStatus() {
		return SaleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		SaleStatus = saleStatus;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getStudentNumber() {
		return StudentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		StudentNumber = studentNumber;
	}

	public String getTrialNumber() {
		return TrialNumber;
	}

	public void setTrialNumber(String trialNumber) {
		TrialNumber = trialNumber;
	}

}
