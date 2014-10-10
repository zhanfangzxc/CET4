package com.yingshibao.cet4.service;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.yingshibao.cet4.api.RegisterAndLoginApi;
import com.yingshibao.cet4.bean.User;
import com.yingshibao.cet4.constants.Constants;
import com.yingshibao.db.dao.UserDao;

/**
 * 自动登陆的Service
 * 
 * @author malinkang
 * 
 */
public class AutoLoginService extends Service {
	private RegisterAndLoginApi api;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		api = new RegisterAndLoginApi(this);
		new GetUserFromDBTask().execute();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	private class GetUserFromDBTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			UserDao userDao = new UserDao(AutoLoginService.this);
			User user = userDao.getUser();
			if (user != null) {

				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("requestCourseLevel", Constants.LEVEL);
				map.put("username", user.getUsername());
				map.put("password", user.getPassword());
				api.login(map, true,
						new ProgressDialog(getApplicationContext()));
			}
			return null;
		}

	}

}
