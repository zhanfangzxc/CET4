package com.yingshibao.cet4.bean;

import java.io.Serializable;

/**
 * 
 * 单词详细信息
 * 
 * @author zhaoshan
 * 
 */
public class Word implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id 单词id
	 */
	private int id;

	/**
	 * g_id 单词组id
	 */
	private String g_id;

	private String name;// 单词名称
	private String mean;// 单词意义
	private String soundmark;// 单词音标
	private String vudeoUrl;// 单词视频
	private String audioUrl;// 单词音频
	private String example;// 实例
	private int unknownFlag;// 是否知道
	private String phrase;// 搭配
	private String exampleAudioUrl;// 例句音频
	private String examTimes;// 考频次数
	private String synonym;// 同义词
	private String antonym;// 反义词
	private String mnemonic;// 助记

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getG_id() {
		return g_id;
	}

	public void setG_id(String g_id) {
		this.g_id = g_id;
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

	public String getVudeoUrl() {
		return vudeoUrl;
	}

	public void setVudeoUrl(String vudeoUrl) {
		this.vudeoUrl = vudeoUrl;
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

	public int getUnknownFlag() {
		return unknownFlag;
	}

	public void setUnknownFlag(int unknownFlag) {
		this.unknownFlag = unknownFlag;
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

	public String getExamTimes() {
		return examTimes;
	}

	public void setExamTimes(String examTimes) {
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
		return "Word [id=" + id + ", g_id=" + g_id + ", name=" + name
				+ ", mean=" + mean + ", soundmark=" + soundmark + ", vudeoUrl="
				+ vudeoUrl + ", audioUrl=" + audioUrl + ", example=" + example
				+ ", unknownFlag=" + unknownFlag + ", phrase=" + phrase
				+ ", exampleAudioUrl=" + exampleAudioUrl + ", examTimes="
				+ examTimes + ", synonym=" + synonym + ", antonym=" + antonym
				+ ", mnemonic=" + mnemonic + "]";
	}
	
}
