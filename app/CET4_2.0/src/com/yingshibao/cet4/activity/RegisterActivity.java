package com.yingshibao.cet4.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.content.ContentProvider;
import com.google.gson.Gson;
import com.yingshibao.api.RegisterAndLoginApi;
import com.yingshibao.bean.College;
import com.yingshibao.bean.Colleges;
import com.yingshibao.cet4.R;
import com.yingshibao.db.ColleageTable;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.yingshibao.bean.CollegeList;
/**
 * 注册页
 * 
 * @author zhaoshan
 * 
 */
public class RegisterActivity extends BaseActivity implements
		LoaderCallbacks<Cursor> {

	@InjectView(R.id.username_text)
	EditText username_text;
	@InjectView(R.id.password_text)
	EditText password_text;
	@InjectView(R.id.school_name_text)
	AutoCompleteTextView school_name_text;
	@InjectView(R.id.et_phone_number)
	EditText phoneNumber;
	@InjectView(R.id.et_grade)
	TextView et_grade;
	@InjectView(R.id.register_bt)
	ImageButton register_bt;

	private String username;
	private String password;
	private String coleage;
	private String phoneNumberStr;
	private String gradeStr;
	private int collegeId;
	private ArrayList<String> collegeNames = new ArrayList<String>();
	private ArrayList<College> collegeList = new ArrayList<College>();
	private RegisterAndLoginApi api;
	private Gson mGson;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		ButterKnife.inject(this);
		mGson = new Gson();
		api = new RegisterAndLoginApi(this);
		getSupportLoaderManager().initLoader(0, null, this);
		initData();
		actionBarTitle.setText("用户注册");
	}

	public void initData() {
		for (College college : collegeList) {
			collegeNames.add(college.getName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, collegeNames);
		school_name_text.setAdapter(adapter);
	}

	@OnClick(R.id.et_grade)
	public void selectGrade() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				RegisterActivity.this);
		final String[] items = new String[] { "大一", "大二", "大三", "大四" };
		builder.setSingleChoiceItems(new String[] { "大一", "大二", "大三", "大四" },
				0, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						et_grade.setText(items[which]);
					}
				}).show();

	}

	private College selectSchool() {
		for (College mCollege : collegeList) {
			if (coleage.equals(mCollege.getName())) {
				return mCollege;
			}
		}
		return null;
	}

	@OnClick(R.id.register_bt)
	public void register() {
		username = username_text.getText().toString().trim();
		password = password_text.getText().toString().trim();
		phoneNumberStr = phoneNumber.getText().toString().trim();
		gradeStr = et_grade.getText().toString().trim();
		coleage = school_name_text.getText().toString().trim();
		collegeId = selectSchool().getId();
		if (gradeStr.equals("大一")) {
			gradeStr = "1";
		} else if (gradeStr.equals("大二")) {
			gradeStr = "2";
		} else if (gradeStr.equals("大三")) {
			gradeStr = "3";
		} else if (gradeStr.equals("大四")) {
			gradeStr = "4";
		}
		if (null == this.username || ("").equals(this.username)) {
			this.username_text.requestFocus();
			Toast.makeText(getApplicationContext(), R.string.username_promt,
					Toast.LENGTH_SHORT).show();
		} else if (null == this.password || ("").equals(this.password)) {
			this.password_text.requestFocus();
			Toast.makeText(getApplicationContext(), R.string.reg_password_hint,
					Toast.LENGTH_SHORT).show();
		} else if (null == this.coleage || ("").equals(this.coleage)) {
			Toast.makeText(getApplicationContext(),
					R.string.reg_check_password_hint, Toast.LENGTH_SHORT)
					.show();
		} else if (username.equals(password)) {
			Toast.makeText(getApplicationContext(), "账号和密码一致",
					Toast.LENGTH_SHORT).show();
		} else if (!EmailFormat(username) || username.length() > 31) {// 通过正则表达式检验账号是否是邮箱
			Toast.makeText(this, "账号不为邮箱或格式不正确", Toast.LENGTH_LONG).show();
		} else if (password.length() < 3 || password.length() > 32) {
			Toast.makeText(this, "密码长度应在3～32位！", Toast.LENGTH_LONG).show();
		} else if (collegeId != -1) {
			dialog = new ProgressDialog(this);
			dialog.setMessage("正在注册，请稍候。。。");
			dialog.show();
			try {
				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("username", username);
				map.put("password", password);
				map.put("nickname", "");
				map.put("phone", phoneNumberStr);
				map.put("college", selectSchool().getId().toString());
				map.put("grade", gradeStr);
				api.register(map, selectSchool());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getApplicationContext(), "请输入正确的学校名称！",
					Toast.LENGTH_SHORT).show();
		}
	}

	private boolean EmailFormat(String username) {
		Pattern pattern = Pattern
				.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
		Matcher mc = pattern.matcher(username);
		return mc.matches();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this, ContentProvider.createUri(Colleges.class,
				null), null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor == null || cursor.getCount() == 0) {
		} else {
			if (cursor.moveToNext()) {
				String json = cursor.getString(cursor
						.getColumnIndex(ColleageTable.COLUMN_COLLEAGES));
				collegeList.addAll(mGson.fromJson(json, CollegeList.class));
				initData();
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}
}
