package com.yingshibao.cet4.bean;

import java.io.Serializable;
import java.util.List;

public class Practice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Practice [id=" + id + ", name=" + name + ", content=" + content
				+ ", audioUrl=" + audioUrl + ", questionJson=" + questionJson
				+ "]";
	}

	private int id;
	private String name;
	private String content;
	private String audioUrl;// 音频
	private List<Question> questionJson;
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

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

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public List<Question> getQuestionJson() {
		return questionJson;
	}

	public void setQuestionJson(List<Question> questionJson) {
		this.questionJson = questionJson;
	}

}
