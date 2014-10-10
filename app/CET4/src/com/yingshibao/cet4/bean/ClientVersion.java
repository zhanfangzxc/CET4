package com.yingshibao.cet4.bean;


/**
 * 
 * 客户端版本返回信息
 * 
 * @author zhaoshan
 * 
 */
public class ClientVersion {

	// 错误码
	private int errorCode;
	// 版本信息
	private Version latest;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public Version getLatest() {
		return latest;
	}

	public void setLatest(Version latest) {
		this.latest = latest;
	}

	
}
