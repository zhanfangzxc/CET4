package com.yingshibao.bean;

public class Word {

	private int id;
	private String name;
	private String mean;
	private String soundmark;
	private String videoUrl;
	private String audioUrl;
	private String example;
	private int unKnownFlag;
	private String phrase;
	private String exampleAudioUrl;
	private int examTimes;
	private String synonym;
	private String antonym;
	private String mnemonic;

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

	public String getMean() {
		return mean;
	}

	public void setMean(String mean) {
		this.mean = mean;
	}

	public String getSoundmark() {
		return soundmark;
	}

	public void setSoundmark(String soundmark) {
		this.soundmark = soundmark;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public int getUnKnownFlag() {
		return unKnownFlag;
	}

	public void setUnKnownFlag(int unKnownFlag) {
		this.unKnownFlag = unKnownFlag;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public String getExampleAudioUrl() {
		return exampleAudioUrl;
	}

	public void setExampleAudioUrl(String exampleAudioUrl) {
		this.exampleAudioUrl = exampleAudioUrl;
	}

	public int getExamTimes() {
		return examTimes;
	}

	public void setExamTimes(int examTimes) {
		this.examTimes = examTimes;
	}

	public String getSynonym() {
		return synonym;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	public String getAntonym() {
		return antonym;
	}

	public void setAntonym(String antonym) {
		this.antonym = antonym;
	}

	public String getMnemonic() {
		return mnemonic;
	}

	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}

	@Override
	public String toString() {
		return "Word [id=" + id + ", name=" + name + ", mean=" + mean
				+ ", soundmark=" + soundmark + ", videoUrl=" + videoUrl
				+ ", audioUrl=" + audioUrl + ", example=" + example
				+ ", unKnownFlag=" + unKnownFlag + ", phrase=" + phrase
				+ ", exampleAudioUrl=" + exampleAudioUrl + ", examTimes="
				+ examTimes + ", synonym=" + synonym + ", antonym=" + antonym
				+ ", mnemonic=" + mnemonic + "]";
	}

}
