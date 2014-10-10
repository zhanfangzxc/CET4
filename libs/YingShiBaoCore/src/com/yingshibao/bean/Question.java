package com.yingshibao.bean;

import java.util.ArrayList;

public class Question {

	private int id;
	private String title;
	private String textExplain;
	private String videoExplainUrl;
	private int rightOpt;
	private int userOpt;
	private ArrayList<Answer> answerJsons;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTextExplain() {
		return textExplain;
	}

	public void setTextExplain(String textExplain) {
		this.textExplain = textExplain;
	}

	public String getVideoExplainUrl() {
		return videoExplainUrl;
	}

	public void setVideoExplainUrl(String videoExplainUrl) {
		this.videoExplainUrl = videoExplainUrl;
	}

	public int getRightOpt() {
		return rightOpt;
	}

	public void setRightOpt(int rightOpt) {
		this.rightOpt = rightOpt;
	}

	public int getUserOpt() {
		return userOpt;
	}

	public void setUserOpt(int userOpt) {
		this.userOpt = userOpt;
	}

	public ArrayList<Answer> getAnswerJsons() {
		return answerJsons;
	}

	public void setAnswerJsons(ArrayList<Answer> answerJsons) {
		this.answerJsons = answerJsons;
	}

}
