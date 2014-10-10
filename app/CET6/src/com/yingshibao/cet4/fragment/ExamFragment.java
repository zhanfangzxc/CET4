package com.yingshibao.cet4.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.activity.ArticleTemplatesActivity;
import com.yingshibao.cet4.activity.Level3CourseActivity;
import com.yingshibao.cet4.activity.UnKnowWordsActivity;
import com.yingshibao.cet4.activity.WordGroupActivity;
import com.yingshibao.cet4.adapter.CourseLevel2Adapter;
import com.yingshibao.cet4.adapter.WordGroupLevel1Adapter;
import com.yingshibao.cet4.api.CourseApi;
import com.yingshibao.cet4.api.WordApi;
import com.yingshibao.cet4.bean.Course;
import com.yingshibao.cet4.bean.CustomWordGroup;
import com.yingshibao.cet4.bean.LoginInfo;
import com.yingshibao.cet4.bean.TreeCourseInfo;
import com.yingshibao.cet4.bean.UnKnownWords;
import com.yingshibao.cet4.bean.Word;
import com.yingshibao.cet4.bean.WordGroup;
import com.yingshibao.cet4.bean.WordGroupInfo;
import com.yingshibao.cet4.constants.ParentCourseTable;
import com.yingshibao.cet4.constants.UnKnownWordsTable;
import com.yingshibao.cet4.constants.WordGroupTable;
import com.yingshibao.cet4.receiver.ReloadData;
import com.yingshibao.cet4.util.LogUtil;
import com.yingshibao.cet4.util.ReadUtil;
import com.yingshibao.cet4.util.UIUtil;
import com.yingshibao.cet6.R;
import com.yingshibao.db.dao.ParentCourseDao;
import com.yingshibao.db.dao.UnknowWordsDao;
import com.yingshibao.db.dao.WordGroupDao;

public class ExamFragment extends Fragment implements LoaderCallbacks<Cursor>,
		OnItemClickListener, ReloadData, OnClickListener {

	private Activity mActivity;
	private ParentCourseDao parentCourseDao;
	private CourseApi courseApi;
	private WordApi wordApi;
	private Gson mGson;

	private TreeCourseInfo listenTreeCourseInfo;
	private TreeCourseInfo readTreeCourseInfo;
	private WordGroupInfo wordGroupInfo;
	private UnKnownWords unKnownWordsInfo;
	//
	private CourseLevel2Adapter listenAdapter;
	private CourseLevel2Adapter readAdapter;
	private WordGroupLevel1Adapter wordAdapter;

	private AppContext mContext = AppContext.getInstance();

	private LoginInfo loginInfo = mContext.getLoginInfo();
	private GridView listenGv;
	private GridView readGv;
	private GridView wordGv;
	private ArrayList<CustomWordGroup> customWordGroups;
	private ArrayList<Word> wordList;

	private int[] listenIcons = { R.drawable.zongshu, R.drawable.duanduihua,
			R.drawable.changduihua, R.drawable.pianzhang, };
	private int[] readIcons = { R.drawable.shenduyuedu, R.drawable.xinxipipei,
			R.drawable.xuancitiankong };
	private int[] wordIcons = { R.drawable.gaopin, R.drawable.zhongpin,
			R.drawable.dipin };
	private WordGroupDao wordGroupDao;
	private UnknowWordsDao unKnownsWordDao;

	private ImageButton unKnown_bt;
	private View articleTemplate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_exam, container, false);
		listenGv = UIUtil.getView(view, R.id.gv_listen);
		readGv = UIUtil.getView(view, R.id.gv_read);
		wordGv = UIUtil.getView(view, R.id.gv_words);
		unKnown_bt = UIUtil.getView(view, R.id.unknown_bt);
		articleTemplate = UIUtil.getView(view, R.id.iv_article_template);
		listenGv.setOnItemClickListener(this);
		readGv.setOnItemClickListener(this);
		wordGv.setOnItemClickListener(this);
		unKnown_bt.setVisibility(View.GONE);
		articleTemplate.setOnClickListener(this);
		unKnown_bt.setOnClickListener(this);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = getActivity();
		parentCourseDao = new ParentCourseDao(mActivity);
		wordGroupDao = new WordGroupDao(mActivity);
		unKnownsWordDao = new UnknowWordsDao(mActivity);
		mGson = new Gson();
		courseApi = new CourseApi(mActivity);
		wordApi = new WordApi(mActivity);
		reload();
		// 听力
		getLoaderManager().initLoader(0, null, this);
		// 阅读
		getLoaderManager().initLoader(1, null, this);
		// 单词
		getLoaderManager().initLoader(2, null, this);
		// 生词本
		getLoaderManager().initLoader(3, null, this);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		switch (arg0) {
		case 0:
			return parentCourseDao.getCursorLoader(mActivity, "12");

		case 1:
			return parentCourseDao.getCursorLoader(mActivity, "13");

		case 2:
			return wordGroupDao.getCursorLoader(mActivity, "11");

		case 3:
			return unKnownsWordDao.getCursorLoader(mActivity, "1");
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor == null || cursor.getCount() == 0) {
			switch (arg0.getId()) {
			case 0:
				String json = ReadUtil.readFile(mActivity,
						"wwyj_offline/practicecoursetree/12");
				listenTreeCourseInfo = mGson.fromJson(json,
						TreeCourseInfo.class);
				if (listenTreeCourseInfo != null) {
					listenAdapter = new CourseLevel2Adapter(mActivity,
							listenTreeCourseInfo);
					int listenCourseNum = listenTreeCourseInfo.getCourseJsons()
							.size();
					for (int i = 0; i < listenCourseNum; i++) {
						listenTreeCourseInfo.getCourseJsons().get(i)
								.setIconResId(listenIcons[i]);
					}
					listenGv.setNumColumns(listenCourseNum);
					listenGv.setAdapter(listenAdapter);
				}
				break;
			case 1:
				String json2 = ReadUtil.readFile(mActivity,
						"wwyj_offline/practicecoursetree/13");
				readTreeCourseInfo = mGson
						.fromJson(json2, TreeCourseInfo.class);
				if (readTreeCourseInfo != null) {
					readAdapter = new CourseLevel2Adapter(mActivity,
							readTreeCourseInfo);
					int readCourseNum = readTreeCourseInfo.getCourseJsons()
							.size();
					for (int i = 0; i < readCourseNum; i++) {
						readTreeCourseInfo.getCourseJsons().get(i)
								.setIconResId(readIcons[i]);
					}
					readGv.setNumColumns(readCourseNum + 1);
					readGv.setAdapter(readAdapter);
				}
				break;
			case 2:
				ArrayList<WordGroup> highWordGroups = new ArrayList<WordGroup>();
				ArrayList<WordGroup> middleWordGroups = new ArrayList<WordGroup>();
				ArrayList<WordGroup> lowWordGroups = new ArrayList<WordGroup>();
				CustomWordGroup highCustomWordGroup = new CustomWordGroup();
				CustomWordGroup middleCustomWordGroup = new CustomWordGroup();
				CustomWordGroup lowCustomWordGroup = new CustomWordGroup();
				String json3 = ReadUtil.readFile(mActivity,
						"wwyj_offline/wordgroups/11");
				LogUtil.e("数据库中的单词分组是：" + json3);
				wordGroupInfo = mGson.fromJson(json3, WordGroupInfo.class);
				if (wordGroupInfo != null) {
					ArrayList<WordGroup> wordGroups = wordGroupInfo
							.getWordJsonGroups();
					for (WordGroup wordGroup : wordGroups) {
						LogUtil.e("单词频级是：" + wordGroup.getExamLevel() + "");
						if (1 == wordGroup.getExamLevel()) {
							lowWordGroups.add(wordGroup);
						} else if (2 == wordGroup.getExamLevel()) {
							middleWordGroups.add(wordGroup);
						} else if (3 == wordGroup.getExamLevel()) {
							highWordGroups.add(wordGroup);
						}
					}
					highCustomWordGroup.setIconResId(wordIcons[0]);
					highCustomWordGroup.setWordGroups(highWordGroups);
					middleCustomWordGroup.setIconResId(wordIcons[1]);
					middleCustomWordGroup.setWordGroups(middleWordGroups);
					lowCustomWordGroup.setIconResId(wordIcons[2]);
					lowCustomWordGroup.setWordGroups(lowWordGroups);
					customWordGroups = new ArrayList<CustomWordGroup>();

					customWordGroups.add(highCustomWordGroup);
					customWordGroups.add(middleCustomWordGroup);
					customWordGroups.add(lowCustomWordGroup);
					LogUtil.e(highCustomWordGroup.toString());
					LogUtil.e(middleCustomWordGroup.toString());
					LogUtil.e(lowCustomWordGroup.toString());
					wordAdapter = new WordGroupLevel1Adapter(mActivity,
							customWordGroups);
					wordGv.setNumColumns(4);
					wordGv.setAdapter(wordAdapter);
				}
				break;
			}
		} else {
			if (cursor.moveToNext()) {

				switch (arg0.getId()) {
				case 0:
					String json = cursor.getString(cursor
							.getColumnIndex(ParentCourseTable.COURSE_JSON));
					listenTreeCourseInfo = mGson.fromJson(json,
							TreeCourseInfo.class);
					if (listenTreeCourseInfo != null) {
						listenAdapter = new CourseLevel2Adapter(mActivity,
								listenTreeCourseInfo);
						int listenCourseNum = listenTreeCourseInfo
								.getCourseJsons().size();
						for (int i = 0; i < listenCourseNum; i++) {
							listenTreeCourseInfo.getCourseJsons().get(i)
									.setIconResId(listenIcons[i]);
						}
						listenGv.setNumColumns(listenCourseNum);
						listenGv.setAdapter(listenAdapter);
					}
					break;
				case 1:
					String json2 = cursor.getString(cursor
							.getColumnIndex(ParentCourseTable.COURSE_JSON));
					readTreeCourseInfo = mGson.fromJson(json2,
							TreeCourseInfo.class);
					if (readTreeCourseInfo != null) {
						readAdapter = new CourseLevel2Adapter(mActivity,
								readTreeCourseInfo);
						int readCourseNum = readTreeCourseInfo.getCourseJsons()
								.size();
						for (int i = 0; i < readCourseNum; i++) {
							readTreeCourseInfo.getCourseJsons().get(i)
									.setIconResId(readIcons[i]);
						}
						readGv.setNumColumns(4);
						readGv.setAdapter(readAdapter);
					}
					break;
				case 2:
					ArrayList<WordGroup> highWordGroups = new ArrayList<WordGroup>();
					ArrayList<WordGroup> middleWordGroups = new ArrayList<WordGroup>();
					ArrayList<WordGroup> lowWordGroups = new ArrayList<WordGroup>();
					CustomWordGroup highCustomWordGroup = new CustomWordGroup();
					CustomWordGroup middleCustomWordGroup = new CustomWordGroup();
					CustomWordGroup lowCustomWordGroup = new CustomWordGroup();
					String json3 = cursor.getString(cursor
							.getColumnIndex(WordGroupTable.WORD_GROUP_JSON));
					LogUtil.e("数据库中的单词分组是：" + json3);
					wordGroupInfo = mGson.fromJson(json3, WordGroupInfo.class);

					if (wordGroupInfo != null) {
						ArrayList<WordGroup> wordGroups = wordGroupInfo
								.getWordJsonGroups();
						LogUtil.e("解析后的单词分组：" + wordGroups.toString());
						for (WordGroup wordGroup : wordGroups) {
							LogUtil.e("单词频级是：" + wordGroup.getExamLevel() + "");
							if (1 == wordGroup.getExamLevel()) {
								lowWordGroups.add(wordGroup);
							} else if (2 == wordGroup.getExamLevel()) {
								middleWordGroups.add(wordGroup);
							} else if (3 == wordGroup.getExamLevel()) {
								highWordGroups.add(wordGroup);
							}
						}
						highCustomWordGroup.setIconResId(wordIcons[0]);
						highCustomWordGroup.setWordGroups(highWordGroups);
						middleCustomWordGroup.setIconResId(wordIcons[1]);
						middleCustomWordGroup.setWordGroups(middleWordGroups);
						lowCustomWordGroup.setIconResId(wordIcons[2]);
						lowCustomWordGroup.setWordGroups(lowWordGroups);
						customWordGroups = new ArrayList<CustomWordGroup>();

						customWordGroups.add(highCustomWordGroup);
						customWordGroups.add(middleCustomWordGroup);
						customWordGroups.add(lowCustomWordGroup);
						LogUtil.e(highCustomWordGroup.toString());
						LogUtil.e(middleCustomWordGroup.toString());
						LogUtil.e(lowCustomWordGroup.toString());
						wordAdapter = new WordGroupLevel1Adapter(mActivity,
								customWordGroups);
						wordGv.setNumColumns(4);
						wordGv.setAdapter(wordAdapter);
					}
					break;
				case 3:
					String json4 = cursor
							.getString(cursor
									.getColumnIndex(UnKnownWordsTable.UNKNOWN_WORDS_JSON));
					wordList = new ArrayList<Word>();
					unKnownWordsInfo = mGson
							.fromJson(json4, UnKnownWords.class);
					if (unKnownWordsInfo != null) {
						wordList.addAll(unKnownWordsInfo.getWordJsons());
						if (wordList != null && wordList.size() != 0) {
							unKnown_bt.setVisibility(View.VISIBLE);
						}
					}
					break;
				}
			}
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		HashMap<String, String> map = new HashMap<String, String>();
		switch (parent.getId()) {
		//
		case R.id.gv_listen:
			MobclickAgent.onEvent(getActivity(), "listen");
			Course course = (Course) parent.getItemAtPosition(position);
			Intent intent = new Intent(mActivity, Level3CourseActivity.class);
			StringBuffer sb1 = new StringBuffer("听力" + "·" + course.getName());
			intent.putExtra("title", sb1.toString());
			intent.putExtra("course", course);
			intent.putExtra("course_tree", listenTreeCourseInfo);
			map.put("course_name", sb1.toString());
			MobclickAgent.onEvent(getActivity(), "进入听力第3级课程", map);
			startActivity(intent);
			break;
		case R.id.gv_read:
			MobclickAgent.onEvent(getActivity(), "read");
			Course course2 = (Course) parent.getItemAtPosition(position);
			Intent intent2 = new Intent(mActivity, Level3CourseActivity.class);
			StringBuffer sb2 = new StringBuffer("阅读" + "·" + course2.getName());
			intent2.putExtra("title", sb2.toString());
			intent2.putExtra("course", course2);
			intent2.putExtra("course_tree", readTreeCourseInfo);
			map.put("course_name", sb2.toString());
			MobclickAgent.onEvent(getActivity(), "进入阅读第3级课程", map);
			startActivity(intent2);

			break;
		case R.id.gv_words:
			MobclickAgent.onEvent(getActivity(), "word");
			if (position == 0) {
				ArrayList<WordGroup> wordGroups = customWordGroups.get(2)
						.getWordGroups();
				Intent intent1 = new Intent(mActivity, WordGroupActivity.class);
				intent1.putExtra("wordGroups", wordGroups);
				intent1.putExtra("level", 3);
				intent1.putExtra("wordCourseId", "11");
				map.put("level", "低频");
				MobclickAgent.onEvent(getActivity(), "进入单词分组", map);
				startActivity(intent1);
			} else if (position == 1) {
				ArrayList<WordGroup> wordGroups = customWordGroups.get(1)
						.getWordGroups();
				Intent intent1 = new Intent(mActivity, WordGroupActivity.class);
				intent1.putExtra("wordGroups", wordGroups);
				intent1.putExtra("level", 2);
				intent1.putExtra("wordCourseId", "11");
				map.put("level", "中频");
				MobclickAgent.onEvent(getActivity(), "进入单词分组", map);
				startActivity(intent1);
			} else {
				ArrayList<WordGroup> wordGroups = customWordGroups.get(0)
						.getWordGroups();
				Intent intent1 = new Intent(mActivity, WordGroupActivity.class);
				intent1.putExtra("wordGroups", wordGroups);
				intent1.putExtra("level", 1);
				intent1.putExtra("wordCourseId", "11");
				map.put("level", "高频");
				MobclickAgent.onEvent(getActivity(), "进入单词分组", map);
				startActivity(intent1);
			}

			break;
		default:
			break;
		}

	}

	@Override
	public void reload() {
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("rootCourseId", "12");
		map1.put("token", loginInfo.getToken());
		courseApi.getPracticeCourseTree(map1);
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("rootCourseId", "13");
		map2.put("token", loginInfo.getToken());
		courseApi.getPracticeCourseTree(map2);
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("token", loginInfo.getToken());
		map3.put("wordCourseId", "11");
		wordApi.getWordGroup(map3);
		wordApi.getUnknownwords();
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.unknown_bt:
			MobclickAgent.onEvent(getActivity(), "进入生词本");
			intent.setClass(this.getActivity(), UnKnowWordsActivity.class);
			intent.putExtra("wordList", wordList);
			startActivity(intent);
			break;
		case R.id.iv_article_template:
			MobclickAgent.onEvent(getActivity(), "进入作文模板");
			intent.setClass(getActivity(), ArticleTemplatesActivity.class);
			startActivity(intent);

			break;

		default:
			break;
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

}
