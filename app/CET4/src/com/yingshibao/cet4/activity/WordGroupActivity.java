package com.yingshibao.cet4.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.R;
import com.yingshibao.cet4.adapter.WordGroupAdapter;
import com.yingshibao.cet4.api.WordApi;
import com.yingshibao.cet4.bean.WordGroup;
import com.yingshibao.cet4.bean.WordGroupInfo;
import com.yingshibao.cet4.constants.WordGroupTable;
import com.yingshibao.cet4.util.ReadUtil;
import com.yingshibao.db.dao.WordGroupDao;

/**
 * 单词分组页面
 * 
 * @author zhaoshan
 * 
 */
public class WordGroupActivity extends BaseActivity implements
		LoaderCallbacks<Cursor>, OnClickListener {

	/**
	 * word_gv 词汇分组九宫格
	 */
	private GridView word_gv;

	/**
	 * adapter 词汇分组适配器
	 */
	private WordGroupAdapter adapter;
	private ArrayList<WordGroup> wordGroups = new ArrayList<WordGroup>();
	private WordGroupDao mWordGroupDao;
	private Gson mGson;
	private String wordCourseId;
	private WordApi mWordApi;
	private int level;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_group);
		level = getIntent().getIntExtra("level", 3);
		wordGroups.addAll((ArrayList<WordGroup>) getIntent()
				.getSerializableExtra("wordGroups"));
		wordCourseId = getIntent().getStringExtra("wordCourseId");
		mWordGroupDao = new WordGroupDao(this);
		mGson = new Gson();
		mWordApi = new WordApi(this);
		initView();
		initData();
		getSupportLoaderManager().initLoader(Integer.parseInt(wordCourseId),
				null, this);

	}

	// 初始化界面
	private void initView() {
		actionBarTitle.setText("单词");
		back.setOnClickListener(this);
		word_gv = (GridView) findViewById(R.id.group_gv);
		word_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				WordGroup wordGroup = (WordGroup) word_gv
						.getItemAtPosition(index);
				WordGroup prewordGroup = null;// 前一个分组
				if (index > 0) {
					prewordGroup = (WordGroup) word_gv
							.getItemAtPosition(index - 1);
				}
				if (prewordGroup == null
						|| prewordGroup.getProgress()
								* prewordGroup.getTotalWordCount() == prewordGroup
								.getTotalWordCount()
						|| wordGroup.getProgress() != 0) {
					HashMap<String, String> map = new HashMap<String, String>();
					if (level == 1) {
						map.put("group_index", index + "");
						MobclickAgent.onEvent(WordGroupActivity.this,
								"进入低频单词的每个分组", map);
					} else if (level == 2) {
						map.put("group_index", index + "");
						MobclickAgent.onEvent(WordGroupActivity.this,
								"进入中频单词的每个分组", map);
					} else if (level == 3) {
						map.put("group_index", index + "");
						MobclickAgent.onEvent(WordGroupActivity.this,
								"进入高频单词的每个分组", map);
					}
					Intent intent = new Intent();
					intent.putExtra("wordGroup", wordGroup);
					intent.putExtra("level", level + "");
					intent.setClass(WordGroupActivity.this,
							WordStudyActivity.class);
					startActivityForResult(intent, 0);
				} else {
					Toast.makeText(getApplicationContext(), "请先学完前一组单词",
							Toast.LENGTH_LONG).show();

				}

			}
		});
	}

	// 初始化数据
	private void initData() {
		bindGroupsAdapter();
		word_gv.setAdapter(adapter);
	}

	/**
	 * 初始化WordGroupAdapter
	 */
	private void bindGroupsAdapter() {

		adapter = new WordGroupAdapter(wordGroups, getApplicationContext());
		word_gv.setAdapter(adapter);

	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return mWordGroupDao.getCursorLoader(this, arg0 + "");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor == null || cursor.getCount() == 0) {
			String wordGroupInfoJson = ReadUtil.readFile(this,
					"wwyj_offline/wordgroups/3");
			WordGroupInfo wordGroupInfo = mGson.fromJson(wordGroupInfoJson,
					WordGroupInfo.class);
			if (wordGroupInfo.getWordJsonGroups() != null
					&& !wordGroupInfo.getWordJsonGroups().isEmpty()) {
				AppContext.getInstance().setWordGroups(
						wordGroupInfo.getWordJsonGroups());
				wordGroups.clear();
				for (int i = 0; i < wordGroupInfo.getWordJsonGroups().size(); i++) {
					WordGroup wordGroup = wordGroupInfo.getWordJsonGroups()
							.get(i);
					if (wordGroup.getExamLevel() == level) {
						wordGroups.add(wordGroup);
					}
				}
				adapter.notifyDataSetChanged();

			}
		} else if (cursor.moveToNext()) {
			String wordGroupInfoJson = cursor.getString(cursor
					.getColumnIndex(WordGroupTable.WORD_GROUP_JSON));
			WordGroupInfo wordGroupInfo = mGson.fromJson(wordGroupInfoJson,
					WordGroupInfo.class);
			AppContext.getInstance().setWordGroups(
					wordGroupInfo.getWordJsonGroups());
			if (wordGroupInfo.getWordJsonGroups() != null
					&& !wordGroupInfo.getWordJsonGroups().isEmpty()) {
				wordGroups.clear();
				for (int i = 0; i < wordGroupInfo.getWordJsonGroups().size(); i++) {
					WordGroup wordGroup = wordGroupInfo.getWordJsonGroups()
							.get(i);
					if (wordGroup.getExamLevel() == level) {
						wordGroups.add(wordGroup);
					}
				}
				adapter.notifyDataSetChanged();

			}
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
	}

	@Override
	protected void onResume() {
		super.onResume();
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("token", AppContext.getInstance().getLoginInfo().getToken());
		map3.put("wordCourseId", "3");
		new WordApi(this).getWordGroup(map3);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

}
