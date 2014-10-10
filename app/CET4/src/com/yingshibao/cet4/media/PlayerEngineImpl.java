/*
 * Copyright (C) 2009 Teleca Poland Sp. z o.o. <android@teleca.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yingshibao.cet4.media;

import java.io.File;
import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Environment;
import android.os.Handler;

import com.yingshibao.cet4.bean.Practice;
import com.yingshibao.cet4.util.LogUtil;
import com.yingshibao.db.dao.DownloadDao;

/**
 * Player core engine allowing playback, in other words, a wrapper around
 * Android's <code>MediaPlayer</code>, supporting <code>Playlist</code> classes
 * 
 * @author Lukasz Wisniewski
 */
public class PlayerEngineImpl implements PlayerEngine {

	/**
	 * Time frame - used for counting number of fails within that time
	 */
	private static final long FAIL_TIME_FRAME = 1000;

	/**
	 * Acceptable number of fails within FAIL_TIME_FRAME
	 */
	private static final int ACCEPTABLE_FAIL_NUMBER = 2;

	/**
	 * Beginning of last FAIL_TIME_FRAME
	 */
	private long mLastFailTime;

	/**
	 * Number of times failed within FAIL_TIME_FRAME
	 */
	private long mTimesFailed;

	/**
	 * Simple MediaPlayer extensions, adds reference to the current track
	 * 
	 * @author Lukasz Wisniewski
	 */
	private class InternalMediaPlayer extends MediaPlayer {

		/**
		 * Keeps record of currently played track, useful when dealing with
		 * multiple instances of MediaPlayer
		 */
		public Practice practice;

		/**
		 * Still buffering
		 */
		public boolean preparing = false;

		/**
		 * Determines if we should play after preparation, e.g. we should not
		 * start playing if we are pre-buffering the next track and the old one
		 * is still playing
		 */
		// public boolean playAfterPrepare = false;

	}

	/**
	 * InternalMediaPlayer instance (maybe add another one for cross-fading)
	 */
	private InternalMediaPlayer mCurrentMediaPlayer;

	/**
	 * Listener to the engine events
	 */
	private PlayerEngineListener mPlayerEngineListener;

	/**
	 * Playlist
	 */
	private Playlist mPlaylist = null;

	/**
	 * Playlist of song played before
	 */
	private Playlist prevPlaylist = null;

	/**
	 * Handler to the context thread
	 */
	private Handler mHandler;

	/**
	 * Runnable periodically querying Media Player about the current position of
	 * the track and notifying the listener
	 */
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {

			if (mPlayerEngineListener != null) {
				// TODO use getCurrentPosition less frequently (usage of
				// currentTimeMillis or uptimeMillis)
				if (mCurrentMediaPlayer != null)
					mPlayerEngineListener.onTrackProgress(mCurrentMediaPlayer
							.getCurrentPosition());
				mHandler.postDelayed(this, 0);
			}
		}
	};

	private String fileName;

	// 构造函数
	public PlayerEngineImpl() {
		mLastFailTime = 0;
		mTimesFailed = 0;
		mHandler = new Handler();
	}

	@Override
	public void next() {
		if (mPlaylist != null) {
			mPlaylist.selectNext();
			play();
		}
	}

	@Override
	public void openPlaylist(Playlist playlist) {
		if (!playlist.isEmpty()) {
			prevPlaylist = mPlaylist;
			mPlaylist = playlist;
		} else
			mPlaylist = null;
	}

	// 暂停
	@Override
	public void pause() {
		if (mCurrentMediaPlayer != null) {
			// still preparing
			if (mCurrentMediaPlayer.preparing) {
				// mCurrentMediaPlayer.playAfterPrepare = false;
				return;
			}

			// check if we play, then pause
			if (mCurrentMediaPlayer.isPlaying()) {
				mCurrentMediaPlayer.pause();
				if (mPlayerEngineListener != null)
					mPlayerEngineListener.onTrackPause();
				return;
			}
		}
	}

	// 播放
	@Override
	public void play() {

		if (mPlayerEngineListener.onTrackStart() == false) {
			return;
		}
		if (mPlaylist != null) {
			if (mCurrentMediaPlayer == null) {
				mCurrentMediaPlayer = build(mPlaylist.getSelectedTrack());
			}

			if (mCurrentMediaPlayer != null
					&& mCurrentMediaPlayer.practice != mPlaylist
							.getSelectedTrack()) {
				cleanUp(); // this will do the cleanup job
				mCurrentMediaPlayer = build(mPlaylist.getSelectedTrack());
			}

			if (mCurrentMediaPlayer == null)
				return;

			// check if current media player is not still buffering
			if (!mCurrentMediaPlayer.preparing) {

				// prevent double-press
				if (!mCurrentMediaPlayer.isPlaying()) {

					// starting timer
					mHandler.removeCallbacks(mUpdateTimeTask);
					mHandler.postDelayed(mUpdateTimeTask, 1000);
					mCurrentMediaPlayer.start();
				}
			} else {
				// tell the mediaplayer to play the song as soon as it ends
				// preparing
				// mCurrentMediaPlayer.playAfterPrepare = true;
			}
		}
	}

	@Override
	public void prev() {
		if (mPlaylist != null) {
			mPlaylist.selectPrev();
			play();
		}
	}

	@Override
	public void skipTo(int index) {
		mPlaylist.select(index);
		play();
	}

	@Override
	public void stop() {
		cleanUp();

		if (mPlayerEngineListener != null) {
			mPlayerEngineListener.onTrackStop();
		}
	}

	// 销毁对象
	private void cleanUp() {
		// nice clean-up job
		if (mCurrentMediaPlayer != null) {
			try {
				mCurrentMediaPlayer.stop();
			} catch (IllegalStateException e) {

			} finally {
				mCurrentMediaPlayer.release();
				mCurrentMediaPlayer = null;
			}
		}
	}

	// 构建MediaPlayer
	private InternalMediaPlayer build(Practice practice) {
		final InternalMediaPlayer mediaPlayer = new InternalMediaPlayer();
		String path = practice.getAudioUrl();
		if (new DownloadDao().getFileName(path) != null) {
			fileName = new DownloadDao().getFileName(path).mTitle;
		}
		if (fileName != null && !fileName.isEmpty()) {
			LogUtil.e("文件名" + fileName);
			File file = new File(Environment.getExternalStorageDirectory()
					+ "/yingshibao/" + fileName);
			path = file.getAbsolutePath();
			LogUtil.e("文件路径" + file.getAbsolutePath());
		}
		try {
			// 设置数据源
			mediaPlayer.setDataSource(path);
			mediaPlayer.practice = practice;
			// 播放监听期
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					stop();
				}

			});
			// 准备监听器
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					mediaPlayer.preparing = false;
					if (mPlaylist.getSelectedTrack() == mediaPlayer.practice) {
						// mediaPlayer.playAfterPrepare = false;
						play();
						if (mPlayerEngineListener != null) {
							//
							mPlayerEngineListener.onTrackChanged(mediaPlayer
									.getDuration());
						}
					}

				}

			});

			mediaPlayer
					.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
						@Override
						public void onBufferingUpdate(MediaPlayer mp,
								int percent) {
							if (mPlayerEngineListener != null) {
								mPlayerEngineListener.onTrackBuffering(percent);
							}
						}

					});

			mediaPlayer.setOnErrorListener(new OnErrorListener() {

				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {

					if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
						// we probably lack network
						if (mPlayerEngineListener != null) {
							mPlayerEngineListener.onTrackStreamError();
						}
						stop();
						return true;
					}

					if (what == -1) {
						long failTime = System.currentTimeMillis();
						if (failTime - mLastFailTime > FAIL_TIME_FRAME) {
							// outside time frame
							mTimesFailed = 1;
							mLastFailTime = failTime;

						} else {
							// inside time frame
							mTimesFailed++;
							if (mTimesFailed > ACCEPTABLE_FAIL_NUMBER) {
								stop();
								return true;
							}
						}
					}
					return false;
				}
			});

			mediaPlayer.preparing = true;
			mediaPlayer.prepareAsync();

			// this is a new track, so notify the listener

			return mediaPlayer;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Playlist getPlaylist() {
		return mPlaylist;
	}

	@Override
	public boolean isPlaying() {
		if (mCurrentMediaPlayer == null)
			return false;
		if (mCurrentMediaPlayer.preparing)
			return false;
		return mCurrentMediaPlayer.isPlaying();
	}

	@Override
	public void setListener(PlayerEngineListener playerEngineListener) {
		mPlayerEngineListener = playerEngineListener;
	}

	public void forward(int time) {
		mCurrentMediaPlayer.seekTo(mCurrentMediaPlayer.getCurrentPosition()
				+ time);

	}

	@Override
	public void rewind(int time) {
		mCurrentMediaPlayer.seekTo(mCurrentMediaPlayer.getCurrentPosition()
				- time);
	}

	public void seekTo(int progress) {
		mCurrentMediaPlayer.seekTo(progress);
	}

	@Override
	public void prevList() {
		if (prevPlaylist != null) {
			openPlaylist(prevPlaylist);
			play();
		}
	}

	public InternalMediaPlayer getmCurrentMediaPlayer() {
		return mCurrentMediaPlayer;
	}

	public void setmCurrentMediaPlayer(InternalMediaPlayer mCurrentMediaPlayer) {
		this.mCurrentMediaPlayer = mCurrentMediaPlayer;
	}

}
