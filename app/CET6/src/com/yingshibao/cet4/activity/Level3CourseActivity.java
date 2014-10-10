package com.yingshibao.cet4.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.bean.Course;
import com.yingshibao.cet4.bean.TreeCourseInfo;
import com.yingshibao.cet4.util.UIUtil;
import com.yingshibao.cet6.R;

/**
 * 第三级信息列表页
 * 
 * @author zhaoshan
 * 
 */
public class Level3CourseActivity extends BaseActivity {

	private Course course;
	private ArrayList<Course> childrenCourses;
	private ArrayList<String> childrenCourseNames;
	private TreeCourseInfo treeCourseInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level3_course);
		actionBar.setDisplayHomeAsUpEnabled(true);
		childrenCourseNames = new ArrayList<String>();
		course = (Course) getIntent().getSerializableExtra("course");
		treeCourseInfo = (TreeCourseInfo) getIntent().getSerializableExtra(
				"course_tree");
		final String title = getIntent().getStringExtra("title");

		actionBarTitle.setText(course.getName().replace("四级听力", ""));
		childrenCourses = course.getChildren();
		if (childrenCourses == null || childrenCourses.isEmpty()) {
			// 没有儿子
			Intent intent = new Intent(Level3CourseActivity.this,
					CoursePackageActivity.class);
			intent.putExtra("course", course);
			intent.putExtra("course_tree", treeCourseInfo);
			intent.putExtra("title", title);
			startActivity(intent);
			finish();
		} else {
			for (int i = 0; i < childrenCourses.size(); i++) {
				childrenCourseNames.add(childrenCourses.get(i).getName());
			}
		}
		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < childrenCourseNames.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("level3_text", childrenCourseNames.get(i));
			data.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
				data, R.layout.layout_level3_item,
				new String[] { "level3_text" }, new int[] { R.id.level3_tv });
		ListView listView = UIUtil.getView(this, R.id.lv_level3course);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(Level3CourseActivity.this,
						CoursePackageActivity.class);
				final StringBuffer sb = new StringBuffer(title);
				sb.append("·" + childrenCourses.get(arg2).getName());
				intent.putExtra("title", sb.toString());
				intent.putExtra("course", childrenCourses.get(arg2));
				intent.putExtra("course_tree", treeCourseInfo);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("course_name", sb.toString());
				MobclickAgent.onEvent(Level3CourseActivity.this, "进入课程包", map);
				startActivity(intent);
			}
		});

	}
}
