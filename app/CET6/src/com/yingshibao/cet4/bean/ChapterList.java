package com.yingshibao.cet4.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ChapterList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Section> SectionList;

	public ArrayList<Section> getSectionList() {
		return SectionList;
	}

	public void setSectionList(ArrayList<Section> sectionList) {
		SectionList = sectionList;
	}
}
