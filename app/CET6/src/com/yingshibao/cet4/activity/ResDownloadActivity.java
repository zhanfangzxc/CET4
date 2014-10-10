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

import com.mozillaonline.providers.DownloadManager;
import com.mozillaonline.providers.downloads.DownloadInfo;
import com.mozillaonline.providers.downloads.Downloads;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.adapter.DownloadAdapter;
import com.yingshibao.cet4.bean.Practice;
import com.yingshibao.cet4.constants.Constants;
import com.yingshibao.cet4.ui.MyGridView;
import com.yingshibao.cet6.R;

public class ResDownloadActivity extends BaseActivity {
	private ProgressBar progress;
	private TextView precent;
	private MyGridView downloadGV;
	private ArrayList<Practice> practices;
	private DownloadAdapter adapter;
	private String mTitle;
	private MyReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_res_download);
		mTitle = getIntent().getStringExtra("title");
		practices = (ArrayList<Practice>) getIntent().getSerializableExtra(
				"practice");
		actionBarTitle.setText(mTitle);
		progress = (ProgressBar) findViewById(R.id.progress_precent);
		new DownloadManager(getContentResolver(), getPackageName());
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

				startActivity(new Intent(ResDownloadActivity.this,
						DownloadManageActivity.class));

			}
		});
		adapter = new DownloadAdapter(this, practices, mTitle);
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
				File dir = new File(Environment.getExternalStorageDirectory(),
						"yingshibao");
				if (!dir.exists()) {
					dir.mkdir();
				}
				for (int i = 0; i < practices.size(); i++) {

					String fileName = mTitle + "·" + "第" + (i + 1) + "题.mp3";
					final File file = new File(dir, fileName);
					if (title.equals(fileName)) {
						View view = downloadGV.getChildAt(i);
						TextView statusTv = (TextView) view
								.findViewById(R.id.tv_status);
						if (status == Downloads.STATUS_RUNNING) {
							statusTv.setText("正在下载");
						} else if (status == Downloads.STATUS_SUCCESS
								&& file.exists()) {
							statusTv.setText("已下载");
						} else if (status == Downloads.STATUS_PAUSED_BY_APP) {
							statusTv.setText("继续下载");
						}
					}
					adapter.notifyDataSetChanged();
				}

			}

		}
	}

}
