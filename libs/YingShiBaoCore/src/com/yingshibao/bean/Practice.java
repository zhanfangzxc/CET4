package com.yingshibao.bean;

import java.util.ArrayList;

public class Practice {

	private int id;
	private String name;
	private String content;
	private String andioUrl;
	private ArrayList<Question> questionJson;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAndioUrl() {
		return andioUrl;
	}

	public void setAndioUrl(String andioUrl) {
		this.andioUrl = andioUrl;
	}

	public ArrayList<Question> getQuestionJson() {
		return questionJson;
	}

	public void setQuestionJson(ArrayList<Question> questionJson) {
		this.questionJson = questionJson;
	}

}
