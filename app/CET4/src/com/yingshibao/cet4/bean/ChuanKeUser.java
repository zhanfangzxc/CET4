package com.yingshibao.cet4.bean;

import java.io.Serializable;

public class ChuanKeUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private UserData data;
	public String getCode() {
		return code;
	}
	public UserData getData() {
		return data;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setData(UserData data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ChuanKeUser [code=" + code + ", data=" + data + "]";
	}
	
	
}
