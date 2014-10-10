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


/**
 * 
 * Interface to the player engine
 * 
 * @author Lukasz Wisniewski
 */
public interface PlayerEngine {
	// 打开播放列表
	public void openPlaylist(Playlist playlist);

	// 获取播放列表
	public Playlist getPlaylist();

	// 播放
	public void play();

	// 正在播放
	public boolean isPlaying();

	// 停止
	public void stop();

	// 暂停
	public void pause();

	// 下一首
	public void next();

	// 上一首
	public void prev();

	// 上一个列表
	public void prevList();

	//
	public void skipTo(int index);

	// 设置监听器
	public void setListener(PlayerEngineListener playerEngineListener);

	public void forward(int time);

	public void rewind(int time);
}
