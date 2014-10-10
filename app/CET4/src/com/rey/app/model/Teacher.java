/**
 * Title：Teacher.java
 * Copyright:云电同方科技有限公司
 * Company：云电同方科技有限公司
 */
package com.rey.app.model;

import java.io.Serializable;

/**
 * @author hwm
 * 2014-1-17
 * 1.0
 */
public class Teacher implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String name;
	
	private String info;

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

}
