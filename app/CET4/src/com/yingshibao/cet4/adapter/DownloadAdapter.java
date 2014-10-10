package com.yingshibao.cet4.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mozillaonline.providers.DownloadManager;
import com.mozillaonline.providers.DownloadManager.Request;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.R;
import com.yingshibao.cet4.bean.Practice;
import com.yingshibao.db.dao.DownloadDao;

public class DownloadAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Practice> mPractices;
	private DownloadManager mDownloadManager;
	private String mTitle;
	private SharedPreferences mSharedPreferences;

	public class ViewHolder {
		TextView name;
		TextView status;
	}

	public DownloadAdapter(Context context, ArrayList<Practice> practices,
			String title) {
		mContext = context;
		mPractices = practices;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDownloadManager = new DownloadManager(mContext.getContentResolver(),
				mContext.getPackageName());
		mTitle = title;
		mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		mSharedPreferences.edit();
		new DownloadDao();

	}

	@Override
	public int getCount() {
		return mPractices.size();
	}

	@Override
	public Object getItem(int position) {
		return mPractices.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Practice practice = mPractices.get(position);
		ViewHolder holder = null;
		View view = null;
		if (convertView == null) {
			view = mInflater.inflate(R.layout.layout_audio_download_item, null,
					false);
			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.tv_name);
			holder.status = (TextView) view.findViewById(R.id.tv_status);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		File dir = new File(Environment.getExternalStorageDirectory(),
				"yingshibao");
		if (!dir.exists()) {
			dir.mkdir();
		}
		holder.name.setText("第" + (position + 1) + "题");
		final StringBuilder sb = new StringBuilder(mTitle);
		sb.append("·" + "第" + (position + 1) + "题.mp3");
		final File file = new File(dir, sb.toString());
		if (file.exists()) {
			holder.status.setText("已下载");
		}
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(mContext,
						R.anim.translate);
				v.startAnimation(anim);
				anim.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

						HashMap<String, String> map = new HashMap<String, String>();
						map.put("course_name", sb.toString());
						MobclickAgent.onEvent(mContext, "下载练习音频", map);
						startDownload(practice.getAudioUrl(), position,
								sb.toString());

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {

					}
				});

			}

		});
		return view;
	}

	// 开始下载
	private void startDownload(String url, int position, String fileName) {
		Uri srcUri = Uri.parse(url);
		DownloadManager.Request request = new Request(srcUri);
		request.setDestinationInExternalPublicDir("yingshibao", fileName);
		mDownloadManager.enqueue(request);
	}
}
