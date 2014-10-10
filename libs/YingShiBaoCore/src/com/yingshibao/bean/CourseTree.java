package com.yingshibao.bean;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.yingshibao.db.CourseTreeTable;

@Table(name = CourseTreeTable.TABLE_NAME)
public class CourseTree extends BaseBean {

	private ArrayList<Course> courseJsons;
	@Column(name = CourseTreeTable.COLUMN_COURSEJSONS)
	private String courseJsons_json;

	public List<Course> getCourseJsons() {
		return courseJsons;
	}

	public void setCourseJsons(ArrayList<Course> courseJsons) {
		this.courseJsons = courseJsons;
	}

}
