package com.yingshibao.cet4.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.umeng.analytics.MobclickAgent;
import com.viewpagerindicator.TabPageIndicator;
import com.yingshibao.cet4.fragment.DownloadFinishFragment;
import com.yingshibao.cet4.fragment.DownloadUnFinishFragment;
import com.yingshibao.cet6.R;

public class DownloadManageActivity extends BaseActivity {
	private static final String[] CONTENT = new String[] { "下载中", "下载完成" };
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBarTitle.setText("下载管理");
		setContentView(R.layout.simple_tabs);
		FragmentPagerAdapter adapter = new MyPagerAdapter(
				getSupportFragmentManager());
		fragments.add(new DownloadUnFinishFragment());
		fragments.add(new DownloadFinishFragment());
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);

	}

	class MyPagerAdapter extends FragmentPagerAdapter {
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length].toUpperCase();
		}

		@Override
		public int getCount() {
			return CONTENT.length;
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
