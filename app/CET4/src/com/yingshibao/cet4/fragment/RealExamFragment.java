package com.yingshibao.cet4.fragment;

import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.R;
import com.yingshibao.cet4.adapter.RealExamAdapter;
import com.yingshibao.cet4.bean.Course;
import com.yingshibao.cet4.bean.TreeCourseInfo;
import com.yingshibao.cet4.constants.ParentCourseTable;
import com.yingshibao.cet4.util.LogUtil;
import com.yingshibao.cet4.util.ReadUtil;
import com.yingshibao.db.dao.ParentCourseDao;

/**
 * 真题Fragment
 * 
 * @author malinkang
 * 
 */
public class RealExamFragment extends Fragment implements
		LoaderCallbacks<Cursor> {

	private View view;
	private ParentCourseDao mParentCourseDao;
	private ExpandableListView real_exam_lv;
	private Course[] parents;
	private Course[][] children;
	private RealExamAdapter adapter;
	private ArrayList<Course> courses;
	private TreeCourseInfo treeCourseInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_real_exam, container, false);
		real_exam_lv = (ExpandableListView) view
				.findViewById(R.id.real_exam_lv);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); // 统计页面
		mParentCourseDao = new ParentCourseDao(getActivity());
		getLoaderManager().restartLoader(5, null, this);
	}

	private void initData(String json) {
		Gson gson = new Gson();
		treeCourseInfo = gson.fromJson(json, TreeCourseInfo.class);
		courses = treeCourseInfo.getCourseJsons();
		readData(courses);
		adapter = new RealExamAdapter(parents, children, getActivity(),
				treeCourseInfo);
		real_exam_lv.setAdapter(adapter);
	}

	private void readData(ArrayList<com.yingshibao.cet4.bean.Course> courses) {
		parents = new Course[courses.size()];
		children = new Course[courses.size()][];
		for (int i = 0; i < courses.size(); i++) {
			parents[i] = courses.get(i);
			ArrayList<com.yingshibao.cet4.bean.Course> childrenlist = parents[i]
					.getChildren();
			ArrayList<com.yingshibao.cet4.bean.Course> childrenchildlist = new ArrayList<Course>();
			int cllength = childrenlist.size();
			for (int j = 0; j < cllength; j++) {
				childrenchildlist.addAll(childrenlist.get(j).getChildren());
			}
			int ccllength = childrenchildlist.size();
			children[i] = new Course[ccllength];
			for (int j = 0; j < ccllength; j++) {
				children[i][j] = childrenchildlist.get(j);
			}
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return mParentCourseDao.getCursorLoader(getActivity(), "5");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		if (arg1 == null || arg1.getCount() == 0) {
			String json = ReadUtil.readFile(getActivity(),
					"wwyj_offline/practicecoursetree/5");
			if (json != null) {
				initData(json);
			}

		} else {
			if (arg1.moveToNext()) {
				String json = arg1.getString(arg1
						.getColumnIndex(ParentCourseTable.COURSE_JSON));
				if (json != null) {
					initData(json);
				}
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		LogUtil.e("onLoaderReset");
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}
}