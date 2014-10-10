package com.yingshibao.bean;

import java.util.ArrayList;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.yingshibao.db.WordGroupsTable;

@Table(name = WordGroupsTable.TABLE_NAME)
public class WordGroups extends BaseBean {

	@Column(name = WordGroupsTable.COLUMN_TOTALWORDCOUNT)
	private int totalWordCount;
	@Column(name = WordGroupsTable.COLUMN_GROUPCOUNT)
	private int groupCount;
	@Column(name = WordGroupsTable.COLUMN_KNOWNWORDSCOUNT)
	private int knownWordsCount;
	private ArrayList<WordGroup> wordJsonGroups;
	@Column(name = WordGroupsTable.COLUMN_WORDJSONGROUPSJSON)
	private String wordJsonGroups_json;

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

	public ArrayList<WordGroup> getWordJsonGroups() {
		return wordJsonGroups;
	}

	public void setWordJsonGroups(ArrayList<WordGroup> wordJsonGroups) {
		this.wordJsonGroups = wordJsonGroups;
	}

	@Override
	public String toString() {
		return "WordGroups [totalWordCount=" + totalWordCount + ", groupCount="
				+ groupCount + ", knownWordsCount=" + knownWordsCount
				+ ", wordJsonGroups=" + wordJsonGroups
				+ ", wordJsonGroups_json=" + wordJsonGroups_json + "]";
	}

}
