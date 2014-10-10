package com.yingshibao.cet4.api;

import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yingshibao.cet4.bean.PracticeInfo;
import com.yingshibao.cet4.bean.PracticeRecord;
import com.yingshibao.cet4.constants.Constants;
import com.yingshibao.cet4.constants.PracticeTable;
import com.yingshibao.cet4.service.AutoLoginService;
import com.yingshibao.cet4.util.LogUtil;
import com.yingshibao.db.dao.PracticeDao;

/**
 * 
 * 练习相关api
 * 
 * @author zhaoshan
 * 
 */
public class PracticeApi extends BaseApi {

	private PracticeDao practiceDao;

	public PracticeApi(Context context) {
		super(context);
		practiceDao = new PracticeDao(context);
	}

	/**
	 * 
	 * 课程习题查询
	 * 
	 * @param map
	 *            请求的参数
	 */
	public void queryPractice(final Map<String, String> map) {
		try {
			String paramsStr = mGson.toJson(map);
			byte[] data = paramsStr.toString().getBytes("UTF-8");
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(mContext, Constants.PRACTICEQUERY_URL, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							super.onSuccess(arg0, arg1, arg2);
							String json = new String(arg2);
							LogUtil.e("查询练习成功返回" + json);
							PracticeInfo practiceInfo = mGson.fromJson(json,
									PracticeInfo.class);
							if (practiceInfo != null
									&& practiceInfo.getErrorCode() == Constants.ERROR_NO) {
								LogUtil.e("解析练习成功返回" + practiceInfo.toString());
								ContentValues values = new ContentValues();
								values.put(PracticeTable.ID,
										map.get("courseId"));
								values.put(PracticeTable.PRACTICE_JSON, json);
								practiceDao.insertPractice(values);
							} else if (practiceInfo.getErrorCode() == Constants.ERROR_TOKEN_INVALID) {
								mContext.startService(new Intent(mContext,
										AutoLoginService.class));
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
	 * 
	 * 课程习题提交
	 * 
	 * @param map
	 *            请求的参数
	 */
	public void PracriceRecord(Map<String, Object> map) {
		try {
			String paramsStr = mGson.toJson(map);
			byte[] data = paramsStr.toString().getBytes("UTF-8");
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(mContext, Constants.PRACTICERECORD_URL, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							super.onSuccess(arg0, arg1, arg2);
							// 获取返回的json字符串
							if (arg2 != null) {
								String json = new String(arg2);
								PracticeRecord mPracticeRecord = mGson
										.fromJson(json, PracticeRecord.class);
								if (mPracticeRecord.getErrorCode() == 0) {
									LogUtil.e("提交成功");
								} else {
									LogUtil.e("提交失败");
								}
								LogUtil.e("成功返回" + new String(arg2));
							}

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

}
