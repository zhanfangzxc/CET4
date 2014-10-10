package com.yingshibao;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yingshibao.bean.User;
import com.yingshibao.bean.UserAdapter;

public class AppContext extends Application {

	private static AppContext instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		ActiveAndroid.initialize(this);
	}

	public static AppContext getInstance() {
		return instance;
	}

	public static void setInstance(AppContext instance) {
		AppContext.instance = instance;
	}

	private User userInfo;

	public User getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		ActiveAndroid.dispose();
	}

	public static Gson getGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.registerTypeAdapter(User.class,
				new UserAdapter()).create();
		return gson;
	}
}
