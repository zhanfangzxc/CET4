package com.yingshibao.cet4.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yingshibao.cet4.AppConfig;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.AppException;
import com.yingshibao.cet4.R;
import com.yingshibao.cet4.bean.LoginInfo;
import com.yingshibao.cet4.bean.School;
import com.yingshibao.cet4.bean.SchoolGroup;
import com.yingshibao.cet4.bean.SchoolInfo;
import com.yingshibao.cet4.constants.Constants;
import com.yingshibao.cet4.constants.UserTable;
import com.yingshibao.cet4.service.AddPhoneNumberService;
import com.yingshibao.cet4.util.DESUtil;
import com.yingshibao.cet4.util.ReadUtil;
import com.yingshibao.db.dao.UserDao;

public class RegisterActivity extends BaseActivity implements OnClickListener {
	private EditText username_text;
	private EditText password_text;
	private AutoCompleteTextView school_name_text;
	private String username;
	private String password;
	private String school;
	private String phoneNumberStr;
	private int schoolId;
	private Button register_bt;
	private ProgressDialog dialog;
	private SchoolGroup schoolGroup;
	private ArrayList<School> schools;
	private String json;
	private String loginJson;
	private String token;
	private UserDao userDao;
	private ArrayList<String> schoolNames = new ArrayList<String>();
	private TextView grade;
	private EditText phoneNumber;
	private String gradeStr;
	private TextView gradeTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registe);
		actionBarTitle.setText("注册");
		userDao = new UserDao(this);
		this.initView();
		this.initData();
	}

	private void initView() {
		username_text = (EditText) findViewById(R.id.username_text);
		password_text = (EditText) findViewById(R.id.password_text);
		school_name_text = (AutoCompleteTextView) findViewById(R.id.school_name_text);
		phoneNumber = (EditText) findViewById(R.id.et_phone_number);
		grade = (TextView) findViewById(R.id.et_grade);
		gradeTv = (TextView) findViewById(R.id.tv_grade);
		gradeTv.setOnClickListener(this);
		grade.setOnClickListener(this);
		register_bt = (Button) findViewById(R.id.register_bt);
		register_bt.setOnClickListener(this);
	}

	private void initData() {
		json = ReadUtil.readFile(this, "wwyj_offline/acquirecollege");
		Gson gson = new Gson();
		schoolGroup = gson.fromJson(json, SchoolGroup.class);
		schools = schoolGroup.getCollegeList();
		for (School school : schools) {
			schoolNames.add(school.getName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, schoolNames);
		school_name_text.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.register_bt:
			this.username = this.username_text.getText().toString().trim();
			this.password = this.password_text.getText().toString();
			this.school = this.school_name_text.getText().toString();
			gradeStr = grade.getText().toString();
			this.schoolId = selectSchool();
			phoneNumberStr = phoneNumber.getText().toString();
			//
			if (null == this.username || ("").equals(this.username)) {
				this.username_text.requestFocus();
				Toast.makeText(getApplicationContext(),
						R.string.username_promt, Toast.LENGTH_SHORT).show();
				return;
			}
			//
			if (!EmailFormat(username) || username.length() > 31) {// 通过正则表达式检验账号是否是邮箱
				Toast.makeText(this, "账号不为邮箱或格式不正确", Toast.LENGTH_LONG).show();
				return;
			}
			if (null == this.password || ("").equals(this.password)) {
				this.password_text.requestFocus();
				Toast.makeText(getApplicationContext(),
						R.string.reg_password_hint, Toast.LENGTH_SHORT).show();
				return;
			}
			if (password.length() < 3 || password.length() > 32) {
				Toast.makeText(this, "密码长度应在3～32位！", Toast.LENGTH_LONG).show();
				return;
			}
			if (null == this.school || ("").equals(this.school)) {
				Toast.makeText(getApplicationContext(),
						R.string.reg_check_password_hint, Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (schoolId == -1) {
				Toast.makeText(getApplicationContext(), "请输入正确的学校名称！",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (!TextUtils.isEmpty(phoneNumberStr)) {
				if (!isMobile(phoneNumberStr)) {
					Toast.makeText(this, "手机号格式不对", Toast.LENGTH_LONG).show();
					return;
				}
			}

			dialog = new ProgressDialog(this);
			dialog.setMessage("正在注册，请稍候。。。");
			dialog.show();
			try {
				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("requestCourseLevel", Constants.LEVEL);
				map.put("username", username);
				map.put("password", password);
				String paramsStr = new Gson().toJson(map);
				byte[] data = DESUtil.encryptDESBytes(paramsStr,
						Constants.DES_KEY);
				HttpEntity entity = new ByteArrayEntity(data);
				new AsyncHttpClient().post(this, Constants.REGISTER_URL,
						entity, "application/json",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, Header[] arg1,
									byte[] arg2) {
								super.onSuccess(arg0, arg1, arg2);
								loginJson = DESUtil.decryptDESBytes(arg2,
										Constants.DES_KEY);
								registe(loginJson);
							}

							@Override
							public void onFailure(int arg0, Header[] arg1,
									byte[] arg2, Throwable arg3) {
								super.onFailure(arg0, arg1, arg2, arg3);
								dialog.dismiss();
								Toast.makeText(getApplicationContext(),
										"注册失败，错误的网络连接！", Toast.LENGTH_SHORT)
										.show();
							}
						});
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case R.id.et_grade:
		case R.id.tv_grade:
			AlertDialog.Builder builder = new AlertDialog.Builder(
					RegisterActivity.this);
			final String[] items = new String[] { "大一 Freshman", "大二 Sophomore", "大三 Junior", "大四 Senior" };
			builder.setSingleChoiceItems(
					new String[] { "大一 Freshman", "大二 Sophomore", "大三 Junior", "大四 Senior" }, 0,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							grade.setText(items[which]);
						}
					})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).show();
			break;

		}
	}

	private void registe(String json) {
		if (json != null && json.length() > 0) {
			Gson gson = new Gson();
			LoginInfo loginInfo = gson.fromJson(json, LoginInfo.class);
			Intent intent = new Intent(RegisterActivity.this,
					AddPhoneNumberService.class);
			intent.putExtra("token", loginInfo.getToken());
			intent.putExtra("phone", phoneNumberStr);
			if (gradeStr.equals("大一 Freshman")) {
				intent.putExtra("grade", "1");
			} else if (gradeStr.equals("大二 Sophomore")) {
				intent.putExtra("grade", "2");
			} else if (gradeStr.equals("大三 Junior")) {
				intent.putExtra("grade", "3");
			} else if (gradeStr.equals("大四 Senior")) {
				intent.putExtra("grade", "4");
			}
			startService(intent);
			if (loginInfo != null && loginInfo.getErrorCode() == 0) {
				AppContext.getInstance().setLoginInfo(loginInfo);
				HttpUtils http = new HttpUtils();
				http.configTimeout(30 * 1000);
				RequestParams params = new RequestParams();
				params.setContentType(AppConfig.CONTENT_TYPE);
				token = loginInfo.getToken();
				try {
					byte[] body = AppConfig.school(token, schoolId);
					params.setBodyEntity(new ByteArrayEntity(body));
					http.send(HttpMethod.POST,
							AppConfig.requestURL(AppConfig.setcollege_action),
							params, new RequestCallBack<String>() {
								public void onFailure(HttpException arg0,
										String arg1) {
									dialog.dismiss();
									Toast.makeText(getApplicationContext(),
											"注册失败，错误的网络连接！", Toast.LENGTH_SHORT)
											.show();
								}

								public void onSuccess(
										ResponseInfo<String> reinfo) {
									dialog.dismiss();
									String json = reinfo.result;
									registeSchool(json);
								}

							});
				} catch (AppException e) {
					e.printStackTrace();
				}
			} else {
				dialog.dismiss();
				if (loginInfo.getErrorCode() == 3) {
					Toast.makeText(getApplicationContext(), "账号已存在！",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "注册失败，网络连接失败！",
							Toast.LENGTH_SHORT).show();
				}
			}
		} else {
			dialog.dismiss();
			Toast.makeText(getApplicationContext(), "注册失败，错误的网络连接！",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void registeSchool(String json) {
		Gson gson = new Gson();
		SchoolInfo schoolInfo = gson.fromJson(json, SchoolInfo.class);
		String coursejson = ReadUtil.readFile(this,
				"wwyj_offline/rootcourses4level/1");
		if (schoolInfo.getErrorCode() == 0) {
			LoginInfo loginInfo = gson.fromJson(coursejson, LoginInfo.class);
			loginInfo.setToken(token);
			AppContext.getInstance().setLoginInfo(loginInfo);
			AppContext.getInstance().setUsername(username);
			ContentValues loginInfoValues = new ContentValues();
			loginInfoValues.put(UserTable.USER_NAME, username);
			loginInfoValues.put(UserTable.USER_PASSWORD, password);
			loginInfoValues.put(UserTable.LOGIN_INFO, loginJson);
			userDao.insertUser(loginInfoValues);
			startActivity(new Intent(this, HomeActivity.class));
			LoginActivity.instance.finish();
			finish();
		} else {
			Toast.makeText(getApplicationContext(), "注册失败，网络连接失败！",
					Toast.LENGTH_SHORT).show();
		}
	}

	private int selectSchool() {
		for (int i = 0; i < schools.size(); i++) {
			if (school.equals(schools.get(i).getName())) {
				return schools.get(i).getId();
			}
		}
		return -1;
	}

	/**
	 * 邮箱判断正则表达式
	 * 
	 * @param
	 * @return
	 */
	private boolean EmailFormat(String username) {

		Pattern pattern = Pattern

				.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

		Matcher mc = pattern.matcher(username);

		return mc.matches();

	}

	/**
	 * 邮箱判断正则表达式
	 * 
	 * @param
	 * @return
	 */
	private boolean isMobile(String username) {

		Pattern pattern = Pattern

		.compile("^1[3458][0-9]{9}$");

		Matcher mc = pattern.matcher(username);

		return mc.matches();

	}

}
