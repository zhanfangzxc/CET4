package com.yingshibao.cet4.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.yingshibao.cet4.R;

public class BaseActivity extends ActionBarActivity {
	protected LayoutParams params;

	@InjectView(R.id.tv_actionbar_title)
	TextView actionBarTitle;// 提示
	protected ActionBar actionBar;
	@InjectView(R.id.btn_download)
	Button downloadItem;// 下载announced
	@InjectView(R.id.back)
	ImageView back;// 返回按钮
	@InjectView(R.id.unknown_btn)
	Button unknown_btn;// 生词本

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
		ButterKnife.inject(customeActionBar);
		getSupportActionBar().setCustomView(customeActionBar, params);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return false;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@OnClick(R.id.back)
	public void back() {
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.reset(this);
	}
}
