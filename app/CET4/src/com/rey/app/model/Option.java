package com.rey.app.model;

import java.io.Serializable;

public class Option implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static String getOptDisplayText(int opt) {
		if (opt== 1) {
			return "A";
		} else if (opt == 2) {
			return "B";
		} else if (opt == 3) {
			return "C";
		} else if (opt == 4) {
			return "D";
		}
		return "";
	}
}
