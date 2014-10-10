package com.yingshibao.cet4.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.content.ContentProvider;
import com.yingshibao.AppContext;
import com.yingshibao.api.RegisterAndLoginApi;
import com.yingshibao.bean.User;
import com.yingshibao.cet4.R;
import com.yingshibao.db.UserTable;
import com.yingshibao.util.UIUtil;

/**
 * 登陆页
 * 
 * @author zhaoshan
 * 
 */
public class LoginActivity extends FragmentActivity implements
		LoaderCallbacks<Cursor> {

	@InjectView(R.id.et_username)
	EditText usernameEt;
	@InjectView(R.id.et_password)
	EditText passwordEt;
	@InjectView(R.id.register_tv)
	TextView register_tv;
	@InjectView(R.id.btn_login)
	TextView btn_login;
	private String usernameStr;
	private String passwordStr;
	private User info;
	private RegisterAndLoginApi api;
	private ProgressDialog loading;
	public static LoginActivity instance = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ButterKnife.inject(this);
		instance = this;
		api = new RegisterAndLoginApi(this);
		loading = new ProgressDialog(this);
		loading.setMessage("正在登陆。。。");
	}

	@OnClick(R.id.register_tv)
	public void register() {
		startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
	}

	@OnClick(R.id.btn_login)
	public void login() {
		usernameStr = usernameEt.getText().toString().trim();
		passwordStr = passwordEt.getText().toString().trim();
		if (TextUtils.isEmpty(usernameStr)) {
			UIUtil.showShortToast("用户名不能为空");
			return;
		}
		if (TextUtils.isEmpty(passwordStr)) {
			UIUtil.showShortToast("密码不能为空");
			return;
		}
		loading.show();
		info = new User();
		info.setUserName(usernameStr);
		info.setPassword(passwordStr);
		api.login(info, loading);
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.reset(this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this, ContentProvider.createUri(User.class,
				null), null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor == null || cursor.getCount() == 0) {
		} else {
			if (cursor.moveToNext()) {
				loading.dismiss();
				String college = cursor.getString(cursor
						.getColumnIndex(UserTable.COLUMN_COLLEGE));
				String username = cursor.getString(cursor
						.getColumnIndex(UserTable.COLUMN_USERNAME));
				String password = cursor.getString(cursor
						.getColumnIndex(UserTable.COLUMN_PASSWORD));
				String token = cursor.getString(cursor
						.getColumnIndex(UserTable.COLUMN_TOKEN));
				info = new User();
				info.setUserName(username);
				info.setPassword(password);
				info.setToken(token);
				AppContext.getInstance().setUserInfo(info);
				startActivity(new Intent(this, PaymentActivity.class));
				finish();
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}
}
