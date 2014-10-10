package com.yingshibao.cet4.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.R;
import com.yingshibao.cet4.api.RegisterAndLoginApi;
import com.yingshibao.cet4.util.LogUtil;

/**
 * AppStart 应用程序启动类：显示欢迎界面并跳转到主界面
 * 
 * @author
 * 
 */
public class SplashActivity extends FragmentActivity {

	private View view;
	private AlphaAnimation alphaAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		new RegisterAndLoginApi(this).getColleage();
		super.onCreate(savedInstanceState);
		view = View.inflate(SplashActivity.this, R.layout.start_activity, null);
		this.setContentView(this.view);
		initView();
		String info = getDeviceInfo(this);
		LogUtil.e(info);
	}

	private void initView() {
		this.alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
		this.alphaAnimation.setDuration(2000);
		this.view.startAnimation(this.alphaAnimation);
		this.alphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity(new Intent(SplashActivity.this,
						LoginActivity.class));
				finish();
			}
		});

	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			String device_id = tm.getDeviceId();

			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);

			String mac = wifi.getConnectionInfo().getMacAddress();
			json.put("mac", mac);

			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}

			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
			}

			json.put("device_id", device_id);

			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
