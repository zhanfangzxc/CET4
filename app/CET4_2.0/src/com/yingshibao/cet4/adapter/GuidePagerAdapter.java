package com.yingshibao.cet4.adapter;

import com.yingshibao.cet4.fragment.GuideFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class GuidePagerAdapter extends FragmentPagerAdapter {

	private int[] imageIds;

	public GuidePagerAdapter(FragmentManager fm, int[] imageIds) {
		super(fm);
		this.imageIds = imageIds;
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
