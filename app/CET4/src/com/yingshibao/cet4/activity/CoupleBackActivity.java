package com.yingshibao.cet4.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.yingshibao.cet4.R;
import com.yingshibao.cet4.fragment.CoupleBackFragment;

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
}
