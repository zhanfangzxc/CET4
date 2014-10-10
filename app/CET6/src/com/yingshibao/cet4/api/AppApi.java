package com.yingshibao.cet4.api;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yingshibao.cet4.constants.Constants;

/**
 * 版本先相关请求
 * 
 * @author zhaoshan
 * 
 */
public class AppApi extends BaseApi {

	public AppApi(Context context) {
		super(context);
	}

	/**
	 * 
	 * 获取最新版本信息
	 * 
	 * @param map
	 */
	public void getLatestversion(RequestParams params,
			RequestCallBack<String> mRequestCallBack) {

		HttpUtils utils = new HttpUtils();
		utils.send(HttpRequest.HttpMethod.POST, Constants.VERSION_URL, params,
				mRequestCallBack);

	}

}
