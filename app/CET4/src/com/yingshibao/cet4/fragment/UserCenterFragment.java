package com.yingshibao.cet4.fragment;

import java.util.HashMap;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.R;
import com.yingshibao.cet4.activity.CoupleBackActivity;
import com.yingshibao.cet4.activity.DownloadManageActivity;
import com.yingshibao.cet4.activity.LoginActivity;
import com.yingshibao.db.dao.ChuanKeUserDao;
import com.yingshibao.db.dao.UserDao;

/**
 * 个人中心Fragment
 * 
 * @author malinkang
 * 
 */
public class UserCenterFragment extends Fragment implements OnClickListener {
	private TextView username;
	private LinearLayout user_couple_back;
	private LinearLayout download_manage;
	private UserDao userDao;
	private ChuanKeUserDao mChuanKeUserDao;
	private LinearLayout username_lt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_center, container,
				false);
		userDao = new UserDao(getActivity());
		mChuanKeUserDao = new ChuanKeUserDao(getActivity());
		initView(view);
		return view;
	}

	private void initView(View view) {
		username_lt = (LinearLayout) view.findViewById(R.id.username_lt);
		username_lt.setOnClickListener(this);
		username = (TextView) view.findViewById(R.id.username);
		username.setText(AppContext.getInstance().getUsername());
		user_couple_back = (LinearLayout) view
				.findViewById(R.id.user_couple_back);
		user_couple_back.setOnClickListener(this);
		download_manage = (LinearLayout) view
				.findViewById(R.id.download_manage);
		download_manage.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.username_lt:

			AlertDialog.Builder builder = new Builder(getActivity());
			builder.setMessage("确认退出吗？");
			builder.setTitle("提示");
			builder.setNegativeButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							HashMap<String, String> map = new HashMap<String, String>();

							MobclickAgent.onEvent(getActivity(), "用户退出登陆", map);
							userDao.delete();
							mChuanKeUserDao.delete();
							startActivity(new Intent(getActivity(),
									LoginActivity.class));
							getActivity().finish();
						}
					});
			builder.setNeutralButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			builder.create().show();
			break;
		case R.id.user_couple_back:
			intent = new Intent(getActivity(), CoupleBackActivity.class);
			MobclickAgent.onEvent(getActivity(), "点击进入用户反馈");
			startActivity(intent);
			break;
		case R.id.download_manage:
			HashMap<String, String> map = new HashMap<String, String>();
			MobclickAgent.onEvent(getActivity(), "点击进入下载管理", map);
			startActivity(new Intent(getActivity(),
					DownloadManageActivity.class));
			break;
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}
}
