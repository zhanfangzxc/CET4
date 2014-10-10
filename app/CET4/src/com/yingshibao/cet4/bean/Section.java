package com.yingshibao.cet4.bean;

import java.io.Serializable;

public class Section implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Brief;
	private String CID;
	private String ClassIndex;
	private String Courseware;
	private String PrelectStartTime;
	private String PrelectStatus;
	private String PrelectTimeLength;
	private String SID;
	private String StudyType;
	private String TeacherName;
	private String TeacherUID;
	private String TrailFlag;
	private String videoID;
	private String VideoStatus;
	private String videoTimeLength;

	public String getBrief() {
		return Brief;
	}

	@Override
	public String toString() {
		return "Section [Brief=" + Brief + ", CID=" + CID + ", ClassIndex="
				+ ClassIndex + ", Courseware=" + Courseware
				+ ", PrelectStartTime=" + PrelectStartTime + ", PrelectStatus="
				+ PrelectStatus + ", PrelectTimeLength=" + PrelectTimeLength
				+ ", SID=" + SID + ", StudyType=" + StudyType
				+ ", TeacherName=" + TeacherName + ", TeacherUID=" + TeacherUID
				+ ", TrailFlag=" + TrailFlag + ", videoID=" + videoID
				+ ", VideoStatus=" + VideoStatus + ", videoTimeLength="
				+ videoTimeLength + "]";
	}

	public void setBrief(String brief) {
		Brief = brief;
	}

	public String getCID() {
		return CID;
	}

	public void setCID(String cID) {
		CID = cID;
	}

	public String getClassIndex() {
		return ClassIndex;
	}

	public void setClassIndex(String classIndex) {
		ClassIndex = classIndex;
	}

	public String getCourseware() {
		return Courseware;
	}

	public void setCourseware(String courseware) {
		Courseware = courseware;
	}

	public String getPrelectStartTime() {
		return PrelectStartTime;
	}

	public void setPrelectStartTime(String prelectStartTime) {
		PrelectStartTime = prelectStartTime;
	}

	public String getPrelectStatus() {
		return PrelectStatus;
	}

	public void setPrelectStatus(String prelectStatus) {
		PrelectStatus = prelectStatus;
	}

	public String getPrelectTimeLength() {
		return PrelectTimeLength;
	}

	public void setPrelectTimeLength(String prelectTimeLength) {
		PrelectTimeLength = prelectTimeLength;
	}

	public String getSID() {
		return SID;
	}

	public void setSID(String sID) {
		SID = sID;
	}

	public String getStudyType() {
		return StudyType;
	}

	public void setStudyType(String studyType) {
		StudyType = studyType;
	}

	public String getTeacherName() {
		return TeacherName;
	}

	public void setTeacherName(String teacherName) {
		TeacherName = teacherName;
	}

	public String getTeacherUID() {
		return TeacherUID;
	}

	public void setTeacherUID(String teacherUID) {
		TeacherUID = teacherUID;
	}

	public String getTrailFlag() {
		return TrailFlag;
	}

	public void setTrailFlag(String trailFlag) {
		TrailFlag = trailFlag;
	}

	public String getVideoID() {
		return videoID;
	}

	public void setVideoID(String videoID) {
		this.videoID = videoID;
	}

	public String getVideoStatus() {
		return VideoStatus;
	}

	public void setVideoStatus(String videoStatus) {
		VideoStatus = videoStatus;
	}

	public String getVideoTimeLength() {
		return videoTimeLength;
	}

	public void setVideoTimeLength(String videoTimeLength) {
		this.videoTimeLength = videoTimeLength;
	}
}
