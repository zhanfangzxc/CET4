package com.yingshibao.cet4.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;

public class BaseApi {
	protected Context mContext;

	protected AsyncHttpClient client;

	protected Gson mGson = new GsonBuilder().enableComplexMapKeySerialization()
			.create();

	protected BaseApi(Context context) {
		this.mContext = context;
		client = new AsyncHttpClient();

	}
}
