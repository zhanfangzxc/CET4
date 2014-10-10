package com.yingshibao.cet4.service;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.yingshibao.cet4.api.RegisterAndLoginApi;

public class AddPhoneNumberService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String token = intent.getStringExtra("token");
		String phone = intent.getStringExtra("phone");
		String grade = intent.getStringExtra("grade");
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("token", token);
		map.put("phone", phone);
		map.put("grade", grade);
		new RegisterAndLoginApi(this).setPhoneNumber(map);
		return super.onStartCommand(intent, flags, startId);
	}

}
