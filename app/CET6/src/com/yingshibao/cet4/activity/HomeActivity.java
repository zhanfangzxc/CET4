package com.yingshibao.cet4.activity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.mozillaonline.providers.downloads.DownloadService;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.api.ChuanKeApi;
import com.yingshibao.cet4.api.CourseApi;
import com.yingshibao.cet4.bean.ClientVersion;
import com.yingshibao.cet4.bean.Version;
import com.yingshibao.cet4.constants.Constants;
import com.yingshibao.cet4.fragment.ExamFragment;
import com.yingshibao.cet4.fragment.OnlineFragment;
import com.yingshibao.cet4.fragment.RealExamFragment;
import com.yingshibao.cet4.fragment.UserCenterFragment;
import com.yingshibao.cet4.util.LogUtil;
import com.yingshibao.cet4.util.MD5Util;
import com.yingshibao.cet4.util.StreamTools;
import com.yingshibao.cet4.util.UIUtil;
import com.yingshibao.cet6.R;

public class HomeActivity extends BaseActivity implements OnPageChangeListener,
		OnCheckedChangeListener {
	private int count = 0;
	private ArrayList<Fragment> fragments;
	private RadioButton userCenterRB;
	private RadioButton onlineRB;
	private RadioButton realExamRB;
	private RadioButton examRB;
	private ViewPager mViewPager;
	private Gson mGson;
	private String path;
	private CourseApi courseApi;
	private ProgressDialog dialog;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constants.SHOW_UPDATE_DIALOG:
				showUpdateDialog(path);
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		courseApi = new CourseApi(this);
		dialog = new ProgressDialog(this);
		mGson = new Gson();
		checkUpdate();
		// 自定义ActionBar
		back.setVisibility(View.INVISIBLE);
		mViewPager = UIUtil.getView(this, R.id.viewpager);
		mViewPager.setOffscreenPageLimit(4);
		examRB = UIUtil.getView(this, R.id.rb_exam);
		realExamRB = UIUtil.getView(this, R.id.rb_real_exam);
		onlineRB = UIUtil.getView(this, R.id.rb_online);
		userCenterRB = UIUtil.getView(this, R.id.rb_user_center);
		examRB.setOnCheckedChangeListener(this);
		realExamRB.setOnCheckedChangeListener(this);
		onlineRB.setOnCheckedChangeListener(this);
		userCenterRB.setOnCheckedChangeListener(this);
		HomePagerAdapter adapter = new HomePagerAdapter(
				getSupportFragmentManager());
		fragments = new ArrayList<Fragment>();
		fragments.add(new ExamFragment());
		fragments.add(new RealExamFragment());
		fragments.add(new OnlineFragment());
		fragments.add(new UserCenterFragment());
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("from", "wywj");
		params.addBodyParameter("sid", "1043717");
		params.addBodyParameter("page", "1");
		params.addBodyParameter("limit", "30");
		params.addBodyParameter("authcode", "05977463e2a4f40ac9fb5aec9429f22b");
		new ChuanKeApi(this).getCourseList(params);
		String email = AppContext.getInstance().getUsername();
		if (!EmailFormat(email)) {
			email = email + "@sina.com";
		}
		String from = "wywj";
		String key = "4dfsdpd";
		String nickName = email.substring(0, email.indexOf("@"));
		String pass = AppContext.getInstance().getUserpassword();
		String plainText = email + from + nickName + pass + key;
		String authcode = MD5Util.Md5(plainText);
		RequestParams params2 = new RequestParams();
		params2.addBodyParameter("from", from);
		params2.addBodyParameter("email", email);
		params2.addBodyParameter("nickname", nickName);
		params2.addBodyParameter("pass", pass);
		params2.addBodyParameter("authcode", authcode);
		new ChuanKeApi(this).getUserId(params2);
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("rootCourseId", "14");
		map2.put("token", AppContext.getInstance().getLoginInfo().getToken());
		courseApi.getPracticeCourseTree(map2);
		startDownloadService();

	}

	private class HomePagerAdapter extends FragmentPagerAdapter {

		public HomePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {

			return fragments.size();
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.rb_exam:
				MobclickAgent.onEvent(this, "tab1");
				mViewPager.setCurrentItem(0);
				break;
			case R.id.rb_real_exam:
				MobclickAgent.onEvent(this, "tab2");
				mViewPager.setCurrentItem(1);
				break;
			case R.id.rb_online:
				MobclickAgent.onEvent(this, "tab3");
				mViewPager.setCurrentItem(2);
				break;
			case R.id.rb_user_center:
				MobclickAgent.onEvent(this, "tab4");
				mViewPager.setCurrentItem(3);
				break;
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		examRB.setChecked(false);
		realExamRB.setChecked(false);
		userCenterRB.setChecked(false);
		onlineRB.setChecked(false);
		switch (arg0) {
		case 0:
			examRB.setChecked(true);
			actionBarTitle.setText(R.string.exam);
			break;
		case 1:
			realExamRB.setChecked(true);
			actionBarTitle.setText(R.string.real_exam);
			break;
		case 2:
			onlineRB.setChecked(true);
			actionBarTitle.setText(R.string.online);
			break;
		case 3:
			userCenterRB.setChecked(true);
			actionBarTitle.setText(R.string.user_center);
			break;
		}
	}

	/**
	 * 获取当前应用的版本号
	 * 
	 * @return 版本号信息
	 */
	public String getAppVersion() {
		PackageManager manager = getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo("com.yingshibao.cet6", 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "版本号未知";
		}
	}

	// 版本检查更新
	public void checkUpdate() {
		new Thread() {
			public void run() {
				Message msg = Message.obtain();
				try {
					URL url = new URL(Constants.VERSION_URL);
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					connection.setDoOutput(true);
					connection.setDoInput(true);
					connection.setRequestMethod("POST");
					connection.setUseCaches(false);
					connection.setInstanceFollowRedirects(true);
					connection.setRequestProperty("Content-Type",
							"application/json");
					connection.connect();
					DataOutputStream out = new DataOutputStream(
							connection.getOutputStream());
					JSONObject obj = new JSONObject();
					obj.put("level", Constants.LEVEL);
					obj.put("type", 2);
					out.writeBytes(obj.toString());
					out.flush();
					out.close();
					int code = connection.getResponseCode();
					if (code == 200) {
						InputStream in = connection.getInputStream();
						String json = StreamTools.readFromStream(in);
						ClientVersion clientVersion = mGson.fromJson(json,
								ClientVersion.class);
						Version newVersion = clientVersion.getLatest();
						path = newVersion.getUrl();
						if (newVersion.getVersion().equals(getAppVersion())) {
						} else {
							msg.what = Constants.SHOW_UPDATE_DIALOG;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					handler.sendMessage(msg);
				}

			};
		}.start();
	}

	protected void showUpdateDialog(final String path) {
		AlertDialog.Builder builder = new Builder(HomeActivity.this);
		builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
			}
		});
		builder.setTitle("更新提醒");
		builder.setPositiveButton("立刻更新", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					HttpUtils mHttpUtils = new HttpUtils();
					mHttpUtils.download(path, Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ "/cet6.apk", new RequestCallBack<File>() {

						@Override
						public void onSuccess(ResponseInfo<File> arg0) {
							HomeActivity.this.dialog.cancel();
							installApk(arg0.result);
						}

						@Override
						public void onLoading(long total, long current,
								boolean isUploading) {
							int progress = (int) (current * 100 / total);
							LogUtil.d("当前进度是：" + current);
							String str = "正在下载：" + progress + "%";
							showProgressDialog(str, progress);
						}

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							Toast.makeText(getApplicationContext(), "更新失败",
									Toast.LENGTH_SHORT).show();
						}

						private void installApk(File t) {
							Intent intent = new Intent();
							intent.setAction("android.intent.action.VIEW");
							intent.addCategory("android.intent.category.DEFAULT");
							intent.setDataAndType(Uri.fromFile(t),
									"application/vnd.android.package-archive");
							startActivity(intent);
						}
					});
				} else {
					Toast.makeText(getApplicationContext(), "sd卡不存在",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		builder.setNegativeButton("下次再说", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// System.exit(0);
			}
		});
		// builder.setCancelable(false);
		builder.setCancelable(true);
		builder.show();
	}

	private void showProgressDialog(String str, int progress) {
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setTitle("新版本下载");
		dialog.setMessage(str);
		dialog.setMax(100);
		dialog.setCancelable(false);
		dialog.setProgress(progress);
		dialog.show();
	}

	// 开启下载服务
	private void startDownloadService() {
		Intent intent = new Intent();
		intent.setClass(this, DownloadService.class);
		startService(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		count++;
		if (count == 1) {
			UIUtil.showShortToast(this, "再按一次返回键关闭程序");
		}
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				count = 0;
			}
		}, 2000);
		if (count == 2) {
			finish();
		}
		return false;
	}

	/**
	 * 邮箱验证
	 * 
	 * @param username
	 * @return
	 */
	private boolean EmailFormat(String username) {

		Pattern pattern = Pattern

				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

		Matcher mc = pattern.matcher(username);

		return mc.matches();

	}

}
