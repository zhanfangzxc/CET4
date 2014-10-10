package com.yingshibao.bean;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.yingshibao.db.CourseProgressTable;

@Table(name = CourseProgressTable.TABLE_NAME)
public class CourseProgress extends BaseBean {

	@Column(name = CourseProgressTable.COLUMN_PROGRESS)
	private double progress;

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

}
