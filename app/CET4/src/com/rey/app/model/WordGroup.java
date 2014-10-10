package com.rey.app.model;

import java.io.Serializable;

public class WordGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private int current;

	private int total;
	
	private int currentNow;
	
	private String name;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCurrentNow() {
		return currentNow;
	}

	public void setCurrentNow(int currentNow) {
		this.currentNow = currentNow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
