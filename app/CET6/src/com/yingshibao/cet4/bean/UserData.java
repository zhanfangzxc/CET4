package com.yingshibao.cet4.bean;

import java.io.Serializable;

public class UserData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uid;
	private String username;
	private String password;
	private String nickname;
	public String getUid() {
		return uid;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Override
	public String toString() {
		return "UserData [uid=" + uid + ", username=" + username
				+ ", password=" + password + ", nickname=" + nickname + "]";
	}
	
	
}
