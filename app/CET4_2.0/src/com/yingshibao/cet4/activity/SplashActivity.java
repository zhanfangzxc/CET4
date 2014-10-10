package com.yingshibao.cet4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.yingshibao.api.RegisterAndLoginApi;
import com.yingshibao.cet4.R;

/**
 * 欢迎页
 * 
 * @author zhaoshan
 * 
 */
public class SplashActivity extends FragmentActivity {
	private RegisterAndLoginApi api = new RegisterAndLoginApi(this);
	private View view;
	private AlphaAnimation alphaAnimation;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		view = View.inflate(this, R.layout.activity_splash, null);

		setContentView(this.view);
		api.getColleage();

		initView();
		api.getColleage();

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
}
