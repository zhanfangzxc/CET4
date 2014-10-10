package com.yingshibao.bean;

/**
 * 学院bean
 * 
 * @author zhaoshan
 * 
 */
public class College {

	private Integer id;// 学员编号
	private String name;// 学院名字
	private String abbr;// 学院首字母缩写

	public Integer getId() {
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

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	@Override
	public String toString() {
		return "Colleage [id=" + id + ", name=" + name + ", abbr=" + abbr + "]";
	}

}
