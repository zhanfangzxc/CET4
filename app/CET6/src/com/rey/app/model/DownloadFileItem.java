package com.rey.app.model;

import java.io.Serializable;

public class DownloadFileItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String fileUrl;

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

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

}
