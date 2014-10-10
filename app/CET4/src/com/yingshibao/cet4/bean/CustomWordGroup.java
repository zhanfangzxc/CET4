package com.yingshibao.cet4.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomWordGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int iconResId;
	private ArrayList<WordGroup> wordGroups;

	public int getIconResId() {
		return iconResId;
	}

	public void setIconResId(int iconResId) {
		this.iconResId = iconResId;
	}

	public ArrayList<WordGroup> getWordGroups() {
		return wordGroups;
	}

	public void setWordGroups(ArrayList<WordGroup> wordGroups) {
		this.wordGroups = wordGroups;
	}

	@Override
	public String toString() {
		return "CustomWordGroup [iconResId=" + iconResId + ", wordGroups="
				+ wordGroups + "]";
	}
	
	

}
