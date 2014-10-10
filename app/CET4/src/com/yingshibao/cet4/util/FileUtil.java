package com.yingshibao.cet4.util;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * FileUtil 文件操作工具类
 * 
 * @author Rey
 * 
 */
public class FileUtil {

	public static boolean existSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		return false;
	}

	public static String getSDPath() {
		if (existSDCard()) {
			return android.os.Environment.getExternalStorageDirectory()
					.toString();
		}
		return null;
	}

	public static boolean mkDir(String dirPath) {
		if (existSDCard()) {
			File dirFile = new File(getSDPath() + dirPath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
				return true;
			}
		}
		return false;
	}

	public static void streamToFile(InputStream inputstream, String filename)
			throws Exception {
		File file = new File(filename);
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		byte[] buf = new byte[1024];
		int length = 0;
		while ((length = inputstream.read(buf, 0, 1024)) != -1) {
			raf.write(buf, 0, length);
		}
		inputstream.close();
		raf.close();
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
}
