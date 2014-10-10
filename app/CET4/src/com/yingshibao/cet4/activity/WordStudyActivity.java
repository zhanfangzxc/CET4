package com.yingshibao.cet4.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.R;
import com.yingshibao.cet4.adapter.WordStudyAdapter;
import com.yingshibao.cet4.api.WordApi;
import com.yingshibao.cet4.bean.Word;
import com.yingshibao.cet4.bean.WordGroup;
import com.yingshibao.cet4.bean.Words4Group;
import com.yingshibao.cet4.constants.WordInfoTable;
import com.yingshibao.cet4.util.LogUtil;
import com.yingshibao.cet4.util.ReadUtil;
import com.yingshibao.db.dao.WordInfoDao;

public class WordStudyActivity extends BaseActivity implements OnClickListener,
		LoaderCallbacks<Cursor> {

	private ListView word_lv;// 单词分组的listview
	private Button study_btn;// 开始学习的按钮
	private WordStudyAdapter adapter;
	private ArrayList<Word> wordList = new ArrayList<Word>();
	private Gson mGson;
	private WordInfoDao mWordInfoDao;
	private WordApi api;
	private WordGroup wordGroup;
	private String groupId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_study);
		adapter = new WordStudyAdapter(this, wordList);
		mGson = new Gson();
		mWordInfoDao = new WordInfoDao(this);
		api = new WordApi(this);
		// 获取从上个界面传过来的单词列表信息
		wordGroup = (WordGroup) getIntent().getSerializableExtra("wordGroup");
		groupId = wordGroup.getId();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("groupId", groupId);
		map.put("token", AppContext.getInstance().getLoginInfo().getToken());
		api.getwords4group(map);
		getSupportLoaderManager().initLoader(Integer.parseInt(groupId), null,
				this);

	}

	// 初始化界面
	public void initView() {
		actionBarTitle.setText("单词");
		word_lv = (ListView) findViewById(R.id.word_lv);
		study_btn = (Button) findViewById(R.id.study_btn);
		study_btn.setOnClickListener(this);
	}

	// 初始化数据
	public void initData() {
		adapter = new WordStudyAdapter(getApplicationContext(), wordList);
		word_lv.setAdapter(adapter);
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.study_btn:
			if (wordList == null) {
				return;
			}
			Intent intent = new Intent(WordStudyActivity.this,
					WordInfoActivity.class);
			intent.putExtra("wordList", wordList);
			intent.putExtra("groupId", wordGroup.getId());
			intent.putExtra("level", getIntent().getStringExtra("level"));
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return mWordInfoDao.getCursorLoader(this, arg0 + "");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {

		if (cursor == null || cursor.getCount() == 0) {
			String wordInfoJson = ReadUtil.readFile(this,
					"wwyj_offline/words4group/" + wordGroup.getId());
			Words4Group wordsInfo = mGson.fromJson(wordInfoJson,
					Words4Group.class);
			if (wordsInfo.getWordJsons() != null
					&& !wordsInfo.getWordJsons().isEmpty()) {
				wordList.clear();
				wordList.addAll((ArrayList<Word>) wordsInfo.getWordJsons());
				initView();
				initData();
			}
		} else if (cursor.moveToNext()) {
			String wordInfoJson = cursor.getString(cursor
					.getColumnIndex(WordInfoTable.WORD_INFO_JSON));
			Words4Group wordsInfo = mGson.fromJson(wordInfoJson,
					Words4Group.class);
			if (wordsInfo.getWordJsons() != null
					&& !wordsInfo.getWordJsons().isEmpty()) {
				wordList.clear();
				wordList.addAll((ArrayList<Word>) wordsInfo.getWordJsons());
				LogUtil.e("数据库中查询到的单词信息是：" + wordList.toString());
				initView();
				initData();
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}
}
