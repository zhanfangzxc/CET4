/**
 * Title：Teacher.java
 * Copyright:云电同方科技有限公司
 * Company：云电同方科技有限公司
 */
package com.yingshibao.cet4.bean;

import java.io.Serializable;

/**
 * 
 * @author malinkang
 * 
 */
public class Teacher implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;// 教师编号

	private String name;// 教师名称

	private String info;// 教师简介

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + ", info=" + info + "]";
	}

}
