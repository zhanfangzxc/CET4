package com.yingshibao.cet4.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TabPageIndicator;
import com.yingshibao.cet4.R;
import com.yingshibao.cet4.fragment.DownloadFinishFragment;
import com.yingshibao.cet4.fragment.DownloadUnFinishFragment;

public class DownloadManageActivity extends BaseActivity {
	private static final String[] CONTENT = new String[] { "下载中", "下载完成" };
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_tabs);
		FragmentPagerAdapter adapter = new MyPagerAdapter(
				getSupportFragmentManager());
		fragments.add(new DownloadUnFinishFragment());
		fragments.add(new DownloadFinishFragment());
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		actionBarTitle.setText("下载管理");
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
}
