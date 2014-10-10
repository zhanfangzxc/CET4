package com.yingshibao.cet4.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yingshibao.cet4.R;
import com.yingshibao.cet4.bean.CustomWordGroup;
import com.yingshibao.cet4.util.UIUtil;

public class WordGroupLevel1Adapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<CustomWordGroup> mWordGroups;

	public WordGroupLevel1Adapter(Context context,
			ArrayList<CustomWordGroup> wordGroups) {
		mWordGroups = wordGroups;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mWordGroups.size();
	}

	@Override
	public Object getItem(int position) {
		return mWordGroups.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CustomWordGroup wordGroup = mWordGroups.get(position);
		View view = null;
		if (convertView == null) {
			view = mInflater.inflate(R.layout.layout_course_level2_item, null,
					false);
		} else {
			view = convertView;
		}
		ImageView courseLevel2 = UIUtil.getView(view, R.id.tv_course_level2);
		// courseLevel2.setCompoundDrawablesWithIntrinsicBounds(0,
		// wordGroup.getIconResId(), 0, 0);
		courseLevel2.setImageResource(wordGroup.getIconResId());
		return view;
	}
}
