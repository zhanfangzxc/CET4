package com.yingshibao.cet4.bean;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "College [id=" + id + ", name=" + name + ", abbr=" + abbr + "]";
	}

}
