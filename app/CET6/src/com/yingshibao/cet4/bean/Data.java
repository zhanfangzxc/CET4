package com.yingshibao.cet4.bean;

import java.util.ArrayList;

public class Data {

	public String getCount() {
		return Count;
	}

	public void setCount(String count) {
		Count = count;
	}

	private String Count;
	private ArrayList<DataBean> DataList;

	@Override
	public String toString() {
		return "Data [Count=" + Count + ", DataList=" + DataList + "]";
	}

	public ArrayList<DataBean> getDataList() {
		return DataList;
	}

	public void setDataList(ArrayList<DataBean> dataList) {
		DataList = dataList;
	}

}
