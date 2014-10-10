package com.yingshibao.cet4.activity;

import java.util.HashMap;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.api.ChuanKeApi;
import com.yingshibao.cet4.bean.DataBean;
import com.yingshibao.cet4.bean.PayBean;
import com.yingshibao.cet4.constants.ChuanKeUserTable;
import com.yingshibao.cet4.util.LogUtil;
import com.yingshibao.cet4.util.MD5Util;
import com.yingshibao.cet6.R;
import com.yingshibao.db.dao.ChuanKeUserDao;

/**
 * 在线课程
 * 
 * @author zhaoshan
 * 
 */
public class OnlineCoursesActivity extends BaseActivity implements
		LoaderCallbacks<Cursor> {

	private ChuanKeApi mChuankeApi;
	private Gson mGson;
	private String uid;
	private DataBean mDataBean;
	private ImageView photo_iv;
	private TextView coursename_tv;
	private TextView teacher_tv;
	private TextView quality_tv;
	private TextView studentnumber_tv;
	private WebView course_wv;
	private ChuanKeUserDao dao;
	private Button unpay_btn;
	private TextView price_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_online_courses);
		mChuankeApi = new ChuanKeApi(this);
		mDataBean = (DataBean) getIntent().getSerializableExtra("mDataBean");
		mGson = new Gson();
		dao = new ChuanKeUserDao(this);

		getSupportLoaderManager().initLoader(0, null, this);

	}

	// 初始化界面
	public void initView() {
		// 封装支付的参数
		coursename_tv = (TextView) findViewById(R.id.coursename_tv);
		teacher_tv = (TextView) findViewById(R.id.teacher_tv);
		quality_tv = (TextView) findViewById(R.id.quality_tv);
		studentnumber_tv = (TextView) findViewById(R.id.studentnumber_tv);
		course_wv = (WebView) findViewById(R.id.course_wv);
		photo_iv = (ImageView) findViewById(R.id.photo_iv);
		course_wv.getSettings().setDefaultTextEncodingName("GBK");
		BitmapUtils utils = new BitmapUtils(this);
		utils.display(photo_iv, mDataBean.getPhotoURL());
		price_tv = (TextView) findViewById(R.id.price_tv);
		unpay_btn = (Button) findViewById(R.id.unpay_btn);
		unpay_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 免费课程
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("course_name", mDataBean.getCourseName());
				MobclickAgent
						.onEvent(OnlineCoursesActivity.this, "报名在线课程", map);
				if (mDataBean.getCost().equals("0")) {
					String plainText2 = "wywj1043717" + mDataBean.getCourseID()
							+ uid + "4dfsdpd";
					String authcode2 = MD5Util.Md5(plainText2);
					final RequestParams freeparams = new RequestParams();
					freeparams.addBodyParameter("from", "wywj");
					freeparams.addBodyParameter("sid", "1043717");
					freeparams.addBodyParameter("courseId",
							mDataBean.getCourseID());
					freeparams.addBodyParameter("uid", uid);
					freeparams.addBodyParameter("sig", authcode2);
					RequestCallBack<String> mRequestCallBack = new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							Toast.makeText(OnlineCoursesActivity.this, "报名失败",
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							PayBean payBean = mGson.fromJson(arg0.result,
									PayBean.class);
							if (payBean.getCode().equals("200")) {
								Toast.makeText(OnlineCoursesActivity.this,
										"报名成功", Toast.LENGTH_SHORT).show();
								// unpay_btn.setClickable(false);
								unpay_btn.setText("已报名");
							} else {
								Toast.makeText(OnlineCoursesActivity.this,
										"已报名", Toast.LENGTH_SHORT).show();
								unpay_btn.setText("已报名");
							}

						}

					};
					mChuankeApi.freeCourse(freeparams, mRequestCallBack);
				} else {
					// 收费课程
					String plainText1 = "wywj1043717" + mDataBean.getCourseID()
							+ uid + "http://www.yingshibao.com" + "4dfsdpd";
					String authcode1 = MD5Util.Md5(plainText1);
					final RequestParams payparams = new RequestParams();
					payparams.addBodyParameter("from", "wywj");
					payparams.addBodyParameter("sid", "1043717");
					payparams.addBodyParameter("courseid",
							mDataBean.getCourseID());
					payparams.addBodyParameter("uid", uid);
					payparams.addBodyParameter("returl",
							"http://www.yingshibao.com");
					payparams.addBodyParameter("authcode", authcode1);
					RequestCallBack<String> mRequestCallBack = new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							Toast.makeText(OnlineCoursesActivity.this, "支付失败",
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							LogUtil.e(arg0.result);
							Intent intent = new Intent(
									OnlineCoursesActivity.this,
									PayActivity.class);
							intent.putExtra("html", arg0.result);
							intent.putExtra("CourseName",
									mDataBean.getCourseName());
							startActivity(intent);
						}

					};
					mChuankeApi.PayCourse(payparams, mRequestCallBack);
				}
			}
		});
	}

	// 初始化数据
	public void initData() {
		String teacherNeme = mDataBean.getChapterList().get(0).getSectionList()
				.get(0).getTeacherName();
		coursename_tv.setText(mDataBean.getCourseName());
		teacher_tv.setText("授课教师：" + teacherNeme);
		quality_tv.setText("课程数量：" + mDataBean.getClassHour());
		int number = mDataBean.getStudentNumber().length();
		String studentNumber = "已有" + mDataBean.getStudentNumber() + "位同学";
		SpannableString spannableString = new SpannableString(studentNumber);
		spannableString.setSpan(new ForegroundColorSpan(Color.RED), 2,
				2 + number, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		studentnumber_tv.setText(spannableString);
		course_wv.loadDataWithBaseURL(null, mDataBean.getBrief(), "text/html",
				"utf-8", null);
		Float price = Float.parseFloat(mDataBean.getCost()) / 100;
		String cost = "价格：" + price.toString();
		if (mDataBean.getCost().equals("0")) {
			SpannableString spannableString2 = new SpannableString(cost);
			spannableString2.setSpan(new ForegroundColorSpan(Color.RED), 3, 4,
					Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
			price_tv.setText(spannableString2);
		}
		SpannableString spannableString1 = new SpannableString(cost);
		spannableString1.setSpan(new ForegroundColorSpan(Color.RED), 3,
				3 + mDataBean.getCost().length(),
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		price_tv.setText(spannableString1);
		actionBarTitle.setText(mDataBean.getCourseName());
	}

	@Override
	public android.support.v4.content.Loader<Cursor> onCreateLoader(int arg0,
			Bundle arg1) {
		return dao.getCursorLoader(this);
	}

	@Override
	public void onLoadFinished(android.support.v4.content.Loader<Cursor> arg0,
			Cursor arg1) {
		if (arg1 != null && arg1.moveToNext()) {
			String kk = arg1.getString(arg1
					.getColumnIndex(ChuanKeUserTable.UID));
			if (kk != null) {
				uid = kk;
				initView();
				initData();
			}
		}
	}

	@Override
	public void onLoaderReset(android.support.v4.content.Loader<Cursor> arg0) {
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SplashScreen"); // 统计页面
		MobclickAgent.onResume(this); // 统计时长
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SplashScreen"); // 保证 onPageEnd 在onPause
													// 之前调用,因为 onPause 中会保存信息
		MobclickAgent.onPause(this);
	}

}
