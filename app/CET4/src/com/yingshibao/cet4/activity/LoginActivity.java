package com.yingshibao.cet4.activity;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.R;
import com.yingshibao.cet4.api.RegisterAndLoginApi;
import com.yingshibao.cet4.bean.LoginInfo;
import com.yingshibao.cet4.constants.Constants;
import com.yingshibao.cet4.constants.UserTable;
import com.yingshibao.cet4.util.UIUtil;
import com.yingshibao.db.dao.UserDao;

/**
 * 登陆Activity
 * 
 * @author malinkang
 * 
 */
public class LoginActivity extends FragmentActivity implements OnClickListener,
		LoaderCallbacks<Cursor> {
	ProgressDialog loading;
	private EditText usernameEt;
	private EditText passwordEt;
	private TextView register_tv;
	private TextView loginBtn;
	private String usernameStr;
	private String passwordStr;
	private RegisterAndLoginApi api;
	private Map<String, String> map;
	private UserDao userDao;
	public static LoginActivity instance = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		userDao = new UserDao(this);
		api = new RegisterAndLoginApi(this);
		loading = new ProgressDialog(this);
		loading.setMessage("正在登陆...");
		getSupportLoaderManager().initLoader(0, null, this);

	}

	private void initView() {
		setContentView(R.layout.activity_login);
		usernameEt = UIUtil.getView(this, R.id.et_username);
		passwordEt = UIUtil.getView(this, R.id.et_password);
		register_tv = UIUtil.getView(this, R.id.register_tv);
		register_tv.setOnClickListener(this);
		register_tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		loginBtn = UIUtil.getView(this, R.id.btn_login);
		loginBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_tv:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		case R.id.btn_login:

			usernameStr = usernameEt.getText().toString().trim();
			passwordStr = passwordEt.getText().toString();
			if (TextUtils.isEmpty(usernameStr)) {
				UIUtil.showShortToast(this, "用户名不能为空");
				return;
			}
			if (TextUtils.isEmpty(passwordStr)) {
				UIUtil.showShortToast(this, "密码不能为空");
				return;
			}
			loading.show();
			map = new LinkedHashMap<String, String>();
			map.put("requestCourseLevel", Constants.LEVEL);
			map.put("username", usernameStr);
			map.put("password", passwordStr);
			api.login(map, false, loading);
			break;
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return userDao.getCursorLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		// 2.3 和4.0区别
		if (cursor == null || cursor.getCount() == 0) {
			initView();
		} else {
			if (cursor.moveToNext()) {
				loading.dismiss();
				String json = cursor.getString(cursor
						.getColumnIndex(UserTable.LOGIN_INFO));
				String username = cursor.getString(cursor
						.getColumnIndex(UserTable.USER_NAME));
				String userpassword = cursor.getString(cursor
						.getColumnIndex(UserTable.USER_PASSWORD));
				map = new LinkedHashMap<String, String>();
				map.put("requestCourseLevel", Constants.LEVEL);
				map.put("username", username);
				map.put("password", userpassword);
				api.login(map, true, loading);
				Gson mGson = new Gson();
				LoginInfo loginInfo = mGson.fromJson(json, LoginInfo.class);
				AppContext.getInstance().setLoginInfo(loginInfo);
				AppContext.getInstance().setUsername(username);
				AppContext.getInstance().setUserpassword(userpassword);
				startActivity(new Intent(this, HomeActivity.class));
				finish();
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
