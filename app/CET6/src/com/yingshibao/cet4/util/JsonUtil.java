package com.yingshibao.cet4.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.yingshibao.cet4.AppException;

/**
 * JsonUtil json工具类
 * @author Rey
 *
 */
public class JsonUtil {
	
	public static String createJsonString (String key,Object value) throws AppException {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(key, value);
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return jsonObject.toString();
	}
	
	public static String createJsonString (String[] key,Object[] value) throws AppException {
		JSONObject jsonObject = new JSONObject();
		if (key.length != value.length) {
			return null;
		}
		for (int i = 0; i < key.length; i++) {
			try {
				jsonObject.put(key[i], value[i]);
			} catch (JSONException e) {
				throw AppException.json(e);
			}
		}
		return jsonObject.toString();
	}
}
