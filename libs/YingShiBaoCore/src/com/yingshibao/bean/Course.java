package com.yingshibao.bean;

public class Course {

	private int id;
	private String name;
	private int pid;
	private String description;
	private int level;
	private int groupRootId;
	private int type;
	private double progress;
	private Course children;
	private Teacher teacher;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getGroupRootId() {
		return groupRootId;
	}

	public void setGroupRootId(int groupRootId) {
		this.groupRootId = groupRootId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public Course getChildren() {
		return children;
	}

	public void setChildren(Course children) {
		this.children = children;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", pid=" + pid
				+ ", description=" + description + ", level=" + level
				+ ", groupRootId=" + groupRootId + ", type=" + type
				+ ", progress=" + progress + ", children=" + children
				+ ", teacher=" + teacher + "]";
	}

}
