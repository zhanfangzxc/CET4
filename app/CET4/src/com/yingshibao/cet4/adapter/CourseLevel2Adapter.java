package com.yingshibao.cet4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yingshibao.cet4.R;
import com.yingshibao.cet4.bean.Course;
import com.yingshibao.cet4.bean.TreeCourseInfo;
import com.yingshibao.cet4.util.UIUtil;

public class CourseLevel2Adapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private TreeCourseInfo mTreeCourseInfo;

	public CourseLevel2Adapter(Context context, TreeCourseInfo treeCourseInfo) {
		mContext = context;
		mTreeCourseInfo = treeCourseInfo;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mTreeCourseInfo.getCourseJsons().size();
	}

	@Override
	public Object getItem(int position) {
		return mTreeCourseInfo.getCourseJsons().get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Course course = mTreeCourseInfo.getCourseJsons().get(position);
		View view = null;
		if (convertView == null) {
			view = mInflater.inflate(R.layout.layout_course_level2_item, null,
					false);
		} else {
			view = convertView;
		}
		ImageView courseLevel2 = UIUtil.getView(view, R.id.tv_course_level2);
		courseLevel2.setImageResource(course.getIconResId());
		return view;
	}
}
