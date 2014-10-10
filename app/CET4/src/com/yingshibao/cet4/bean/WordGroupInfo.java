/**
 * Title：WordGroupInfo.java
 * Copyright:云电同方科技有限公司
 * Company：云电同方科技有限公司
 */
package com.yingshibao.cet4.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author hwm 2014-1-15 1.0
 */
public class WordGroupInfo implements Serializable {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	private int errorCode;// 错误码 零成功,非零失败
	private int totalWordCount;// 单词总数
	private int groupCount;// 总单词分组
	private int knownWordsCount;// 知道的单词数目
	private int unknownWordsCount;// 不知道的单词数目
	private ArrayList<WordGroup> wordJsonGroups;// 单词分组列表

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getTotalWordCount() {
		return totalWordCount;
	}

	public void setTotalWordCount(int totalWordCount) {
		this.totalWordCount = totalWordCount;
	}

	public int getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}

	public int getKnownWordsCount() {
		return knownWordsCount;
	}

	public void setKnownWordsCount(int knownWordsCount) {
		this.knownWordsCount = knownWordsCount;
	}

	public int getUnknownWordsCount() {
		return unknownWordsCount;
	}

	public void setUnknownWordsCount(int unknownWordsCount) {
		this.unknownWordsCount = unknownWordsCount;
	}

	public ArrayList<WordGroup> getWordJsonGroups() {
		return wordJsonGroups;
	}

	public void setWordJsonGroups(ArrayList<WordGroup> wordJsonGroups) {
		this.wordJsonGroups = wordJsonGroups;
	}

}
