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

import java.io.Serializable;
import java.util.ArrayList;

import com.yingshibao.cet4.bean.Practice;

/**
 * 
 * @author malinkang
 * 
 */
public class Playlist implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String TAG = "Playlist";

	private ArrayList<Integer> mPlayOrder = new ArrayList<Integer>();
	// 播放列表
	protected ArrayList<Practice> mPlaylist = null;
	// 当前选择索引t
	protected int selected = -1;

	public Playlist(ArrayList<Practice> playlist) {
		mPlaylist = playlist;
		calculateOrder(true);
	}

	public boolean isEmpty() {
		return mPlaylist.size() == 0;
	}

	public void selectNext() {
		if (!isEmpty()) {
			selected++;
			selected %= mPlaylist.size();
		}
	}

	// 上一个
	public void selectPrev() {
		if (!isEmpty()) {
			selected--;
			if (selected < 0)
				selected = mPlaylist.size() - 1;
		}
	}

	public void select(int index) {
		if (!isEmpty()) {
			if (index >= 0 && index < mPlaylist.size())
				selected = mPlayOrder.indexOf(index);
		}
	}

	//
	public int getSelectedIndex() {
		if (isEmpty()) {
			selected = -1;
		}
		if (selected == -1 && !isEmpty()) {
			selected = 0;
		}
		return selected;
	}

	// 获取当前练习
	public Practice getSelectedTrack() {
		Practice playlistEntry = null;

		int index = getSelectedIndex();
		if (index == -1) {
			return null;
		}
		index = mPlayOrder.get(index);
		if (index == -1) {
			return null;
		}
		playlistEntry = mPlaylist.get(index);

		return playlistEntry;

	}

	// 计算播放列表长度
	public int size() {
		return mPlaylist == null ? 0 : mPlaylist.size();
	}

	// 获取指定
	public Practice getPractice(int index) {
		return mPlaylist.get(index);
	}

	// 获取所有练习
	public Practice[] getAllTracks() {
		Practice[] out = new Practice[mPlaylist.size()];
		mPlaylist.toArray(out);
		return out;
	}

	// 计算顺序
	private void calculateOrder(boolean force) {
		if (mPlayOrder.isEmpty() || force) {
			if (!mPlayOrder.isEmpty()) {
				mPlayOrder.clear();
			}

			for (int i = 0; i < size(); i++) {
				mPlayOrder.add(i, i);
			}

		}
	}

	// 是否是最后一个练习
	public boolean isLastTrackOnList() {
		if (selected == size() - 1)
			return true;
		else
			return false;
	}

}
