package com.yingshibao.cet4.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.yingshibao.cet4.R;
import com.yingshibao.cet4.fragment.GuideFragment;
import com.yingshibao.util.PreferenceUtil;
import com.yingshibao.util.UIUtil;

/**
 * 引导页
 * 
 * @author zhaoshan
 * 
 */
public class GuideActivity extends FragmentActivity implements OnClickListener {

	private int[] imageIds = { R.drawable.guide_1, R.drawable.guide_2,
			R.drawable.guide_3, R.drawable.guide_4 };
	private Context mContext;
	private ViewPager mViewPager;
	private GuidePagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		if (!PreferenceUtil.isFirstStart(mContext)) {
			startActivity(new Intent(this, SplashActivity.class));
			finish();
		}
		PreferenceUtil.editFirstStart(mContext);
		setContentView(R.layout.activity_guide);
		mViewPager = UIUtil.getView(this, R.id.viewpager);
		mPagerAdapter = new GuidePagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_start:
			startActivity(new Intent(this, SplashActivity.class));
			finish();
			break;

		default:
			break;
		}
	}

	private class GuidePagerAdapter extends FragmentPagerAdapter {

		public GuidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			GuideFragment fragment = null;
			if (position == 3) {
				fragment = GuideFragment.newInstance(imageIds[position], true);
			} else {
				fragment = GuideFragment.newInstance(imageIds[position], false);
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return imageIds.length;
		}

	}

}
