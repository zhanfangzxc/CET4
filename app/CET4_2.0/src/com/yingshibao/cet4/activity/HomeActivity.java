package com.yingshibao.cet4.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.yingshibao.cet4.R;
import android.os.Bundle;
import android.widget.Button;

public class HomeActivity extends BaseActivity {

	@InjectView(R.id.share_btn)
	Button share_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ButterKnife.inject(this);
	}
	
	@OnClick(R.id.share_btn)
	public void share(){
		
	}
	
	
	
}
