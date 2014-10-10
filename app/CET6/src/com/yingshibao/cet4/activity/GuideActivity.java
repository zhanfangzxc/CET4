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

import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.fragment.GuideFragment;
import com.yingshibao.cet4.util.PreferenceUtil;
import com.yingshibao.cet4.util.UIUtil;
import com.yingshibao.cet6.R;

/**
 * 
 * @author malinkang 2014-2-22 下午2:37:16
 * 
 *         引导页
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
