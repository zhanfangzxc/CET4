package com.yingshibao.bean;

import java.util.ArrayList;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.yingshibao.db.UnknownWordsTable;

@Table(name = UnknownWordsTable.TABLE_NAME)
public class UnknowWords {

	private ArrayList<Word> wordJsons;
	@Column(name = UnknownWordsTable.COLUMN_WORDJSONS)
	private String wordJsons_json;

	public ArrayList<Word> getWordJsons() {
		return wordJsons;
	}

	public void setWordJsons(ArrayList<Word> wordJsons) {
		this.wordJsons = wordJsons;
	}

	public String getWordJsons_json() {
		return wordJsons_json;
	}

	public void setWordJsons_json(String wordJsons_json) {
		this.wordJsons_json = wordJsons_json;
	}

}
