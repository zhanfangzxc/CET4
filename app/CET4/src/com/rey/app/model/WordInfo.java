/**
 * Title：WordInfo.java
 * Copyright:云电同方科技有限公司
 * Company：云电同方科技有限公司
 */
package com.rey.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hwm
 * 2014-1-17
 * 1.0
 */
public class WordInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int errorCode;//错误码 零成功,非零失败
	
	private int progress;////进度
	
	private List<Word> words = new ArrayList<Word>();

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}

}
