package com.yingshibao.cet4.bean;

public class ChuanKeCourseBean {
	@Override
	public String toString() {
		return "ChuanKeCourseBean [code=" + code + ", data=" + data + "]";
	}

	private String code;
	private Data data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
