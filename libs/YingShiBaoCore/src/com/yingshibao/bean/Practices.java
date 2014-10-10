package com.yingshibao.bean;

import java.util.ArrayList;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.yingshibao.db.PracticesTable;

@Table(name = PracticesTable.TABLE_NAME)
public class Practices extends BaseBean {

	@Column(name = PracticesTable.COLUMN_PROGRESS)
	private double progress;
	private ArrayList<Practice> practiceJsons;
	@Column(name = PracticesTable.COLUMN_PRACTICEJSONS)
	private String practiceJsons_json;

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public ArrayList<Practice> getPracticeJsons() {
		return practiceJsons;
	}

	public void setPracticeJsons(ArrayList<Practice> practiceJsons) {
		this.practiceJsons = practiceJsons;
	}

	public String getPracticeJsons_json() {
		return practiceJsons_json;
	}

	public void setPracticeJsons_json(String practiceJsons_json) {
		this.practiceJsons_json = practiceJsons_json;
	}

	@Override
	public String toString() {
		return "Practices [progress=" + progress + ", practiceJsons="
				+ practiceJsons + ", practiceJsons_json=" + practiceJsons_json
				+ "]";
	}

}
