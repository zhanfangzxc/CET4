package com.yingshibao.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 版本信息工具类
 * 
 * @author zhaoshan
 * 
 */
public class VersionUtils {

	private Context context;
	public VersionUtils(Context context) {
		this.context = context;
	}
	/**
	 * 获取versionName
	 * 
	 * @param packName
	 *            包名
	 */
	public static String getVersionName(Context context,String packName) {
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(packName, 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "版本号未知";
		}
	}

	/**
	 * 获取versionCode
	 * 
	 * @param packName
	 *            包名
	 */
	public static int getVersionCode(Context context,String packName) {
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(packName, 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
