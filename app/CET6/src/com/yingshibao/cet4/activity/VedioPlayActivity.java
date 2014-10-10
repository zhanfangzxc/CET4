package com.yingshibao.cet4.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar.LayoutParams;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mozillaonline.providers.DownloadManager;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.bean.Course;
import com.yingshibao.cet4.util.LogUtil;
import com.yingshibao.cet4.util.Player;
import com.yingshibao.cet6.R;
import com.yingshibao.db.dao.DownloadDao;

/**
 */
public class VedioPlayActivity extends BaseActivity implements OnClickListener {

	private String vediopath;

	private SeekBar vedio_seekBar;

	private SurfaceView surfaceView;

	private ImageView vedio_big_play;

	private LinearLayout bottomLayout;

	private ImageView vedio_play;

	private LinearLayout download;

	private TextView time_now;

	private TextView time_all;

	private ProgressBar please_wait;

	protected LayoutParams params;

	protected ImageView back;

	private Player player;

	private boolean isPlaying = false;

	private boolean isFistPlaying = true;

	private String vediourl;

	private boolean hasDownload = false;

	private String title;

	private String flag;
	private ArrayList<Course> courses;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_vedio);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		actionBarTitle.setText("视频播放");
		Intent intent = getIntent();
		vediourl = intent.getStringExtra("vediourl");
		flag = intent.getStringExtra("flag");
		courses = (ArrayList<Course>) intent.getSerializableExtra("course");
		title = intent.getStringExtra("title");
		String fileName = new DownloadDao().getFileName(vediourl);
		if (fileName != null) {
			LogUtil.e("文件名" + fileName);
			File file = new File(Environment.getExternalStorageDirectory()
					+ "/cet6/" + fileName);

			if (file.exists()) {
				vediourl = file.getAbsolutePath();
				hasDownload = true;
			}
			LogUtil.e("文件路径" + file.getAbsolutePath());
		}
		if (vediourl != null && vediourl.length() > 0) {
			vediopath = vediourl;
		}
		vedio_seekBar = (SeekBar) findViewById(R.id.vedio_seekBar);
		vedio_big_play = (ImageView) findViewById(R.id.vedio_big_play);
		vedio_big_play.setOnClickListener(this);
		surfaceView = (SurfaceView) findViewById(R.id.mSurfaceView1);
		surfaceView.setOnClickListener(this);
		bottomLayout = (LinearLayout) findViewById(R.id.bottomLayout);
		download = (LinearLayout) findViewById(R.id.download);
		download.setOnClickListener(this);
		if (flag != null) {
			download.setVisibility(View.INVISIBLE);
		}
		vedio_play = (ImageView) findViewById(R.id.vedio_play);
		vedio_play.setOnClickListener(this);
		time_now = (TextView) findViewById(R.id.time_now);
		time_all = (TextView) findViewById(R.id.time_all);
		please_wait = (ProgressBar) findViewById(R.id.please_wait);
		player = new Player(surfaceView, vedio_seekBar, time_all, time_now,
				please_wait, this);
		new DownloadManager(getContentResolver(), getPackageName());
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		HashMap<String, String> map = new HashMap<String, String>();
		switch (v.getId()) {
		case R.id.vedio_big_play:
			map.put("course_name", title);
			MobclickAgent.onEvent(this, "点击视频播放按钮", map);
			isPlaying = true;
			isFistPlaying = false;
			vedio_play.setImageResource(R.drawable.vedio_pause);
			vedio_big_play.setVisibility(View.GONE);
			player.playUrl(vediopath);
			break;
		case R.id.vedio_play:
			map.put("course_name", title);
			MobclickAgent.onEvent(this, "点击视频播放按钮", map);
			if (!isPlaying) {
				isPlaying = true;
				if (isFistPlaying) {
					isFistPlaying = false;
					player.playUrl(vediopath);
				} else {
					player.play();
				}
				vedio_big_play.setVisibility(View.GONE);
				vedio_play.setImageResource(R.drawable.vedio_pause);
			} else {
				isPlaying = false;
				vedio_play.setImageResource(R.drawable.vedio_play);
				player.pause();
			}
			break;
		case R.id.download:
			Intent intent = new Intent(this, ResDownloadActivity2.class);
			intent.putExtra("course", courses);
			intent.putExtra("title", title);
			map.put("course_name", title);
			MobclickAgent.onEvent(this, "进入视频下载页面", map);
			startActivity(intent);
			break;
		case R.id.mSurfaceView1:
			int flag = bottomLayout.getVisibility();
			if (flag == View.GONE) {
				actionBar.show();
				vedio_seekBar.setVisibility(View.VISIBLE);
				bottomLayout.setVisibility(View.VISIBLE);
			} else {
				actionBar.hide();
				vedio_seekBar.setVisibility(View.GONE);
				bottomLayout.setVisibility(View.GONE);
			}
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		player.release();
	}

	@Override
	protected void onPause() {
		super.onPause();
		isPlaying = false;
		vedio_play.setImageResource(R.drawable.vedio_play);
		player.pause();
	}

}
