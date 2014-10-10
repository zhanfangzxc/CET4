package com.yingshibao.cet4.bean;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String title;


	private String textExplain;
	private String videoExplainUrl;
	private int rightOpt;
	private int userOpt;
	private List<Option> answerJsons;

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

	@Override
	public String toString() {
		return "Question [id=" + id + ", title=" + title + ", textExplain="
				+ textExplain + ", videoExplainUrl=" + videoExplainUrl
				+ ", rightOpt=" + rightOpt + ", userOpt=" + userOpt
				+ ", answerJsons=" + answerJsons + "]";
	}

	public List<Option> getAnswerJsons() {
		return answerJsons;
	}

	public void setAnswerJsons(List<Option> answerJsons) {
		this.answerJsons = answerJsons;
	}


}
