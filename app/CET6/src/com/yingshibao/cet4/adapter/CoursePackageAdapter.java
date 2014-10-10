package com.yingshibao.cet4.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.activity.PracticeDetialActivity;
import com.yingshibao.cet4.activity.VedioPlayActivity;
import com.yingshibao.cet4.bean.Course;
import com.yingshibao.cet4.bean.TreeCourseInfo;
import com.yingshibao.cet4.constants.Constants;
import com.yingshibao.cet4.util.MD5Util;
import com.yingshibao.cet6.R;

/**
 * 
 * 课程包界面适配器
 * 
 * @author zhaoshan
 * 
 */
public class CoursePackageAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	private ArrayList<Course> mCourses;
	private ImageLoader mImageLoader;
	private String mTitle;

	public CoursePackageAdapter(Context context, ArrayList<Course> courses,
			TreeCourseInfo treeCourseInfo, String title) {
		super();
		this.context = context;
		mCourses = courses;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mImageLoader = ImageLoader.getInstance();
		mTitle = title;
	}

	@Override
	public int getCount() {
		return mCourses.size();
	}

	@Override
	public Object getItem(int position) {
		return mCourses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.course_package_item, null);
			holder.title_tv = (TextView) convertView
					.findViewById(R.id.title_tv);
			holder.go_practice = (TextView) convertView
					.findViewById(R.id.gopractice_btn);
			holder.play_btn = (SmartImageView) convertView
					.findViewById(R.id.play_bg_btn);
			holder.fl = (FrameLayout) convertView.findViewById(R.id.fl);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title_tv.setText(mCourses.get(position).getName());
		holder.go_practice.setVisibility(View.GONE);
		holder.fl.setVisibility(View.GONE);
		holder.play_btn.setVisibility(View.GONE);
		holder.play_btn.setImageBitmap(null);

		// 需要判断视频是否为空，如果为空就不显示视频播放按钮
		if (!TextUtils.isEmpty(mCourses.get(position).getVideoUrl())) {
			holder.play_btn.setVisibility(View.VISIBLE);
			mImageLoader.displayImage(
					Constants.PICTURE_BASE_URL
							+ MD5Util.Md5(mCourses.get(position).getVideoUrl())
									.toUpperCase(), holder.play_btn);
			holder.fl.setVisibility(View.VISIBLE);
		}
		if (mCourses.get(position).getPractices() != null
				&& !mCourses.get(position).getPractices().isEmpty()) {
			holder.go_practice.setVisibility(View.VISIBLE);
		}

		holder.go_practice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 跳转到题库界面
				Intent intent = new Intent();
				intent.setClass(context, PracticeDetialActivity.class);
				intent.putExtra("practice", mCourses.get(position)
						.getPractices());
				intent.putExtra("courseId", mCourses.get(position).getId());
				// intent.putExtra("course_tree", treeCourseInfo);
				StringBuffer sb = new StringBuffer(mTitle);
				sb.append("·" + mCourses.get(position).getName());
				intent.putExtra("title", sb.toString());
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("course_name", sb.toString());
				MobclickAgent.onEvent(context, "进入课程包练习界面", map);
				context.startActivity(intent);
			}
		});
		holder.play_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, VedioPlayActivity.class);
				intent.putExtra("vediourl", mCourses.get(position)
						.getVideoUrl());
				intent.putExtra("course", mCourses);
				intent.putExtra("title", mTitle);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("course_name", mTitle);
				MobclickAgent.onEvent(context, "进入课程包视频播放界面", map);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	class ViewHolder {
		private TextView title_tv;
		private SmartImageView play_btn;
		private TextView go_practice;
		private FrameLayout fl;
	}

}
