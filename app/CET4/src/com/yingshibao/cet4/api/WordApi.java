package com.yingshibao.cet4.api;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.bean.UnKnownWords;
import com.yingshibao.cet4.bean.Word;
import com.yingshibao.cet4.bean.WordGroupInfo;
import com.yingshibao.cet4.bean.WordState;
import com.yingshibao.cet4.bean.Words4Group;
import com.yingshibao.cet4.constants.Constants;
import com.yingshibao.cet4.constants.UnKnownWordsTable;
import com.yingshibao.cet4.constants.WordGroupTable;
import com.yingshibao.cet4.constants.WordInfoTable;
import com.yingshibao.cet4.service.AutoLoginService;
import com.yingshibao.cet4.util.LogUtil;
import com.yingshibao.cet4.util.StreamTools;
import com.yingshibao.db.dao.UnknowWordsDao;
import com.yingshibao.db.dao.WordGroupDao;
import com.yingshibao.db.dao.WordInfoDao;

/**
 * 
 * 单词相关请求
 * 
 * @author zhaoshan
 * 
 */
public class WordApi extends BaseApi {

	private WordInfoDao mWordInfoDao;
	private WordGroupDao mWordGroupDao;
	private UnknowWordsDao mUnknowWordsDao;

	public WordApi(Context context) {
		super(context);
		mWordInfoDao = new WordInfoDao(context);
		mWordGroupDao = new WordGroupDao(context);

		mUnknowWordsDao = new UnknowWordsDao(context);

	}

	/**
	 * 获取单词分组
	 * 
	 * @param map
	 *            请求的参数
	 */
	public void getWordGroup(final Map<String, String> map) {
		try {
			String paramsStr = mGson.toJson(map);
			byte[] data = paramsStr.toString().getBytes("UTF-8");
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(mContext, Constants.WORDGROUPS_URL, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							super.onSuccess(arg0, arg1, arg2);
							// 获取返回的json字符串
							String json = new String(arg2);
							LogUtil.e("获取单词分组成功" + json);
							WordGroupInfo mWordGroupInfo = mGson.fromJson(json,
									WordGroupInfo.class);
							if (mWordGroupInfo != null
									&& mWordGroupInfo.getErrorCode() == Constants.ERROR_NO) {
								LogUtil.e("解析单词分组成功返回"
										+ mWordGroupInfo.toString());
								ContentValues values = new ContentValues();
								values.put(WordGroupTable.ID,
										map.get("wordCourseId"));
								values.put(WordGroupTable.WORD_GROUP_JSON, json);
								mWordGroupDao.insertWordGroup(values);
							} else if (mWordGroupInfo.getErrorCode() == Constants.ERROR_TOKEN_INVALID) {
								//
								mContext.startService(new Intent(mContext,
										AutoLoginService.class));
							}

						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							super.onFailure(arg0, arg1, arg2, arg3);
							LogUtil.e("获取单词分组失败返回" + arg3.toString());
						}

						@Override
						public void onFinish() {
							super.onFinish();
							LogUtil.e("onFinish");
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e("失败" + e.toString());
		}

	}

	/**
	 * 获取分组内单词
	 * 
	 * @param map
	 *            请求的参数
	 */
	public void getwords4group(final Map<String, String> map) {
		try {
			String paramsStr = mGson.toJson(map);
			byte[] data = paramsStr.toString().getBytes("UTF-8");
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(mContext, Constants.WORDGROUDP_URL, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							super.onSuccess(arg0, arg1, arg2);
							// 获取返回的json字符串
							String json = new String(arg2);

							Words4Group mWord4GroupInfo = mGson.fromJson(json,
									Words4Group.class);

							List<Word> wordList = mWord4GroupInfo
									.getWordJsons();
							if (mWord4GroupInfo != null
									&& mWord4GroupInfo.getErrorCode() == Constants.ERROR_NO) {
								LogUtil.e("解析单词分组成功返回"
										+ mWord4GroupInfo.toString());
								ContentValues values = new ContentValues();
								values.put(WordGroupTable.ID,
										map.get("groupId"));
								values.put(WordInfoTable.WORD_INFO_JSON, json);
								mWordInfoDao.insertWordInfo(values);
							} else if (mWord4GroupInfo.getErrorCode() == Constants.ERROR_TOKEN_INVALID) {
								// Toast.makeText(mContext, "请求失败",
								// Toast.LENGTH_SHORT).show();
								mContext.startService(new Intent(mContext,
										AutoLoginService.class));
							}

							LogUtil.e("成功返回" + new String(arg2));
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

	/**
	 * 修改单词状态
	 * 
	 * @param map
	 *            请求的参数
	 */
	public void getWordState(Map<String, Object> map) {
		try {
			String paramsStr = mGson.toJson(map);
			byte[] data = paramsStr.toString().getBytes("UTF-8");
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(mContext, Constants.UNKNOWNFLAG4WORD_URL, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							super.onSuccess(arg0, arg1, arg2);
							// 获取返回的json字符串
							String json = new String(arg2);

							WordState wordStateInfo = mGson.fromJson(json,
									WordState.class);
							if (wordStateInfo.getErrorCode() == Constants.ERROR_NO) {

							} else if (wordStateInfo.getErrorCode() == Constants.ERROR_TOKEN_INVALID) {
								mContext.startService(new Intent(mContext,
										AutoLoginService.class));
							}

							LogUtil.e("成功返回" + new String(arg2));
						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							super.onFailure(arg0, arg1, arg2, arg3);
							LogUtil.e("失败返回" + new String(arg3.toString()));
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(e.toString());
		}
	}

	/**
	 * 
	 * 生词本
	 * 
	 * @param map
	 *            请求参数
	 */
	public void getUnknownwords() {
		new Thread() {
			public void run() {
				try {
					URL url = new URL(Constants.UNKNOWNWORDS_URL);
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
					obj.put("token", AppContext.getInstance().getLoginInfo()
							.getToken());
					obj.put("pageSize", 100);
					obj.put("level", 1);
					obj.put("pageIndex", 0);
					out.writeBytes(obj.toString());
					out.flush();
					out.close();
					int code = connection.getResponseCode();
					if (code == 200) {
						InputStream in = connection.getInputStream();
						String json = StreamTools.readFromStream(in);
						UnKnownWords mUnknownWordsInfo = mGson.fromJson(json,
								UnKnownWords.class);
						if (mUnknownWordsInfo != null
								&& mUnknownWordsInfo.getErrorCode() == Constants.ERROR_NO) {
							ContentValues values = new ContentValues();
							values.put(UnKnownWordsTable.ID, "1");
							values.put(UnKnownWordsTable.UNKNOWN_WORDS_JSON,
									json);
							mUnknowWordsDao.insertUnKnownsWordsInfo(values);

						} else if (mUnknownWordsInfo.getErrorCode() == Constants.ERROR_TOKEN_INVALID) {
							// Toast.makeText(mContext, "请求失败",
							// Toast.LENGTH_SHORT)
							// .show();
							mContext.startService(new Intent(mContext,
									AutoLoginService.class));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
}
