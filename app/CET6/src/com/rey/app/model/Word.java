package com.rey.app.model;

import java.io.Serializable;

public class Word implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id 单词id
	 */
	private String id;

	/**
	 * g_id 单词组id
	 */
	private String g_id;

	/**
	 * content 单词内容
	 */
	private String content;
	
	/**
	 * yinbiao 单词音标
	 */
	private String yinbiao;
	
	/**
	 * comment 中文解释
	 */
	private String comment;
	
	/**
	 * example 示例
	 */
	private String example;
	
	/**
	 * voice 单词音频
	 */
	private String voice;
	
	/**
	 * video 单词视频
	 */
	private String video;
	
	/**
	 * know 是否知道
	 */
	private int know;
	
	/**
	 * issyn 是否已经同步到服务器 0-未同步 1-同步
	 */
	private int issyn=0;
	
	private boolean isSelected=true;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getG_id() {
		return g_id;
	}

	public void setG_id(String g_id) {
		this.g_id = g_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getYinbiao() {
		return yinbiao;
	}

	public void setYinbiao(String yinbiao) {
		this.yinbiao = yinbiao;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public int getKnow() {
		return know;
	}

	public void setKnow(int know) {
		this.know = know;
	}
	
	public int getIssyn() {
		return issyn;
	}

	public void setIssyn(int issyn) {
		this.issyn = issyn;
	}
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public String toString() {
		return "Word [id=" + id + ", g_id=" + g_id + ", content=" + content
				+ ", yinbiao=" + yinbiao + ", comment=" + comment
				+ ", example=" + example + ", voice=" + voice + ", video="
				+ video + ", know=" + know + "]";
	}
	
}
