package com.yingshibao.cet4.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.yingshibao.cet6.R;

public class ArticleContentActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_content);
		String url = getIntent().getStringExtra("content");
		WebView mWebView = (WebView) findViewById(R.id.webview);
		mWebView.setVerticalScrollBarEnabled(false); // 取消Vertical ScrollBar显示
		mWebView.setHorizontalScrollBarEnabled(false);// 取消Horizontal //
		actionBarTitle.setText(getIntent().getStringExtra("title")); // ScrollBar显示
		mWebView.loadUrl(url);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

	}

}
