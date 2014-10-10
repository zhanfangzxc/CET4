package com.yingshibao.bean;

import android.provider.BaseColumns;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.yingshibao.db.UserTable;

/**
 * 用户 bean
 * 
 * @author zhaoshan
 * 
 */
@Table(name = UserTable.TABLE_NAME, id = BaseColumns._ID)
public class User extends BaseBean {
	@Column(name = UserTable.COLUMN_USERNAME)
	private String UserName;// 用户名
	@Column(name = UserTable.COLUMN_PASSWORD)
	private String Password;// 密码
	@Column(name = UserTable.COLUMN_TOKEN)
	private String token;// 用户会话标识
	@Column(name = UserTable.COLUMN_NICKNAME)
	private String nickname;// 昵称
	@Column(name = UserTable.COLUMN_AVATAR)
	private String avatar;// 头像
	@Column(name = UserTable.COLUMN_COLLEGE)
	private String college_json;
	private College college;// 学院
	@Column(name = UserTable.COLUMN_GRADE)
	private Integer grade;// 年纪
	@Column(name = UserTable.COLUMN_PHONE)
	private String phone;// 电话

	public String getToken() {
		return token;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public String getCollege_json() {
		return college_json;
	}

	public void setCollege_json(String college_json) {
		this.college_json = college_json;
	}

	@Override
	public String toString() {
		return "User [UserName=" + UserName + ", Password=" + Password
				+ ", errorCode=" + errorCode + ", token=" + token
				+ ", nickname=" + nickname + ", avatar=" + avatar
				+ ", college=" + college + ", grade=" + grade + ", phone="
				+ phone + "]";
	}

}
