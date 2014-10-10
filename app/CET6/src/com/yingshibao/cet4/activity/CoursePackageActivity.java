package com.yingshibao.cet4.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.widget.ListView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.adapter.CoursePackageAdapter;
import com.yingshibao.cet4.api.PracticeApi;
import com.yingshibao.cet4.bean.Course;
import com.yingshibao.cet4.bean.PracticeInfo;
import com.yingshibao.cet4.bean.TreeCourseInfo;
import com.yingshibao.cet4.constants.PracticeTable;
import com.yingshibao.cet4.util.ReadUtil;
import com.yingshibao.cet4.util.UIUtil;
import com.yingshibao.cet6.R;
import com.yingshibao.db.dao.PracticeDao;

/**
 * 
 * @author malinkang
 * 
 *         fuck jiekou
 */

public class CoursePackageActivity extends BaseActivity implements
		LoaderCallbacks<Cursor> {
	private Course course;
	private ArrayList<Course> childrenCourses;// 子课程的集合
	private ArrayList<Course> courses;
	private PracticeDao practiceDao;
	private PracticeApi practiceApi;
	private Map<String, Course> courseMap;
	private Gson mGson;
	private CoursePackageAdapter adapter;
	private TreeCourseInfo treeCourseInfo;
	private String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_package);
		treeCourseInfo = (TreeCourseInfo) getIntent().getSerializableExtra(
				"course_tree");
		course = (Course) getIntent().getSerializableExtra("course");
		title = getIntent().getStringExtra("title");
		actionBarTitle.setText(title);
		childrenCourses = course.getChildren();
		courses = new ArrayList<Course>();
		courseMap = new HashMap<String, Course>();
		mGson = new Gson();
		adapter = new CoursePackageAdapter(this, courses, treeCourseInfo, title);
		ListView listView = UIUtil.getView(this, R.id.cource_package_listview);
		listView.setAdapter(adapter);
		practiceDao = new PracticeDao(this);

		if (childrenCourses.isEmpty()) {
			// 没有儿子
			getSupportLoaderManager().initLoader(
					Integer.parseInt(course.getId()), null, this);
			//

			courses.add(course);

			Map<String, String> map = new HashMap<String, String>();
			map.put("courseId", course.getId());
			map.put("token", AppContext.getInstance().getLoginInfo().getToken());
			courseMap.put(course.getId(), course);
			practiceApi = new PracticeApi(this);
			practiceApi.queryPractice(map);
		} else {
			// 儿子

			for (int i = 0; i < childrenCourses.size(); i++) {
				// 孙子
				ArrayList<Course> grandsonCourse = childrenCourses.get(i)
						.getChildren();
				if (grandsonCourse.isEmpty()) {
					// 没有孙子
					getSupportLoaderManager().initLoader(
							Integer.parseInt(childrenCourses.get(i).getId()),
							null, this);
					courseMap.put(childrenCourses.get(i).getId(),
							childrenCourses.get(i));

					courses.add(childrenCourses.get(i));

					Map<String, String> map = new LinkedHashMap<String, String>();
					map.put("courseId", childrenCourses.get(i).getId());
					map.put("token", AppContext.getInstance().getLoginInfo()
							.getToken());
					practiceApi = new PracticeApi(this);
					practiceApi.queryPractice(map);

				} else {
					// 有孙子
					for (int j = 0; j < grandsonCourse.size(); j++) {
						getSupportLoaderManager()
								.initLoader(
										Integer.parseInt(grandsonCourse.get(j)
												.getId()), null, this);
						if (!TextUtils.isEmpty(grandsonCourse.get(j)
								.getVideoUrl())) {
							courses.add(grandsonCourse.get(j));
						}
						courseMap.put(grandsonCourse.get(j).getId(),
								grandsonCourse.get(j));
						Map<String, String> map = new LinkedHashMap<String, String>();
						map.put("courseId", grandsonCourse.get(j).getId());
						map.put("token", AppContext.getInstance()
								.getLoginInfo().getToken());
						practiceApi = new PracticeApi(this);
						practiceApi.queryPractice(map);
					}
				}
			}
		}

	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return practiceDao.getCursorLoader(this, arg0 + "");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor == null || cursor.getCount() == 0) {
			String practiceInfoJson = ReadUtil.readFile(this,
					"wwyj_offline/practicequery/" + arg0.getId());
			PracticeInfo practiceInfo = mGson.fromJson(practiceInfoJson,
					PracticeInfo.class);
			if (practiceInfo != null
					&& !practiceInfo.getPracticeJsons().isEmpty()) {
				for (Course c : courses) {
					if (c.getId().equals(arg0.getId() + "")) {
						c.setPractices(practiceInfo.getPracticeJsons());
						adapter.notifyDataSetChanged();
						return;
					}

				}
				Course c = courseMap.get(arg0.getId() + "");
				c.setPractices(practiceInfo.getPracticeJsons());
				courses.add(c);
				adapter.notifyDataSetChanged();
			}
		} else if (cursor.moveToNext()) {
			String practiceJson = cursor.getString(cursor
					.getColumnIndex(PracticeTable.PRACTICE_JSON));
			PracticeInfo practiceInfo = mGson.fromJson(practiceJson,
					PracticeInfo.class);
			if (!practiceInfo.getPracticeJsons().isEmpty()) {
				for (Course c : courses) {
					if (c.getId().equals(arg0.getId() + "")) {
						// 设置练习
						c.setPractices(practiceInfo.getPracticeJsons());
						adapter.notifyDataSetChanged();
						return;
					}
				}
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

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
