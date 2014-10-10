package com.yingshibao.cet4.bean;

public class DownloadBean {
	private String url;// 路径

	private String size;// 大小
	private long downloadId;// 下载Id

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getDownloadId() {
		return downloadId;
	}

	public void setDownloadId(long downloadId) {
		this.downloadId = downloadId;
	}

	private int status;// 状态

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
