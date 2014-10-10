package com.yingshibao.cet4.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 生词本
 * @author zhaoshan
 *
 */
public class UnKnownWords implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//响应码
	private int errorCode;
	
	//单词列表
	private List<Word> wordJsons;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public List<Word> getWordJsons() {
		return wordJsons;
	}

	public void setWordJsons(List<Word> wordJsons) {
		this.wordJsons = wordJsons;
	}

	@Override
	public String toString() {
		return "UnKnownWords [errorCode=" + errorCode + ", wordJsons="
				+ wordJsons + "]";
	}
}
