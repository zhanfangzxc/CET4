package com.yingshibao.cet4.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.R;

/**
 * 传课网支付界面
 * 
 * @author zhaoshan
 * 
 */
public class PayActivity extends BaseActivity {

	private WebView webView;
	private String html;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		html = getIntent().getStringExtra("html");
		initView();
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void initView() {
		actionBarTitle.setText(getIntent().getStringExtra("CourseName"));
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSaveFormData(false);
		settings.setSavePassword(false);
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
		webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
