package com.yingshibao.bean.request;

import java.util.ArrayList;

public class PracticeRecord {

	private String token;
	private int courseId;
	private double progress;
	private ArrayList<Question> questionRecordJsons;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public ArrayList<Question> getQuestionRecordJsons() {
		return questionRecordJsons;
	}

	public void setQuestionRecordJsons(ArrayList<Question> questionRecordJsons) {
		this.questionRecordJsons = questionRecordJsons;
	}

}
