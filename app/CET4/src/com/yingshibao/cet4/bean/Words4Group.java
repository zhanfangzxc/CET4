package com.yingshibao.cet4.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 分组内单词列表
 * 
 * @author zhaoshan
 * 
 */
public class Words4Group implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 错误码
	private int errorCode;
	// 进度
	private String progress;
	// 分组内单词列表
	private ArrayList<Word> wordJsons;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}


	public ArrayList<Word> getWordJsons() {
		return wordJsons;
	}

	public void setWordJsons(ArrayList<Word> wordJsons) {
		this.wordJsons = wordJsons;
	}


	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	@Override
	public String toString() {
		return "Words4Group [errorCode=" + errorCode + ", progress=" + progress
				+ ", wordJsons=" + wordJsons + "]";
	}

}
