package com.yingshibao.cet4.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet6.R;

public class BaseActivity extends ActionBarActivity implements OnClickListener {
	protected LayoutParams params;
	protected TextView actionBarTitle;
	protected ActionBar actionBar;
	protected Button downloadItem;
	protected ImageView back;
	protected Button unknown_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		actionBar = getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		params = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		View customeActionBar = View.inflate(this,
				R.layout.layout_custome_actionbar, null);

		actionBarTitle = (TextView) customeActionBar
				.findViewById(R.id.tv_actionbar_title);
		downloadItem = (Button) customeActionBar
				.findViewById(R.id.btn_download);
		unknown_btn = (Button) customeActionBar.findViewById(R.id.unknown_btn);
		back = (ImageView) customeActionBar.findViewById(R.id.back);
		back.setOnClickListener(this);
		getSupportActionBar().setCustomView(customeActionBar, params);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SplashScreen"); // 统计页面
		MobclickAgent.onResume(this); // 统计时长
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SplashScreen"); // 保证 onPageEnd 在onPause //
													// 之前调用,因为 onPause 中会保存信息
		MobclickAgent.onPause(this);
	}
}
