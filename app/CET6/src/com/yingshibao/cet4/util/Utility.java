package com.yingshibao.cet4.util;

import java.security.SecureRandom;
import java.util.Date;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Utility {
	private static final int MAX_NONCE = 0 + 10;

	private static final String LABEL_App_sign = "api_sign";
	private static final String LABEL_TIME = "timestamp";
	private static final String LABEL_NONCE = "nonce";
	private static final String LABEL_UID = "uid";

	private static final SecureRandom sRandom = new SecureRandom();

	private static char sHexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static String getNonce() {
		byte[] bytes = new byte[MAX_NONCE / 2];
		sRandom.nextBytes(bytes);
		return hexString(bytes);
	}

	public static String hexString(byte[] source) {
		if (source == null || source.length <= 0) {
			return "";
		}

		final int size = source.length;
		final char str[] = new char[size * 2];
		int index = 0;
		byte b;
		for (int i = 0; i < size; i++) {
			b = source[i];
			str[index++] = sHexDigits[b >>> 4 & 0xf];
			str[index++] = sHexDigits[b & 0xf];
		}
		return new String(str);
	}

	private static long getTimestamp() {
		Date date = new Date();
		long i = date.getTime();
		return i;
	}

	private static String getAPIsig(String key, long timestamp, String nonce,
			String uid) {
		String result = null;
		StringBuilder builder = new StringBuilder();
		synchronized (builder) {
			builder.append(key);
			builder.append(timestamp);
			builder.append(nonce);
			builder.append(uid);
			// result = MD5Util.encode(builder.toString());
			builder.delete(0, builder.length());
		}
		return result;
	}

	public static String getParams(String key) {
		String result = "";
		try {
			String[] temp = key.split(":");
			long timestamp = getTimestamp();
			String nonce = getNonce();
			String api_sign = getAPIsig(key, timestamp, nonce, temp[1]);

			StringBuilder builder = new StringBuilder();

			synchronized (result) {
				builder.append(String.format("&" + LABEL_UID + "=%s", temp[1]));
				builder.append(String.format("&" + LABEL_NONCE + "=%s", nonce));
				builder.append(String.format("&" + LABEL_TIME + "=%s",
						timestamp));
				builder.append(String.format("&" + LABEL_App_sign + "=%s",
						api_sign));
				result = builder.toString();
				builder.delete(0, builder.length());
			}
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	public static String getScreenParams(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return "&screen="
				+ (dm.heightPixels > dm.widthPixels ? dm.widthPixels + "*"
						+ dm.heightPixels : dm.heightPixels + "*"
						+ dm.widthPixels);
	}

	public static void setListViewHeight(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public static void setExpandableListViewHeight(ExpandableListView listView) {
		ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getGroupCount(); i++) {
			for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
				View listItem = listAdapter.getGroupView(i, true, null,
						listView);
				// getView(i, null, listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + ((listAdapter.getGroupCount()) * 100);
		listView.setLayoutParams(params);
	}

	public static void setExpandableListViewHeight(ExpandableListView listView,
			int position) {
		ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getGroupCount(); i++) {
			if (position < 0) {
				View listItem = listAdapter.getGroupView(i, true, null,
						listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			} else {
				for (int j = 0; j < listAdapter.getChildrenCount(position); j++) {
					View listItem = listAdapter.getGroupView(i, true, null,
							listView);
					listItem.measure(0, 0);
					totalHeight += listItem.getMeasuredHeight();
				}
			}
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;
		listView.setLayoutParams(params);
	}
}
