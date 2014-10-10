package com.yingshibao.bean;

import java.util.ArrayList;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.yingshibao.db.WordsTable;

@Table(name = WordsTable.TABLE_NAME)
public class Words extends BaseBean {

	@Column(name = WordsTable.COLUMN_PROGRESS)
	private double progress;
	private ArrayList<Word> wordJsons;
	@Column(name = WordsTable.COLUMN_WORDSJSON)
	private String wordJsons_json;

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

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

	@Override
	public String toString() {
		return "Words [progress=" + progress + ", wordJsons=" + wordJsons
				+ ", wordJsons_json=" + wordJsons_json + "]";
	}

}
