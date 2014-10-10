package com.yingshibao.cet4.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络连接的工具类
 * 
 * @author zhaoshan
 * 
 */
public class NetWorkUtil {

	@SuppressWarnings("unused")
	public static boolean isNetConnected(Context mContext) {
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo[] infos = cm.getAllNetworkInfo();
			if (infos != null) {
				for (NetworkInfo ni : infos) {
					if (ni.isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
