package com.yingshibao.bean.request;

import java.util.ArrayList;

public class UnKnownFlag4Word {

	private String token;
	private double progress;
	private ArrayList<Word> flagWords;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public ArrayList<Word> getFlagWords() {
		return flagWords;
	}

	public void setFlagWords(ArrayList<Word> flagWords) {
		this.flagWords = flagWords;
	}

}
