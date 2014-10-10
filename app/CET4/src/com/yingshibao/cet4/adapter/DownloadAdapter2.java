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
import com.yingshibao.cet4.bean.Course;
import com.yingshibao.db.dao.DownloadDao;

public class DownloadAdapter2 extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Course> mPractices;
	private DownloadManager mDownloadManager;
	private SharedPreferences mSharedPreferences;
	private String title;

	public class ViewHolder {
		TextView name;
		TextView status;
	}

	public DownloadAdapter2(Context context, ArrayList<Course> practices,
			String title) {
		mContext = context;
		mPractices = practices;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDownloadManager = new DownloadManager(mContext.getContentResolver(),
				mContext.getPackageName());
		mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		mSharedPreferences.edit();
		this.title = title;
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
		final Course practice = mPractices.get(position);
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
		if (practice.getName().equals("四级听力综述")) {
			practice.setName("综述");

		}
		if (practice.getName().length() > 2) {
			holder.name.setText(practice.getName().substring(0, 3));
		} else {
			holder.name.setText(practice.getName());
		}

		File dir = new File(Environment.getExternalStorageDirectory(),
				"yingshibao");
		if (!dir.exists()) {
			dir.mkdir();
		}
		final File file = new File(dir, title + "·" + practice.getName()
				+ ".mp4");

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
						map.put("course_name", title + "·" + practice.getName());
						MobclickAgent.onEvent(mContext, "下载视频", map);
						startDownload(practice.getVideoUrl(), title + "·"
								+ practice.getName() + ".mp4");
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
	private void startDownload(String url, String fileName) {
		Uri srcUri = Uri.parse(url);
		DownloadManager.Request request = new Request(srcUri);
		request.setDestinationInExternalPublicDir("yingshibao", fileName);
		mDownloadManager.enqueue(request);
	}
}
