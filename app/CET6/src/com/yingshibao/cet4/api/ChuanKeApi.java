package com.yingshibao.cet4.api;

import java.util.Map;

import org.apache.http.Header;

import android.content.ContentValues;
import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.bean.ChuanKeCourseBean;
import com.yingshibao.cet4.bean.ChuanKeUser;
import com.yingshibao.cet4.bean.UserData;
import com.yingshibao.cet4.constants.ChuanKeCourseTable;
import com.yingshibao.cet4.constants.ChuanKeUserTable;
import com.yingshibao.cet4.constants.Constants;
import com.yingshibao.cet4.util.LogUtil;
import com.yingshibao.db.dao.ChuanKeUserDao;
import com.yingshibao.db.dao.ChuankeDao;

/**
 * 版本先相关请求
 * 
 * @author zhaoshan
 * 
 */
public class ChuanKeApi extends BaseApi {

	private ChuankeDao dao;
	private ChuanKeUserDao mChuanKeUserDao;

	public ChuanKeApi(Context context) {
		super(context);
		dao = new ChuankeDao(context);
		mChuanKeUserDao = new ChuanKeUserDao(context);
	}

	/**
	 * 
	 * 获取最新版本信息
	 * 
	 * @param map
	 */
	public void getCourseList(com.lidroid.xutils.http.RequestParams params) {
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, Constants.GET_COURSELIST_URL,
				params, new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						LogUtil.w("获取传课网成功" + responseInfo.result);
						dao.delete();
						ContentValues values = new ContentValues();
						values.put(ChuanKeCourseTable.COURSE_INFO,
								responseInfo.result);
						dao.insert(values);
					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});
	}

	/**
	 * 
	 * 获取最新版本信息访问 -403 ？bug
	 * 
	 * @param map
	 */
	@Deprecated
	public void getCourseList(RequestParams params) {
		client.post(
				"http://www.chuanke.com/?mod=interface&act=seller&do=courseList",
				params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1, arg2);
						String json = new String(arg2);
						LogUtil.e("获取传课网成功" + json);
						ChuanKeCourseBean chuankeCourse = mGson.fromJson(json,
								ChuanKeCourseBean.class);
						if (chuankeCourse != null) {
							LogUtil.e("解析传课网返回数据成功" + chuankeCourse.toString());
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1, arg2, arg3);
						LogUtil.e("解析传课网返回数据失败" + arg3.toString());
					}
				});
	}

	/**
	 * 获取用户的uid
	 */

	public void getUserId(com.lidroid.xutils.http.RequestParams params) {
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, Constants.GET_UID_URL, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {

						@SuppressWarnings("unchecked")
						Map<String, Object> map = mGson.fromJson(arg0.result,
								Map.class);
						for (Map.Entry<String, Object> entry : map.entrySet()) {
							String key = entry.getKey().toString();
							String value = entry.getValue().toString();
							if (key.equals("code")) {
								if (value.equals("200")) {
									ChuanKeUser chuanKeUser = mGson.fromJson(
											arg0.result, ChuanKeUser.class);
									UserData userData = chuanKeUser.getData();
									mChuanKeUserDao.delete();
									ContentValues values = new ContentValues();
									values.put(ChuanKeUserTable.USER_NAME,
											AppContext.getInstance()
													.getUsername());
									values.put(ChuanKeUserTable.UID,
											userData.getUid());
									mChuanKeUserDao.insert(values);
								}
							} else {
								ContentValues values = new ContentValues();
								values.put(ChuanKeUserTable.USER_NAME,
										AppContext.getInstance().getUsername());
								values.put(ChuanKeUserTable.UID,
										value.toString());
								mChuanKeUserDao.insert(values);
							}

						}

					}
				});
	}

	/**
	 * 免费课程支付
	 * 
	 * @param params
	 */

	public void freeCourse(com.lidroid.xutils.http.RequestParams params,
			RequestCallBack<String> mRequestCallBack) {
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, Constants.FREE_COURSE, params,
				mRequestCallBack);

	}

	/**
	 * 付费课程支付
	 * 
	 * @param params
	 */
	public void PayCourse(com.lidroid.xutils.http.RequestParams params,
			RequestCallBack<String> mRequestCallBack) {
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, Constants.PAY_COURSE, params,
				mRequestCallBack);
	}
}
