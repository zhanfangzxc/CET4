package com.yingshibao.util;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.yingshibao.AppContext;

/**
 * UI工具类
 * 
 * @author malinkang
 * 
 */

public class UIUtil {

	/**
	 * http://android-activities.blogspot.jp/2013/11/cleaner-view-casting-with-
	 * generics.html
	 * 
	 * @param activity
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final static <E extends View> E getView(Activity activity, int id) {
		try {
			return (E) activity.findViewById(id);
		} catch (ClassCastException ex) {
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	public final static <E extends View> E getView(View view, int id) {
		try {
			return (E) view.findViewById(id);
		} catch (ClassCastException ex) {
			throw ex;
		}
	}

	public final static void showLongToast(String str) {
		Toast.makeText(AppContext.getInstance(), str, Toast.LENGTH_SHORT)
				.show();
	}

	public final static void showShortToast(String str) {
		Toast.makeText(AppContext.getInstance(), str, Toast.LENGTH_SHORT)
				.show();
	}

}
