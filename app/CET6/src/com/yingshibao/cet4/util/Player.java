package com.yingshibao.cet4.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Player implements OnBufferingUpdateListener, OnCompletionListener,
		MediaPlayer.OnPreparedListener, SurfaceHolder.Callback,
		OnSeekBarChangeListener {
	private int videoWidth;
	private int videoHeight;
	public MediaPlayer mediaPlayer;
	private SurfaceHolder surfaceHolder;
	private SeekBar seekBar;
	private Timer mTimer = new Timer();
	private SimpleDateFormat formatter;
	private TextView time_now;
	private TextView time_all;
	private ProgressBar please_wait;
	private Context context;
	private ConnectivityManager cm;
	private NetworkInfo netInfo;

	@SuppressWarnings("deprecation")
	public Player(SurfaceView surfaceView, SeekBar seekBar, TextView timeAll,
			TextView timeNow, ProgressBar please_wait, Context context) {
		this.seekBar = seekBar;
		seekBar.setOnSeekBarChangeListener(this);
		this.time_all = timeAll;
		this.time_now = timeNow;
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mTimer.schedule(mTimerTask, 0, 1000);
		formatter = new SimpleDateFormat("mm:ss");
		this.please_wait = please_wait;
		cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		netInfo = cm.getActiveNetworkInfo();
	}

	/*******************************************************
	 * 通过定时器和Handler来更新进度条
	 ******************************************************/
	TimerTask mTimerTask = new TimerTask() {
		@Override
		public void run() {
			if (mediaPlayer == null)
				return;
			if (mediaPlayer.isPlaying() && seekBar.isPressed() == false) {
				handleProgress.sendEmptyMessage(0);
			}
		}
	};

	Handler handleProgress = new Handler() {
		public void handleMessage(Message msg) {

			int position = mediaPlayer.getCurrentPosition();
			int duration = mediaPlayer.getDuration();

			if (duration > 0) {
				time_now.setText(getTimeNow());
				long pos = seekBar.getMax() * position / duration;
				seekBar.setProgress((int) pos);
			}
		};
	};

	// *****************************************************

	public void play() {
		mediaPlayer.start();
	}

	public String getTimeAll() {
		int time = 0;
		if (mediaPlayer != null) {
			time = mediaPlayer.getDuration();
		}
		String hms = formatter.format(time);
		return hms;
	}

	public String getTimeNow() {
		int time = 0;
		if (mediaPlayer != null) {
			time = mediaPlayer.getCurrentPosition();
		}
		String hms = formatter.format(time);
		return hms;
	}

	public void playUrl(String videoUrl) {
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(videoUrl);
			if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				// 有可用的网络
				please_wait.setVisibility(View.VISIBLE);
				mediaPlayer.prepareAsync();// prepare之后自动播放
			} else {
				mediaPlayer.prepare();
			}
			// mediaPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void release() {
		mTimerTask.cancel();
		if (mediaPlayer != null) {
			mediaPlayer.release();
		}
	}

	public void pause() {
		if (mediaPlayer != null) {
			mediaPlayer.pause();
		}

	}

	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setDisplay(surfaceHolder);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
		} catch (Exception e) {
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	@Override
	/**  
	 * 通过onPrepared播放  
	 */
	public void onPrepared(MediaPlayer arg0) {
		videoWidth = mediaPlayer.getVideoWidth();
		videoHeight = mediaPlayer.getVideoHeight();
		if (videoHeight != 0 && videoWidth != 0) {
			arg0.start();
			time_all.setText(getTimeAll());
			please_wait.setVisibility(View.GONE);
		}
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
		seekBar.setSecondaryProgress(bufferingProgress);

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int duration = mediaPlayer.getDuration();
		mediaPlayer.seekTo(seekBar.getProgress() * duration / seekBar.getMax());
	}

}