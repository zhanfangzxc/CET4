package com.yingshibao.api;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.yingshibao.AppContext;

public class BaseApi {

	protected Context context;
	protected AsyncHttpClient client;
	protected Gson mGson = AppContext.getGson();

	protected BaseApi(Context context) {
		this.context = context;
		client = new AsyncHttpClient();
	}
}
