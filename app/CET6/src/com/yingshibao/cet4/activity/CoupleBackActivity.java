package com.yingshibao.cet4.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.fragment.CoupleBackFragment;
import com.yingshibao.cet6.R;

public class CoupleBackActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupleback);
		getSupportActionBar().hide();
		int courseId = 0;
		Bundle bundle = new Bundle();
		bundle.putSerializable("courseId", courseId);
		CoupleBackFragment vFragment = CoupleBackFragment.instance(bundle);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.coupleback, vFragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SplashScreen"); // 统计页面
		MobclickAgent.onResume(this); // 统计时长
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SplashScreen"); // 保证 onPageEnd 在onPause
													// 之前调用,因为 onPause 中会保存信息
		MobclickAgent.onPause(this);
	}
}
