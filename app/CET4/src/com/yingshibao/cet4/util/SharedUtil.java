package com.yingshibao.cet4.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.yingshibao.cet4.R;

/**
 * SharedUtil Android文本存储工具类
 * 
 * @author Rey
 * 
 */
public class SharedUtil {
	private final static String TAG = "SharedUtil";

	public static void setInt(Context context, String key, int value) {
		SharedPreferences preferences = context.getSharedPreferences(context
				.getResources().getString(R.string.app_label), 0);
		Editor editor = preferences.edit();
		editor.putInt(key, value);
		if (!editor.commit()) {
			
		}
	}

	public static int getInt(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(context
				.getResources().getString(R.string.app_label), 0);
		return preferences.getInt(key, 0);
	}

	public static void setString(Context context, String key, String value) {
		SharedPreferences preferences = context.getSharedPreferences(context
				.getResources().getString(R.string.app_label), 0);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		if (!editor.commit()) {
			LogUtil.e(TAG);
		}
	}

	public static String getString(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(context
				.getResources().getString(R.string.app_label), 0);
		return preferences.getString(key, "");
	}

	public static void setBoolean(Context context, String key, Boolean value) {
		SharedPreferences preferences = context.getSharedPreferences(context
				.getResources().getString(R.string.app_label), 0);
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		if (!editor.commit()) {
			
		}
	}

	public static Boolean getBoolean(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(context
				.getResources().getString(R.string.app_label), 0);
		return preferences.getBoolean(key, false);
	}

	public static void setFloat(Context context, String key, Float value) {
		SharedPreferences preferences = context.getSharedPreferences(context
				.getResources().getString(R.string.app_label), 0);
		Editor editor = preferences.edit();
		editor.putFloat(key, value);
		if (!editor.commit()) {
			
		}
	}

	public static Float getFloat(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(context
				.getResources().getString(R.string.app_label), 0);
		return preferences.getFloat(key, 0);
	}

	public static void setLong(Context context, String key, Long value) {
		SharedPreferences preferences = context.getSharedPreferences(context
				.getResources().getString(R.string.app_label), 0);
		Editor editor = preferences.edit();
		editor.putLong(key, value);
		if (!editor.commit()) {
			
		}
	}

	public static Long getLong(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(context
				.getResources().getString(R.string.app_label), 0);
		return preferences.getLong(key, 0);
	}
}
