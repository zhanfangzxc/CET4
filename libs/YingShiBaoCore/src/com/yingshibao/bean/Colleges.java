package com.yingshibao.bean;

import java.util.ArrayList;

import android.provider.BaseColumns;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.yingshibao.db.ColleageTable;
import com.yingshibao.db.UserTable;

@Table(name = ColleageTable.TABLE_NAME, id = BaseColumns._ID)
public class Colleges extends BaseBean {

	@Column(name = ColleageTable.COLUMN_COLLEAGES)
	private String colleges_json;
	private ArrayList<College> collegeList;

	public ArrayList<College> getCollegeList() {
		return collegeList;
	}

	public void setCollegeList(ArrayList<College> collegeList) {
		this.collegeList = collegeList;
	}

	public String getColleges_json() {
		return colleges_json;
	}

	public void setColleges_json(String colleges_json) {
		this.colleges_json = colleges_json;
	}

}
