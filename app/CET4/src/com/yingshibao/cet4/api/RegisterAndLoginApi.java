package com.yingshibao.cet4.api;

import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.bean.Course;
import com.yingshibao.cet4.bean.LoginInfo;
import com.yingshibao.cet4.constants.Constants;
import com.yingshibao.cet4.constants.UserTable;
import com.yingshibao.cet4.service.AutoLoginService;
import com.yingshibao.cet4.util.DESUtil;
import com.yingshibao.cet4.util.LogUtil;
import com.yingshibao.db.dao.UserDao;

/**
 * 注册和登陆的API
 * 
 * @author malinkang 2014-2-28 下午2:31:06
 * 
 */

public class RegisterAndLoginApi extends BaseApi {
	private UserDao userDao;

	public RegisterAndLoginApi(Context context) {
		super(context);
		userDao = new UserDao(context);
	}

	/**
	 * 
	 * @param map
	 * @param flag
	 *            是否是自动登陆
	 */
	public void login(final Map<String, String> map, final boolean flag,
			final ProgressDialog loading) {
		try {
			String paramsStr = mGson.toJson(map);
			// 加密
			byte[] data = DESUtil.encryptDESBytes(paramsStr, Constants.DES_KEY);
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(mContext, Constants.LOGIN_URL, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							super.onSuccess(arg0, arg1, arg2);

							String json = DESUtil.decryptDESBytes(arg2,
									Constants.DES_KEY);
							LogUtil.e("登陆成功返回" + json);
							LoginInfo loginInfo = mGson.fromJson(json,
									LoginInfo.class);
							if (loginInfo != null
									&& loginInfo.getErrorCode() == Constants.ERROR_NO) {
								LogUtil.e("登陆解析成功返回" + loginInfo.toString());
								if (flag) {

									AppContext.getInstance().setLoginInfo(
											loginInfo);
								} else {
									ContentValues loginInfoValues = new ContentValues();
									loginInfoValues.put(UserTable.USER_NAME,
											map.get("username"));
									loginInfoValues.put(
											UserTable.USER_PASSWORD,
											map.get("password"));
									loginInfoValues.put(UserTable.LOGIN_INFO,
											json);
									userDao.insertUser(loginInfoValues);
								}

							} else if (loginInfo != null
									&& loginInfo.getErrorCode() == Constants.ERROR_TOKEN_INVALID) {
								mContext.startService(new Intent(mContext,
										AutoLoginService.class));
							} else if (loginInfo != null
									&& loginInfo.getErrorCode() == Constants.ERROR_USERNAME_PASSWORD_SAME) {
								loading.dismiss();
								Toast.makeText(mContext, "用户名密码不能一致",
										Toast.LENGTH_SHORT).show();
							} else if (loginInfo.getErrorCode() == Constants.ERROR_USERNAME_INVALID
									|| loginInfo.getErrorCode() == Constants.ERROR_MAIL_INVALID) {
								loading.dismiss();
								Toast.makeText(mContext, "无效的邮箱账号",
										Toast.LENGTH_SHORT).show();
							} else if (loginInfo != null
									&& loginInfo.getErrorCode() == Constants.ERROR_PASSWORD_INVALID) {
								loading.dismiss();
								Toast.makeText(mContext, "密码无效",
										Toast.LENGTH_SHORT).show();
							} else {
								loading.dismiss();
								Toast.makeText(mContext, "登录失败，请核对邮箱、密码后重新登录",
										Toast.LENGTH_SHORT).show();
							}

						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							super.onFailure(arg0, arg1, arg2, arg3);
							LogUtil.e("登陆失败返回" + arg3.toString());
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(e.toString());
		}

	}

	/**
	 * 注册
	 * 
	 * @param map
	 */
	public void register(Map<String, String> map) {
		try {
			String paramsStr = mGson.toJson(map);
			// 加密
			byte[] data = DESUtil.encryptDESBytes(paramsStr, Constants.DES_KEY);
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(mContext, Constants.REGISTER_URL, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							super.onSuccess(arg0, arg1, arg2);
							// 字符串解密
							String json = DESUtil.decryptDESBytes(arg2,
									Constants.DES_KEY);
							// 解析返回的json
							LoginInfo loginInfo = mGson.fromJson(json,
									LoginInfo.class);
							if (loginInfo.getErrorCode() == 0) {
								// TODO 向数据库中插入用户相关Token
								List<Course> courseList = loginInfo
										.getCourseJsons();
								for (int i = 0; i < courseList.size(); i++) {
								}
							} else {
								Toast.makeText(mContext, "注册失败",
										Toast.LENGTH_SHORT).show();
							}

							LogUtil.e("注册成功返回" + json);
						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							super.onFailure(arg0, arg1, arg2, arg3);
							LogUtil.e("注册失败返回" + new String(arg2));
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(e.toString());
		}

	}

	public void setPhoneNumber(Map<String, String> map) {
		try {
			String paramsStr = mGson.toJson(map);
			byte[] data = paramsStr.toString().getBytes("UTF-8");
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(mContext, Constants.SETPHONEGRADE, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							super.onSuccess(arg0, arg1, arg2);
							LogUtil.e("添加手机号成功" + new String(arg2));
						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							super.onFailure(arg0, arg1, arg2, arg3);
							LogUtil.e("" + new String(arg2));
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(e.toString());
		}

	}

	/**
	 * 获取学院信息
	 */
	public void getColleage() {
		try {
			String paramsStr = "{\"token\":\"\"}";
			byte[] data = paramsStr.toString().getBytes("UTF-8");
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(mContext, Constants.ACQUIRE_COLLEGE, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers,
								String content) {
							PreferenceManager
									.getDefaultSharedPreferences(mContext)
									.edit().putString("colleges", content)
									.commit();

						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							super.onFailure(arg0, arg1, arg2, arg3);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(e.toString());
		}
	}
}
