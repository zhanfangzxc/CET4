package com.yingshibao.cet4.bean;

import java.io.Serializable;

public class Version implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int level;
	private int type;
	private String version;
	private String url;
	private String log;
	private String date;
	public int getId() {
		return id;
	}
	public int getLevel() {
		return level;
	}
	public int getType() {
		return type;
	}
	public String getVersion() {
		return version;
	}
	public String getUrl() {
		return url;
	}
	public String getLog() {
		return log;
	}
	public String getDate() {
		return date;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
