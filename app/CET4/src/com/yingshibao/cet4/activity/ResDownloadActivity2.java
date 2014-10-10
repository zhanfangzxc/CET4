package com.yingshibao.cet4.activity;

import java.io.File;
import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mozillaonline.providers.downloads.DownloadInfo;
import com.mozillaonline.providers.downloads.Downloads;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.R;
import com.yingshibao.cet4.adapter.DownloadAdapter2;
import com.yingshibao.cet4.bean.Course;
import com.yingshibao.cet4.constants.Constants;
import com.yingshibao.cet4.ui.MyGridView;

public class ResDownloadActivity2 extends BaseActivity {
	private ProgressBar progress;
	private TextView precent;
	private MyGridView downloadGV;
	private ArrayList<Course> mCourses;
	private DownloadAdapter2 adapter;
	private String mTitle;
	private MyReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_res_download);
		mTitle = getIntent().getStringExtra("title");
		mCourses = (ArrayList<Course>) getIntent().getSerializableExtra(
				"course");
		actionBarTitle.setText(mTitle);
		progress = (ProgressBar) findViewById(R.id.progress_precent);
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		// 总block数
		long totalBlocks = stat.getBlockCount();
		// 可用Block数目
		long availableBlocks = stat.getAvailableBlocks();
		// 一个Block的字节数
		long blockSize = stat.getBlockSize();
		// 总存储空间
		long totalBytes = totalBlocks * blockSize;
		// 可用存储空间
		long availableBytes = availableBlocks * blockSize;
		// 已使用存储空间
		long disAvailableBlocks = totalBytes - availableBytes;
		progress.setMax(100);
		progress.setProgress((int) (disAvailableBlocks / (double) totalBytes * 100));
		precent = (TextView) findViewById(R.id.tv_precent);
		precent.setText("已使用"
				+ Formatter.formatFileSize(this, disAvailableBlocks) + "/"
				+ "剩余" + Formatter.formatFileSize(this, availableBytes));
		downloadItem.setVisibility(View.VISIBLE);
		downloadItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(new Intent(ResDownloadActivity2.this,
						DownloadManageActivity.class));

			}
		});
		ArrayList<Course> courses = new ArrayList<Course>();
		for (int i = 0; i < mCourses.size(); i++) {
			if (!mCourses.get(i).getVideoUrl().isEmpty()) {
				courses.add(mCourses.get(i));
			}
		}
		adapter = new DownloadAdapter2(this, courses, mTitle);
		// 下载
		downloadGV = (MyGridView) findViewById(R.id.gv_download);
		downloadGV.setAdapter(adapter);

	}

	@Override
	protected void onResume() {
		super.onResume();
		receiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_DOWNLOAD);
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	private class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			ArrayList<DownloadInfo> downloadInfos = AppContext.getInstance()
					.getDownloadInfos();
			for (DownloadInfo download : downloadInfos) {
				long status = download.mStatus;
				String title = download.mTitle;
				for (int i = 0; i < mCourses.size(); i++) {
					String fileName = mTitle + "·" + mCourses.get(i).getName()
							+ ".mp4";
					if (title.equals(fileName)) {
						View view = downloadGV.getChildAt(i);
						TextView statusTv = (TextView) view
								.findViewById(R.id.tv_status);
						if (status == Downloads.STATUS_RUNNING) {
							statusTv.setText("正在下载");
						} else if (status == Downloads.STATUS_SUCCESS) {
							if (statusTv.getText().equals("正在下载")) {
								statusTv.setText("已下载");
							}
						} else if (status == Downloads.STATUS_PAUSED_BY_APP) {
							statusTv.setText("继续下载");
						} else {
							if (!statusTv.getText().equals("已下载")
									&& !statusTv.getText().equals("正在下载")) {
								statusTv.setText("下载失败");
								Toast.makeText(context, "下载失败，请重试",
										Toast.LENGTH_SHORT).show();
							}

						}
					}
				}

			}

		}
	}

}
