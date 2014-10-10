package com.yingshibao.cet4.activity;

import java.util.ArrayList;

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
import com.yingshibao.cet4.adapter.UnKnownWordsAdapter;
import com.yingshibao.cet4.api.WordApi;
import com.yingshibao.cet4.bean.UnKnownWords;
import com.yingshibao.cet4.bean.Word;
import com.yingshibao.cet4.constants.UnKnownWordsTable;
import com.yingshibao.cet4.util.LogUtil;
import com.yingshibao.cet4.util.UIUtil;
import com.yingshibao.cet6.R;
import com.yingshibao.db.dao.UnknowWordsDao;

/**
 * 
 * @author zhaoshan
 *
 */
public class UnKnowWordsActivity extends BaseActivity implements
		LoaderCallbacks<Cursor>, OnClickListener {

	private ListView word_lv;// 单词分组的listview
	private Button study_btn;// 开始学习的按钮
	private UnKnownWordsAdapter adapter;
	private ArrayList<Word> wordList = new ArrayList<Word>();
	private UnknowWordsDao mUnknowWordsDao;
	private WordApi mWordApi;
	private Gson mGson;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_study);
		wordList.addAll((ArrayList<Word>) getIntent().getSerializableExtra(
				"wordList"));
		mUnknowWordsDao = new UnknowWordsDao(this);
		mWordApi = new WordApi(this);
		mGson = new Gson();
		mWordApi.getUnknownwords();
		getSupportLoaderManager().initLoader(1, null, this);

		initView();
		initData();
	}

	public void initView() {

		word_lv = UIUtil.getView(UnKnowWordsActivity.this, R.id.word_lv);
		study_btn = UIUtil.getView(UnKnowWordsActivity.this, R.id.study_btn);
		study_btn.setClickable(true);
		study_btn.setOnClickListener(this);
		actionBarTitle.setText("生词本");
		if (wordList == null || wordList.size() == 0) {
			study_btn.setClickable(false);
		} else {
			study_btn.setClickable(true);
		}
	}

	public void initData() {
		adapter = new UnKnownWordsAdapter(this, wordList);
		word_lv.setAdapter(adapter);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return mUnknowWordsDao.getCursorLoader(this, arg0 + "");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor == null || cursor.getCount() == 0) {
		} else if (cursor.moveToNext()) {
			String unKnownWordInfoJson = cursor.getString(cursor
					.getColumnIndex(UnKnownWordsTable.UNKNOWN_WORDS_JSON));
			UnKnownWords unKnownWordsInfo = mGson.fromJson(unKnownWordInfoJson,
					UnKnownWords.class);
			if (unKnownWordsInfo.getWordJsons() != null
					&& !unKnownWordsInfo.getWordJsons().isEmpty()) {
				wordList.clear();
				wordList.addAll((ArrayList<Word>) unKnownWordsInfo
						.getWordJsons());
				LogUtil.e("数据库中查询到的生词本信息是：" + wordList.toString());
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void finish() {
		Intent data = new Intent();
		data.putExtra("unknownwordlist", wordList);
		setResult(0, data);
		super.finish();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.study_btn:
			Intent intent = new Intent(UnKnowWordsActivity.this,
					UnknownWordsInfoActivity.class);
			intent.putExtra("wordList", wordList);
			intent.putExtra("level", 1);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}

}
