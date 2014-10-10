package com.yingshibao.cet4.bean;

import java.io.Serializable;

public class WordGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	
	// 单词分组编号
	private String id;
	// 进度
	private double progress;
	// 组内单词数目
	private int totalWordCount;

	// 单词分组名称
	private String name;

	// 考频等级,0无效,1低频,2中频,3高频
	private int examLevel;

	public int getExamLevel() {
		return examLevel;
	}

	public void setExamLevel(int examLevel) {
		this.examLevel = examLevel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public int getTotalWordCount() {
		return totalWordCount;
	}

	public void setTotalWordCount(int totalWordCount) {
		this.totalWordCount = totalWordCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "WordGroup [id=" + id + ", progress=" + progress
				+ ", totalWordCount=" + totalWordCount + ", name=" + name
				+ ", examLevel=" + examLevel + "]";
	}
	
	

}
