package com.yingshibao.cet4.api;

import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;

import android.content.ContentValues;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yingshibao.cet4.bean.TreeCourseInfo;
import com.yingshibao.cet4.constants.Constants;
import com.yingshibao.cet4.constants.ParentCourseTable;
import com.yingshibao.cet4.util.LogUtil;
import com.yingshibao.db.dao.ParentCourseDao;

public class CourseApi extends BaseApi {

	private ParentCourseDao parentCourseDao;

	public CourseApi(Context context) {
		super(context);
		parentCourseDao = new ParentCourseDao(context);
	}

	/**
	 * 获取根课程信息
	 * 
	 * @param map
	 *            请求的参数
	 */
	public void getRootCourses4Level(Map<String, String> map) {
		try {
			String paramsStr = mGson.toJson(map);
			byte[] data = paramsStr.toString().getBytes("UTF-8");
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(mContext, Constants.ROOTCOURSES4LEVEL_URL, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							super.onSuccess(arg0, arg1, arg2);
						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							super.onFailure(arg0, arg1, arg2, arg3);
							LogUtil.e("失败返回" + arg3.toString());
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(e.toString());
		}

	}

	public void getPracticeCourseTree(Map<String, String> map) {
		try {
			String paramsStr = mGson.toJson(map);
			byte[] data = paramsStr.toString().getBytes("UTF-8");
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(mContext, Constants.PRACTICECOURSETREE_URL, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							TreeCourseInfo treeCourseInfo = mGson.fromJson(
									content, TreeCourseInfo.class);
							if (treeCourseInfo != null
									&& treeCourseInfo.getErrorCode() == Constants.ERROR_NO) {
								ContentValues values = new ContentValues();
								values.put(ParentCourseTable.ID, treeCourseInfo
										.getCourseJsons().get(0).getPid());
								values.put(ParentCourseTable.COURSE_JSON,
										content);
								parentCourseDao.insertCourse(values);
							} else if (treeCourseInfo.getErrorCode() == Constants.ERROR_TOKEN_INVALID) {
								// 过期
							}
						}

						@Override
						public void onFailure(int statusCode, Throwable error,
								String content) {

						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(e.toString());
		}
	}

	/**
	 * 
	 * 发送课程评价信息
	 * 
	 * @param map
	 */
	public void sendCourseEvaluation(Map<String, String> map) {
		try {
			String paramStr = mGson.toJson(map);
			byte[] data = paramStr.toString().getBytes("UTF-8");
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(mContext, Constants.COURSECOMMENT_URL, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							super.onSuccess(arg0, arg1, arg2);
							new String(arg2);

							LogUtil.e("成功返回" + new String(arg2));
						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							super.onFailure(arg0, arg1, arg2, arg3);
							LogUtil.e("失败返回" + new String(arg2));
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(e.toString());
		}
	}

}
