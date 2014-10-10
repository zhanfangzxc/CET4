package com.yingshibao.cet4.bean;

import java.io.Serializable;

public class Option implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int option;
	private String content;
	
	

	@Override
	public String toString() {
		return "Option [option=" + option + ", content=" + content + "]";
	}

	public int getOption() {
		return option;
	}

	public void setId(int option) {
		this.option = option;
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
