package com.rey.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * cid 课程ID
	 */
	private int cid;

	/**
	 * 课程名称
	 */
	private String courseName;

	/**
	 * 课程描述
	 */
	private String description;

	/**
	 * 视频地址
	 */
	private String videoPath;

	/**
	 * 父课程
	 */
	private Course parent;
	/**
	 * cpid 父课程ID
	 */
	private int cpid;

	/**
	 * 本课程下直接所属子课程列表
	 */
	private List<Course> children = new ArrayList<Course>();

	/**
	 * 当前进度
	 */
	private String progress = "0";

	/**
	 * 课程级别
	 */
	private int level = 0;

	/**
	 * 课程类型
	 */
	private int type = 0;
	/**
	 * 教师
	 */

	private Teacher teacher;

	private boolean isExpanded = false;// 是否处于展开状态

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public Course getParent() {
		return parent;
	}

	public void setParent(Course parent) {
		this.parent = parent;
	}

	public int getCpid() {
		return cpid;
	}

	public void setCpid(int cpid) {
		this.cpid = cpid;
	}

	public List<Course> getChildren() {
		return children;
	}

	public void setChildren(List<Course> children) {
		this.children = children;
		if (children != null && children.size() > 0) {
			for (int i = 0; i < children.size(); i++) {
				Course c = children.get(i);
				c.setParent(this);
				c.setCpid(this.cid);
				c.setLevel(this.level + 1);
				c.setChildren(c.getChildren());
			}
		}
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 增加子课程
	 * 
	 * @param organ
	 */
	public void addCourse(Course obj) {
		if (!this.children.contains(obj)) {
			this.children.add(obj);
			obj.setLevel(this.level + 1);
			obj.setParent(this);
		}
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public boolean isRoot() {
		return parent == null ? true : false;
	}

	public boolean isLeaf() {
		return children.size() < 1 ? true : false;
	}

	/**
	 * 删除子课程
	 * 
	 * @param organ
	 */
	public void removeCourse(Course obj) {
		if (this.children.contains(obj)) {
			obj.setParent(null);
			this.children.remove(obj);
		}
	}

	public void clear() {
		children.clear();
	}

	public void remove(int location) {
		children.remove(location);
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	public Course getRoot() {
		if (this.level == 1) {
			return this;
		} else {
			return this.getParent().getRoot();
		}
	}

	public Course clone() throws CloneNotSupportedException {
		Course smodel = new Course();
		smodel.setCid(cid);
		smodel.setCpid(cpid);
		smodel.setParent(parent);
		smodel.setCourseName(courseName);
		smodel.setDescription(description);
		smodel.setVideoPath(videoPath);
		smodel.setLevel(level);
		smodel.setTeacher(teacher);
		smodel.setType(type);
		smodel.setExpanded(isExpanded);
		smodel.setProgress(progress);
		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				Course cCourse = children.get(i).clone();
				smodel.addCourse(cCourse);
			}
		}
		return smodel;
	}
}
