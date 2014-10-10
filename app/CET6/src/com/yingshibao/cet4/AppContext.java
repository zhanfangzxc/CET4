package com.yingshibao.cet4;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.mozillaonline.providers.downloads.DownloadInfo;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yingshibao.cet4.bean.LoginInfo;
import com.yingshibao.cet4.bean.Word;
import com.yingshibao.cet4.bean.WordGroup;

/**
 * AppContext 全局应用程序类：用于保存和调用全局应用配置
 * 
 * @author malinkang
 * 
 */
public class AppContext extends Application {

	private static AppContext instance;
	
	private ArrayList<Word> unKnownwordList;

	public ArrayList<Word> getUnKnownwordList() {
		return unKnownwordList;
	}

	public void setUnKnownwordList(ArrayList<Word> unKnownwordList) {
		this.unKnownwordList = unKnownwordList;
	}

	
	private ArrayList<WordGroup> wordGroups;
	public ArrayList<WordGroup> getWordGroups() {
		return wordGroups;
	}

	public void setWordGroups(ArrayList<WordGroup> wordGroups) {
		this.wordGroups = wordGroups;
	}


	private String username;
	private String userpassword;
	private ArrayList<DownloadInfo> downloadInfos;

	public ArrayList<DownloadInfo> getDownloadInfos() {
		return downloadInfos;
	}

	public void setDownloadInfos(ArrayList<DownloadInfo> downloadInfos) {
		this.downloadInfos = downloadInfos;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private static String ticket;

	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(this);
		instance = this;
	}

	public static AppContext getInstance() {
		return instance;
	}

	
	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	private LoginInfo loginInfo;

	// ///////////////////////////////////////////////

	// //////////////////////////////////////////////////////

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		AppContext.ticket = ticket;
	}

	public static void setInstance(AppContext instance) {
		AppContext.instance = instance;
	}

	public boolean checkNetwork() {
		boolean netSataus = false;
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cwjManager != null && cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		return netSataus;
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

}
