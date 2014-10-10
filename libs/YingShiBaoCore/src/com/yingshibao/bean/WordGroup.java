package com.yingshibao.bean;

public class WordGroup {

	private int id;
	private String name;
	private int totalWordCount;
	private double progress;
	private int examLevel;

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

	public int getTotalWordCount() {
		return totalWordCount;
	}

	public void setTotalWordCount(int totalWordCount) {
		this.totalWordCount = totalWordCount;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public int getExamLevel() {
		return examLevel;
	}

	public void setExamLevel(int examLevel) {
		this.examLevel = examLevel;
	}

}
