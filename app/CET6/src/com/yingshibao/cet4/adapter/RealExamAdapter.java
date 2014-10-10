package com.yingshibao.cet4.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.activity.PracticeDetialActivity;
import com.yingshibao.cet4.bean.Course;
import com.yingshibao.cet4.bean.Practice;
import com.yingshibao.cet4.bean.PracticeInfo;
import com.yingshibao.cet4.bean.TreeCourseInfo;
//import com.yingshibao.cet4.api.PracticeApi;
//import com.yingshibao.db.dao.PracticeDao;
import com.yingshibao.cet6.R;

public class RealExamAdapter extends BaseExpandableListAdapter {
	private Context context;
	private Course[] parents;
	private Course[][] children;
	private int childs[][];
	// private PracticeDao practiceDao;
	// private PracticeApi practiceApi;
	private Course course;
	private ArrayList<Practice> practices;
	private int courseId;
	private ProgressDialog dialog;
	private TreeCourseInfo treeCourseInfo;

	public RealExamAdapter(Course[] parents, Course[][] children,
			Context context, TreeCourseInfo treeCourseInfo) {
		super();
		dialog = new ProgressDialog(context);
		dialog.setMessage("加载中！请稍候。。。");
		this.treeCourseInfo = treeCourseInfo;
		this.context = context;
		this.parents = parents;
		this.children = children;
		childs = new int[parents.length][1];
		// practiceDao = new PracticeDao(context);
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		return childs[arg0][arg1];
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

	@Override
	public View getChildView(final int groupPosition, int childPosition,
			boolean arg2, View view, ViewGroup parent) {
		ChildViewHolder holder = null;
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.real_exam_listitem, parent, false);
		holder = new ChildViewHolder();
		holder.short_toke = (ImageView) view.findViewById(R.id.short_toke);
		holder.long_toke = (ImageView) view.findViewById(R.id.long_toke);
		holder.text = (ImageView) view.findViewById(R.id.text);
		holder.read = (ImageView) view.findViewById(R.id.read);
		holder.noListen = (TextView) view.findViewById(R.id.noListen);
		view.setTag(holder);
		holder.short_toke.setTag(groupPosition);
		holder.long_toke.setTag(groupPosition);
		holder.text.setTag(groupPosition);
		holder.read.setTag(groupPosition);
		boolean hasCourseShort = false;
		boolean hasCourseLong = false;
		boolean hasCourseText = false;
		boolean hasCourseRead = false;
		for (int i = 0; i < children[groupPosition].length; i++) {
			final int index = i;
			if ("短对话".equals(children[groupPosition][i].getName())) {
				hasCourseShort = true;
				holder.short_toke.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.show();
						course = children[(Integer) v.getTag()][index];
						courseId = Integer.parseInt(course.getId());
						loadPracticeFromHttp(parents[groupPosition].getName()
								+ "·" + "短对话");
					}
				});
			} else if ("长对话".equals(children[groupPosition][i].getName())) {
				hasCourseLong = true;
				holder.long_toke.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.show();
						course = children[(Integer) v.getTag()][index];
						courseId = Integer.parseInt(course.getId());
						loadPracticeFromHttp(parents[groupPosition].getName()
								+ "·" + "长对话");
					}
				});
			} else if ("篇章".equals(children[groupPosition][i].getName())) {
				hasCourseText = true;
				holder.text.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.show();
						course = children[(Integer) v.getTag()][index];
						courseId = Integer.parseInt(course.getId());
						loadPracticeFromHttp(parents[groupPosition].getName()
								+ "·" + "篇章");
					}
				});
			} else {
				hasCourseRead = true;
				holder.read.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.show();
						Course course = children[(Integer) v.getTag()][index];
						courseId = Integer.parseInt(course.getId());
						loadPracticeFromHttp(parents[groupPosition].getName()
								+ "·" + "深度阅读");
					}
				});
			}
		}
		if (!hasCourseShort) {
			// holder.short_toke.setOnClickListener(listener);
			holder.short_toke.setVisibility(View.GONE);
		}
		if (!hasCourseLong) {
			// holder.long_toke.setOnClickListener(listener);
			holder.long_toke.setVisibility(View.GONE);
		}
		if (!hasCourseText) {
			// holder.text.setOnClickListener(listener);
			holder.text.setVisibility(View.GONE);
		}
		if (!hasCourseRead) {
			// holder.read.setOnClickListener(listener);
			holder.read.setVisibility(View.GONE);
		}
		if (!hasCourseShort && !hasCourseLong && !hasCourseText) {
			holder.noListen.setVisibility(View.VISIBLE);
		}
		return view;
	}

	@Override
	public int getChildrenCount(int arg0) {
		return childs[arg0].length;
	}

	@Override
	public Object getGroup(int arg0) {
		return parents[arg0];
	}

	@Override
	public int getGroupCount() {
		return parents.length;
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(int position, boolean isdown, View view,
			ViewGroup parent) {
		GroupViewHolder holder = null;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			view = inflater.inflate(R.layout.real_exam_group, parent, false);
			holder = new GroupViewHolder();
			holder.textView = (TextView) view
					.findViewById(R.id.real_exam_group_tv);
			holder.imageView = (ImageView) view
					.findViewById(R.id.real_exam_group_iv);
			view.setTag(holder);
		} else {
			holder = (GroupViewHolder) view.getTag();
		}
		holder.textView.setText(parents[position].getName());
		if (isdown) {
			holder.imageView.setImageResource(R.drawable.flag_press);
		} else {
			holder.imageView.setImageResource(R.drawable.flag_unpress);
		}
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

	public class GroupViewHolder {
		ImageView imageView;
		TextView textView;
	}

	public class ChildViewHolder {
		ImageView short_toke;
		ImageView long_toke;
		ImageView text;
		ImageView read;
		TextView noListen;
	}

	private void loadPracticeFromHttp(final String title) {
		dialog.dismiss();
		loadPracticeFromOffLine(courseId, "wwyj_offline/practicequery/"
				+ courseId, title);
	}

	private void loadPracticeFromOffLine(int courseId, String datapath,
			String title) {
		String json = "";
		AssetManager assetManager = context.getAssets();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					assetManager.open(datapath)));
			String s = "";
			while ((s = reader.readLine()) != null) {
				json += s;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		initActivity(json, title);
	}

	private void initActivity(String json, String title) {
		Gson gson = new Gson();
		PracticeInfo practiceInfo = gson.fromJson(json, PracticeInfo.class);
		practices = practiceInfo.getPracticeJsons();
		Intent intent = new Intent();
		intent.setClass(context, PracticeDetialActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("courseId", "" + courseId);
		intent.putExtra("practice", practices);
		intent.putExtra("course_tree", treeCourseInfo);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("course_name", title);
		MobclickAgent.onEvent(context, "点击套题进入练习", map);
		context.startActivity(intent);
	}
}
