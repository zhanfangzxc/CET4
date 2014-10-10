package com.yingshibao.cet4.util;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Environment;

public class DownloadRequest {
	private static int fileSize = 0;

	public static boolean downloadFile(String fileUrl, String dir,
			String fileName, FileDownLoadListener listener) {
		boolean issucceed = false;
		if (dir == null || dir.length() <= 0) {
			fileName = Environment.getExternalStorageDirectory()
					+ File.separator + fileName;
		} else {
			FileUtil.mkDir(dir);
			fileName = Environment.getExternalStorageDirectory() + dir
					+ File.separator + fileName;
		}
		URL url;
		try {
			url = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(30 * 1000);
			conn.connect();
			int resCode = conn.getResponseCode();
			if (resCode == 200) {
				fileSize = conn.getContentLength();
				int downSize = FileUtil.streamToFile(0,
						conn.getInputStream(), fileName, listener);
				if (downSize < fileSize) {
					conn.disconnect();
					conn = null;
					return downloadFile(fileUrl, fileName, listener, downSize);
				}
			}
			issucceed = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		fileSize = 0;
		return issucceed;
	}

	public static boolean downloadFile(String fileUrl, String fileName,
			FileDownLoadListener listener, int beginByte) {
		boolean issucceed = false;
		URL url;
		try {
			url = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (beginByte > 0) {// 断点续传
				conn.setRequestProperty("RANGE",
						"bytes=" + String.valueOf(beginByte) + "-");
			}
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(30 * 1000);
			conn.connect();
			int resCode = conn.getResponseCode();
			if (resCode == 200 || resCode == 206) {
				if (beginByte == 0) {
					fileSize = conn.getContentLength();
				}
				int downSize = FileUtil.streamToFile(beginByte,
						conn.getInputStream(), fileName, listener);
				if (downSize < fileSize) {
					conn.disconnect();
					conn = null;
					downloadFile(fileUrl, fileName, listener, downSize);
				}
			}
			issucceed = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		fileSize=0;
		return issucceed;
	}

	/**
	 * 把流转换成文件
	 * 
	 * @param inputstream
	 * @param filename
	 * @throws Exception
	 */
	public static int streamToFile(int seek, InputStream inputstream,
			String filename, FileDownLoadListener listener) {
		int downLoadSize = 0;
		try {
			File file = new File(filename);
			RandomAccessFile raf = new RandomAccessFile(file, "rw");

			raf.seek(seek);
			byte[] buf = new byte[1024];
			int length = 0;
			while ((length = inputstream.read(buf, 0, 1024)) != -1) {
				raf.write(buf, 0, length);
				if (listener != null) {
					downLoadSize = listener.getSize() + length;
					listener.onDownloadSize(downLoadSize);
				}
			}
			inputstream.close();
			raf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return downLoadSize;
	}
	
	public static int getFileSize() {
		return fileSize;
	}
}
