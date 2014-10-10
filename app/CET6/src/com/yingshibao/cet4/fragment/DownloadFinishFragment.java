package com.yingshibao.cet4.fragment;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.adapter.DownloadFinishAdapter;
import com.yingshibao.cet6.R;

public class DownloadFinishFragment extends Fragment {

	private Activity mActivity;
	private ListView list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_download, container,
				false);
		list = (ListView) view.findViewById(R.id.list);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = getActivity();
		File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath(), "cet6");
		if (file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			ArrayList<File> fileList = new ArrayList<File>();
			for (int i = 0; i < files.length; i++) {
				fileList.add(files[i]);
			}
			DownloadFinishAdapter adapter = new DownloadFinishAdapter(
					mActivity, fileList);
			list.setAdapter(adapter);
		}
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
