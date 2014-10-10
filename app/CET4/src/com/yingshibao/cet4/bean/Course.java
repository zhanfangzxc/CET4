package com.yingshibao.cet4.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;// 课程编号
	private String name;// 课程名称
	private String pid;// 父课程编号
	private String description;// 课程描述
	private String level;// 课程级别
	private String groupRootId;
	private String type;// 课程类型
	private String progress;// 课程进度
	private String videoUrl;// 课程视频简介
	private ArrayList<Course> children;// 子课程
	private Teacher teacher;// 教师
	private ArrayList<Practice> practices;// 练习
	private ArrayList<DownloadBean> downloadInfo;
	private int status;

	public void setStatus(int status) {
		this.status = status;
	}

	public ArrayList<DownloadBean> getDownloadInfo() {
		return downloadInfo;
	}

	public void setDownloadInfo(ArrayList<DownloadBean> downloadInfo) {
		this.downloadInfo = downloadInfo;
	}

	public ArrayList<Practice> getPractices() {
		return practices;
	}

	public void setPractices(ArrayList<Practice> practice) {
		this.practices = practice;
	}

	private int iconResId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getGroupRootId() {
		return groupRootId;
	}

	public void setGroupRootId(String groupRootId) {
		this.groupRootId = groupRootId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public ArrayList<Course> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Course> children) {
		this.children = children;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public int getIconResId() {
		return iconResId;
	}

	public void setIconResId(int iconResId) {
		this.iconResId = iconResId;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", pid=" + pid
				+ ", description=" + description + ", level=" + level
				+ ", type=" + type + ", progress=" + progress + ", videoUrl="
				+ videoUrl + ", teacher=" + teacher + ", children=" + children
				+ "]";
	}

}
