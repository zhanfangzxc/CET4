package com.yingshibao.cet4.util;

import com.yingshibao.cet6.BuildConfig;

/**
 * 
 * @author malinkang
 * 
 */
public class LogUtil {

	protected static final String TAG = "YingShiBao";

	private LogUtil() {
	}

	public static void v(String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.v(TAG, buildMessage(msg));
	}

	public static void v(String msg, Throwable thr) {
		if (BuildConfig.DEBUG)
			android.util.Log.v(TAG, buildMessage(msg), thr);
	}

	public static void d(String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.d(TAG, buildMessage(msg));
	}

	public static void d(String msg, Throwable thr) {
		if (BuildConfig.DEBUG)
			android.util.Log.d(TAG, buildMessage(msg), thr);
	}

	public static void i(String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.i(TAG, buildMessage(msg));
	}

	public static void i(String msg, Throwable thr) {
		if (BuildConfig.DEBUG)
			android.util.Log.i(TAG, buildMessage(msg), thr);
	}

	public static void e(String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.e(TAG, buildMessage(msg));
	}

	public static void w(String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.w(TAG, buildMessage(msg));
	}

	public static void w(String msg, Throwable thr) {
		if (BuildConfig.DEBUG)
			android.util.Log.w(TAG, buildMessage(msg), thr);
	}

	public static void w(Throwable thr) {
		if (BuildConfig.DEBUG)
			android.util.Log.w(TAG, buildMessage(""), thr);
	}

	public static void e(String msg, Throwable thr) {
		if (BuildConfig.DEBUG)
			android.util.Log.e(TAG, buildMessage(msg), thr);
	}

	protected static String buildMessage(String msg) {
		StackTraceElement caller = new Throwable().fillInStackTrace()
				.getStackTrace()[2];
		return new StringBuilder().append(caller.getClassName()).append(".")
				.append(caller.getMethodName()).append("(): ").append(msg)
				.toString();
	}
}