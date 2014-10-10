package com.yingshibao.cet4.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.api.WordApi;
import com.yingshibao.cet4.bean.UnKnownWords;
import com.yingshibao.cet4.bean.Word;
import com.yingshibao.cet4.bean.WordGroup;
import com.yingshibao.cet4.bean.WordGroupInfo;
import com.yingshibao.cet4.bean.Words4Group;
import com.yingshibao.cet4.constants.UnKnownWordsTable;
import com.yingshibao.cet4.constants.WordGroupTable;
import com.yingshibao.cet4.constants.WordInfoTable;
import com.yingshibao.cet4.receiver.ReloadData;
import com.yingshibao.cet4.util.NetWorkUtil;
import com.yingshibao.cet4.util.ReadUtil;
import com.yingshibao.cet6.R;
import com.yingshibao.db.dao.UnknowWordsDao;
import com.yingshibao.db.dao.WordGroupDao;
import com.yingshibao.db.dao.WordInfoDao;

/**
 * 单词详情页面
 * 
 * @author zhaoshan
 * 
 */
public class WordInfoActivity extends BaseActivity implements OnClickListener,
		LoaderCallbacks<Cursor>, ReloadData {

	private TextView progress_tv;

	private TextView content_tv;

	private ImageButton play_btn;

	private TextView comment_tv;

	private TextView example_tv;
	private TextView match_tv;

	private ImageButton fuzzy_btn;

	private LinearLayout comment_ll;
	private LinearLayout example_ll;
	private LinearLayout match_ll;
	private ImageButton know_btn, previous_btn, next_btn;
	private ArrayList<Word> wordList = new ArrayList<Word>();
	private TextView word_tv;
	private TextView word_match_tv;
	private int current;

	private MediaPlayer mediaPlayer;

	private int allwordsnum;
	private Gson mGson;
	private String groupId;
	private WordInfoDao mWordInfoDao;
	private WordApi api;
	private ArrayList<Word> unKnownwordList = new ArrayList<Word>();
	private UnknowWordsDao unKnownsWordDao;
	private int level;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_info);
		mGson = new Gson();
		mWordInfoDao = new WordInfoDao(this);
		unKnownsWordDao = new UnknowWordsDao(this);
		api = new WordApi(this);
		wordList.addAll((ArrayList<Word>) getIntent().getSerializableExtra(
				"wordList"));
		level = getIntent().getIntExtra("level", 3);
		groupId = getIntent().getStringExtra("groupId");
		reload();
		// 查询生词本的列表
		getSupportLoaderManager().initLoader(1, null, this);
		initView();
		initData();
		play();
		changeView();
	}

	// 初始化界面
	public void initView() {
		progress_tv = (TextView) findViewById(R.id.progress_tv);
		content_tv = (TextView) findViewById(R.id.content_tv);
		play_btn = (ImageButton) findViewById(R.id.play_btn);
		play_btn.setOnClickListener(this);
		comment_tv = (TextView) findViewById(R.id.comment_tv);
		example_tv = (TextView) findViewById(R.id.example_tv);
		match_tv = (TextView) findViewById(R.id.match_tv);
		fuzzy_btn = (ImageButton) findViewById(R.id.fuzzy_btn);
		fuzzy_btn.setOnClickListener(this);
		know_btn = (ImageButton) findViewById(R.id.know_btn);
		know_btn.setOnClickListener(this);
		previous_btn = (ImageButton) findViewById(R.id.previous_btn);
		previous_btn.setOnClickListener(this);
		next_btn = (ImageButton) findViewById(R.id.next_btn);
		next_btn.setOnClickListener(this);
		comment_ll = (LinearLayout) findViewById(R.id.comment_ll);
		example_ll = (LinearLayout) findViewById(R.id.example_ll);
		match_ll= (LinearLayout) findViewById(R.id.match_ll);
		word_tv = (TextView) findViewById(R.id.word_tv);
		word_match_tv = (TextView) findViewById(R.id.word_match_tv);
		unknown_btn.setVisibility(View.VISIBLE);
		unknown_btn.setOnClickListener(this);
	}

	// 初始化界面数据
	public void initData() {
		for (int i = 0; i < wordList.size(); i++) {
			if (wordList.get(i).getUnknownFlag() == -1) {
				this.current = i;
				return;
			}
		}
	}

	private void changeView() {
		// play();
		Word word = null;
		if(this.current == 30){
			word = this.wordList.get(29);
		}else{
			word = this.wordList.get(this.current);
		}
		actionBarTitle.setText((this.current + 1) + "/" + this.wordList.size());
		this.content_tv.setText(word.getName() + "\t\t" + word.getSoundmark());
		SpannableString spannableString1 = new SpannableString("[释义]\n"
				+ word.getMean());
		spannableString1.setSpan(new StyleSpan(Typeface.BOLD), 0, 3,
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		this.comment_tv.setText(spannableString1);

		if (!TextUtils.isEmpty(word.getPhrase())) {
			SpannableString spannableString = new SpannableString("[搭配]");
			spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 3,
					Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
			this.word_match_tv.setText(spannableString);
			this.match_tv.setText(word.getPhrase());
		} else {
			this.match_tv.setVisibility(View.GONE);
			this.match_ll.setVisibility(View.GONE);

		}
		if (!TextUtils.isEmpty(word.getExample())) {
			SpannableString spannableString = new SpannableString("[历年考句]");
			spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 5,
					Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
			this.word_tv.setText(spannableString);
			this.example_tv.setText(word.getExample());
			// this.example_tv.setVisibility(View.VISIBLE);
			// this.example_ll.setVisibility(View.VISIBLE);
		} else {
			this.example_tv.setVisibility(View.GONE);
			this.example_ll.setVisibility(View.GONE);

		}

	}

	private void play() {
		this.mediaPlayer = new MediaPlayer();
		AssetManager am = this.getAssets();
		String voicePath = wordList.get(current).getAudioUrl();
		String voiceName = voicePath.substring(voicePath.lastIndexOf("/") + 1);
		AssetFileDescriptor afd;
		try {
			afd = am.openFd("wordvoice" + "/" + voiceName);
			this.mediaPlayer.setDataSource(afd.getFileDescriptor(),
					afd.getStartOffset(), afd.getLength());
			this.mediaPlayer.prepare();
			this.mediaPlayer
					.setOnCompletionListener(new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							mediaPlayer.release();
						}
					});
		} catch (Exception e) {
		}
		this.mediaPlayer.start();
	}

	private void showExample(boolean show) {
		if (show) {
			this.comment_tv.setVisibility(View.VISIBLE);
			this.example_tv.setVisibility(View.VISIBLE);
			this.comment_ll.setVisibility(View.VISIBLE);
			this.example_ll.setVisibility(View.VISIBLE);
			this.match_tv.setVisibility(View.VISIBLE);
			this.match_ll.setVisibility(View.VISIBLE);
			this.fuzzy_btn.setVisibility(View.INVISIBLE);
			this.know_btn.setVisibility(View.INVISIBLE);
			if (this.current > 0)
				this.previous_btn.setVisibility(View.VISIBLE);
			this.next_btn.setVisibility(View.VISIBLE);
		} else {
			this.comment_tv.setVisibility(View.GONE);
			this.example_tv.setVisibility(View.GONE);
			this.comment_ll.setVisibility(View.GONE);
			this.example_ll.setVisibility(View.GONE);
			this.match_tv.setVisibility(View.GONE);
			this.match_ll.setVisibility(View.GONE);
			this.fuzzy_btn.setVisibility(View.VISIBLE);
			this.know_btn.setVisibility(View.VISIBLE);
			this.previous_btn.setVisibility(View.INVISIBLE);
			this.next_btn.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.play_btn:
			play();
			break;

		case R.id.fuzzy_btn:
			showExample(true);
			Toast toast = Toast.makeText(getApplicationContext(), "已经添加到生词本",
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			changeView();
			updateData(1);
			break;

		case R.id.know_btn:
			showExample(true);
			changeView();
			updateData(0);
			break;

		case R.id.previous_btn:
			showExample(false);
			--this.current;
			play();
			changeView();
			break;

		case R.id.next_btn:
			showExample(false);
			++this.current;
			if (current < wordList.size()) {
				play();
				changeView();
			}
			break;
		case R.id.unknown_btn:
			Intent intent = new Intent(WordInfoActivity.this,
					UnKnowWordsActivity.class);
			intent.putExtra("wordList", unKnownwordList);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	private void updateData(int know) {
		double progress = (double) ((double) (current + 1) / (double) (30));
		if (current < wordList.size()) {
			Word word = wordList.get(current);
			word.setUnknownFlag(know);
			// TODO 提交单词的状态
			System.out.println("当前单词序号为：" + current);
			if (NetWorkUtil.isNetConnected(this)) {
				// 有网络连接的情况下
				Map<String, Integer> map1 = new HashMap<String, Integer>();
				map1.put("wordId", word.getId());
				map1.put("unknownFlag", know);
				List<Map<String, Integer>> flagWords = new ArrayList<Map<String, Integer>>();
				flagWords.add(map1);
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("token", AppContext.getInstance().getLoginInfo()
						.getToken());
				map.put("progress", progress + "");
				map.put("flagWords", flagWords);
				new WordApi(getApplicationContext()).getWordState(map, null);
				// api.getWordState(map, api);
				// 更新本地数据库
				Map<String, String> map2 = new LinkedHashMap<String, String>();
				map2.put("groupId", groupId);
				map2.put("token", AppContext.getInstance().getLoginInfo()
						.getToken());
				api.getwords4group(map2);
				getSupportLoaderManager().initLoader(0, null, this);
				// getSupportLoaderManager().initLoader(Integer.parseInt(groupId),
				// null, this);
			} else {
				// 无网络连接
				Words4Group mWord4Group = new Words4Group();
				mWord4Group.setErrorCode(0);
				mWord4Group.setProgress(progress + "");
				mWord4Group.setWordJsons(wordList);
				String wordJsons = mGson.toJson(mWord4Group);
				ContentValues values = new ContentValues();
				values.put(WordInfoTable.ID, groupId);
				values.put(WordInfoTable.WORD_INFO_JSON, wordJsons);
				mWordInfoDao.insertWordInfo(values);

			}
			if (AppContext.getInstance().getWordGroups() != null) {
				for (WordGroup wordGroup : AppContext.getInstance()
						.getWordGroups()) {
					if (wordGroup.getId().equals(groupId)) {
						wordGroup.setProgress(progress);
						WordGroupInfo wordGroupInfo = new WordGroupInfo();
						wordGroupInfo.setErrorCode(0);
						wordGroupInfo.setTotalWordCount(1967);
						wordGroupInfo.setKnownWordsCount(0);
						wordGroupInfo.setUnknownWordsCount(0);
						wordGroupInfo.setGroupCount(66);
						wordGroupInfo.setWordJsonGroups(AppContext
								.getInstance().getWordGroups());
						String json = mGson.toJson(wordGroupInfo);
						ContentValues values = new ContentValues();
						values.put(WordGroupTable.ID, "11");
						values.put(WordGroupTable.WORD_GROUP_JSON, json);
						new WordGroupDao(this).insertWordGroup(values);
					}
				}
			}
			// 提交学习状态
			if (current + 1 == wordList.size()) {// 学习完成所选的单词
				if (this.allwordsnum == wordList.size()) {
					this.next_btn.setVisibility(View.INVISIBLE);
					Toast.makeText(getApplicationContext(), "恭喜你，该分组已经学习完成!",
							Toast.LENGTH_LONG).show();
				} else {
					this.next_btn.setVisibility(View.INVISIBLE);
					Toast.makeText(getApplicationContext(),
							"恭喜你，所选择的单词已经学习完成!", Toast.LENGTH_LONG).show();
				}
			} else {
			}
		}

	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		switch (arg0) {
		case 0:
			return mWordInfoDao.getCursorLoader(this, groupId);
		case 1:
			return unKnownsWordDao.getCursorLoader(this, "1");
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		switch (arg0.getId()) {
		case 0:
			if (cursor == null || cursor.getCount() == 0) {
				String wordInfoJson = ReadUtil.readFile(this,
						"wwyj_offline/words4group/" + groupId);
				Words4Group wordsInfo = mGson.fromJson(wordInfoJson,
						Words4Group.class);
				if (wordsInfo.getWordJsons() != null
						&& !wordsInfo.getWordJsons().isEmpty()) {
					wordList.clear();
					wordList.addAll((ArrayList<Word>) wordsInfo.getWordJsons());
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
				}
			}
			break;
		case 1:
			if (cursor == null || cursor.getCount() == 0) {
			} else if (cursor.moveToNext()) {
				String unKnownWordInfoJson = cursor.getString(cursor
						.getColumnIndex(UnKnownWordsTable.UNKNOWN_WORDS_JSON));
				UnKnownWords unKnownWordsInfo = mGson.fromJson(
						unKnownWordInfoJson, UnKnownWords.class);
				if (unKnownWordsInfo.getWordJsons() != null
						&& !unKnownWordsInfo.getWordJsons().isEmpty()) {
					unKnownwordList.clear();
					unKnownwordList.addAll((ArrayList<Word>) unKnownWordsInfo
							.getWordJsons());
					if (unKnownwordList == null || unKnownwordList.size() == 0) {
						unknown_btn.setVisibility(View.GONE);
					}
				}

			}
			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
	}

	@Override
	protected void onResume() {
		// getSupportLoaderManager().restartLoader(0, null, this);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("groupId", groupId);
		map.put("token", AppContext.getInstance().getLoginInfo().getToken());
		api.getwords4group(map);
		super.onResume();
	}

	@Override
	public void reload() {
		api.getUnknownwords();
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("groupId", groupId);
		map2.put("token", AppContext.getInstance().getLoginInfo().getToken());
		api.getwords4group(map2);
	}

}
