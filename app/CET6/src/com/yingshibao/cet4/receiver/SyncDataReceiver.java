package com.yingshibao.cet4.receiver;

import com.yingshibao.cet4.util.NetWorkUtil;
import com.yingshibao.db.dao.PracticeDao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 同步数据
 * 
 * @author zhaoshan
 * 
 */
public class SyncDataReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		if(NetWorkUtil.isNetConnected(context)){
			//同步数据
		}
	}

}
