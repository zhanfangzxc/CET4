package com.yingshibao.cet4.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mozillaonline.providers.DownloadManager;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.adapter.DownloadUnfinishAdapter;
import com.yingshibao.cet6.R;

/**
 * 
 * 
 * @author malinkang
 * 
 */
public class DownloadUnFinishFragment extends Fragment {
	private Activity mActivity;
	private ListView list;
	private DownloadUnfinishAdapter adapter;
	private DownloadManager mDownloadManager;
	private Cursor mDateSortedCursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mActivity = getActivity();
		View view = inflater.inflate(R.layout.fragment_download, container,
				false);
		list = (ListView) view.findViewById(R.id.list);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mDownloadManager = new DownloadManager(mActivity.getContentResolver(),
				mActivity.getPackageName());
		DownloadManager.Query baseQuery = new DownloadManager.Query()
				.setFilterByStatus(DownloadManager.STATUS_PAUSED
						| DownloadManager.STATUS_PENDING
						| DownloadManager.STATUS_RUNNING);
		mDateSortedCursor = mDownloadManager.query(baseQuery);
		if (mDateSortedCursor != null) {
			adapter = new DownloadUnfinishAdapter(mActivity, mDateSortedCursor,
					mDownloadManager);
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
