package com.yingshibao.api;

import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yingshibao.YingShiBaoException;
import com.yingshibao.bean.College;
import com.yingshibao.bean.Colleges;
import com.yingshibao.bean.User;
import com.yingshibao.util.Constants;
import com.yingshibao.util.LogUtil;

public class RegisterAndLoginApi extends BaseApi {

	public RegisterAndLoginApi(Context context) {
		super(context);
	}

	public void login(final User info, final ProgressDialog loading) {
		try {
			String paramsStr = mGson.toJson(info);
			byte[] data = paramsStr.toString().getBytes("UTF-8");
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(context, Constants.LOGIN_URL, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							super.onSuccess(arg0, arg1, arg2);
							String json = new String(arg2);
							User userInfo = mGson.fromJson(json, User.class);
							if (userInfo.getErrorCode() == Constants.ERROR_NO
									&& userInfo != null) {
								userInfo.setUserName(info.getUserName());
								userInfo.setPassword(info.getPassword());
								userInfo.setCollege_json(mGson.toJson(userInfo
										.getCollege()));
								userInfo.save();
							} else {
								try {
									throw new YingShiBaoException(userInfo);
								} catch (YingShiBaoException e) {
									e.showErrorToast();
								}
							}

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

	/**
	 * 获取学院信息
	 */
	public void getColleage() {
		try {
			String paramsStr = "{\"token\":\"\"}";
			byte[] data = paramsStr.toString().getBytes("UTF-8");
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(context, Constants.ACQUIRE_COLLEGE, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers,
								String content) {
							String json = new String(content);
							Colleges mColleages = mGson.fromJson(json,
									Colleges.class);
							if (mColleages.getErrorCode() == 0) {
								mColleages.setColleges_json(mGson
										.toJson(mColleages.getCollegeList()));
								mColleages.save();
							} else {
								try {
									throw new YingShiBaoException(mColleages);
								} catch (YingShiBaoException e) {
									e.showErrorToast();
								}
							}

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

	public void register(final Map<String, String> map, final College college) {

		try {
			String paramsStr = mGson.toJson(map);
			byte[] data = paramsStr.toString().getBytes("UTF-8");
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(context, Constants.REGISTER_URL, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@SuppressLint("UseValueOf")
						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							super.onSuccess(arg0, arg1, arg2);
							String json = new String(arg2);
							User userInfo = mGson.fromJson(json, User.class);
							if (userInfo.getErrorCode() == 0) {
								userInfo.setUserName(map.get("username")
										.toString());
								userInfo.setPassword(map.get("password")
										.toString());
								userInfo.setNickname("");
								userInfo.setPhone(map.get("phone").toString());
								userInfo.setGrade(Integer.parseInt(map
										.get("grade")));
								userInfo.setCollege(college);
								userInfo.setCollege_json(mGson.toJson(college));
								userInfo.save();
							} else {
								try {
									throw new YingShiBaoException(userInfo);
								} catch (YingShiBaoException e) {
									e.showErrorToast();
								}
							}
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
