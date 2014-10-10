package com.yingshibao.cet4.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class PracticeInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String progress;

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private int errorCode;

	private ArrayList<Practice> practiceJsons;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public ArrayList<Practice> getPracticeJsons() {
		return practiceJsons;
	}

	public void setPracticeJsons(ArrayList<Practice> practiceJsons) {
		this.practiceJsons = practiceJsons;
	}

	@Override
	public String toString() {
		return "PracticeInfo [errorCode=" + errorCode + ", practiceJsons="
				+ practiceJsons + "]";
	}
}
