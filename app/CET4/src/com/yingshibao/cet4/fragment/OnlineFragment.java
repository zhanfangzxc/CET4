package com.yingshibao.cet4.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.R;
import com.yingshibao.cet4.activity.OnlineCoursesActivity;
import com.yingshibao.cet4.adapter.ChuanKeCourseAdapter;
import com.yingshibao.cet4.bean.ChuanKeCourseBean;
import com.yingshibao.cet4.bean.DataBean;
import com.yingshibao.cet4.constants.ChuanKeCourseTable;
import com.yingshibao.db.dao.ChuankeDao;

/**
 * 在线Fragment
 * 
 * @author malinkang
 * 
 */
public class OnlineFragment extends Fragment implements LoaderCallbacks<Cursor> {
	private ChuankeDao dao;
	private ChuanKeCourseBean chuankeCourseBeans;
	private ArrayList<DataBean> dataList;
	private Gson mGson;
	private ListView list;
	private ChuanKeCourseAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_online, container, false);
		list = (ListView) view.findViewById(R.id.list);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		dao = new ChuankeDao(getActivity());
		dataList = new ArrayList<DataBean>();
		adapter = new ChuanKeCourseAdapter(getActivity(), dataList);
		list.setAdapter(adapter);
		mGson = new Gson();

		getLoaderManager().initLoader(0, null, this);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), OnlineCoursesActivity.class);
				intent.putExtra("mDataBean", dataList.get(arg2));
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("course_name", dataList.get(arg2).getCourseName());
				MobclickAgent.onEvent(getActivity(), "进入在线课程", map);
				startActivity(intent);
			}
		});

	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return dao.getCursorLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		if (arg1 != null && arg1.moveToNext()) {
			String json = arg1.getString(arg1
					.getColumnIndex(ChuanKeCourseTable.COURSE_INFO));
			try {
				chuankeCourseBeans = mGson.fromJson(json,
						ChuanKeCourseBean.class);
				if (chuankeCourseBeans != null) {
					dataList.clear();
					dataList.addAll(chuankeCourseBeans.getData().getDataList());
					for (int i = 0; i < dataList.size(); i++) {
						if (!dataList.get(i).getCourseName().contains("四")) {
							dataList.remove(i);
							i--;
						}
					}
					adapter.notifyDataSetChanged();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

}
