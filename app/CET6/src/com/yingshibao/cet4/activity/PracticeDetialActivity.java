package com.yingshibao.cet4.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.api.PracticeApi;
import com.yingshibao.cet4.bean.Option;
import com.yingshibao.cet4.bean.Practice;
import com.yingshibao.cet4.bean.PracticeInfo;
import com.yingshibao.cet4.bean.Question;
import com.yingshibao.cet4.bean.TreeCourseInfo;
import com.yingshibao.cet4.constants.PracticeTable;
import com.yingshibao.cet4.media.PlayerEngineImpl;
import com.yingshibao.cet4.media.PlayerEngineListener;
import com.yingshibao.cet4.media.Playlist;
import com.yingshibao.cet4.util.LogUtil;
import com.yingshibao.cet4.util.NetWorkUtil;
import com.yingshibao.cet4.util.ReadUtil;
import com.yingshibao.cet6.R;
import com.yingshibao.db.dao.PracticeDao;

/**
 * 
 * 练习详情界面
 * 
 * @author zhaoshan
 * 
 */
public class PracticeDetialActivity extends BaseActivity implements
		OnTouchListener, OnClickListener, PlayerEngineListener,
		OnSeekBarChangeListener, LoaderCallbacks<Cursor> {
	private TextView currTime_tv;// 当前播放时间
	private TextView totalTime_tv;// 总播放时间
	private ImageView play_btn;// 播放按钮
	private SeekBar seekbar;// 进度条

	public static ImageView temp_av;
	private Button scroll_bt;
	private LinearLayout read_question_layout;
	private ImageButton submit_bt;

	private Button next_practice_bt;
	private Button last_practice_bt;
	private RelativeLayout parentlayout;
	private ImageView line;

	private int btHigh;
	private int parentHigh;
	private int mStartY;
	private int mStartTop;
	private int lHigh;

	private ScrollView read_scrollview_content;
	private ScrollView read_scroll_pactice;
	private LinearLayout playTime_ll;
	private LinearLayout progress;
	private LinearLayout listen;
	private RelativeLayout view;
	private TextView read_content;
	private LayoutInflater layInflater;
	private ArrayList<Practice> practices = new ArrayList<Practice>();// 练习集合
	private Practice practice;
	private List<Question> questionlist;// 问题集合
	private String courseId;
	private int selectedIndex = 0;// 当前题
	private List<View> optioncomments = new ArrayList<View>();// 存储注释信息的View
	private PlayerEngineImpl playEngineImpl;
	private Playlist playlist;
	private LinearLayout listen_rl;
	private ImageView download;
	private TreeCourseInfo treeCourseInfo;
	private PracticeDao mPracticeDao;
	private Gson mGson;
	private String title;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = (RelativeLayout) View.inflate(this,
				R.layout.activity_practice_detial, null);
		setContentView(view);
		mPracticeDao = new PracticeDao(this);
		mGson = new Gson();
		playEngineImpl = new PlayerEngineImpl();
		playEngineImpl.setListener(this);
		practices = (ArrayList<Practice>) getIntent().getSerializableExtra(
				"practice");
		treeCourseInfo = (TreeCourseInfo) getIntent().getSerializableExtra(
				"course_tree");
		title = getIntent().getStringExtra("title");
		courseId = getIntent().getStringExtra("courseId");
		playlist = new Playlist(practices);
		playEngineImpl.openPlaylist(playlist);
		line = (ImageView) findViewById(R.id.line);
		this.layInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initView();
		initData();
		scroll_bt.setOnTouchListener(this);
	}

	public void initView() {
		if (null == this.view)
			return;
		parentlayout = (RelativeLayout) findViewById(R.id.read_parentlayout);
		scroll_bt = (Button) findViewById(R.id.scroll_bt);
		scroll_bt.setOnTouchListener(this);
		read_scroll_pactice = (ScrollView) findViewById(R.id.read_scroll_pactice);
		read_scrollview_content = (ScrollView) findViewById(R.id.read_scrollview_content);
		read_content = (TextView) findViewById(R.id.read_content);
		read_question_layout = (LinearLayout) findViewById(R.id.read_question_layout);
		submit_bt = (ImageButton) findViewById(R.id.submit_bt);
		submit_bt.setOnClickListener(this);
		next_practice_bt = (Button) findViewById(R.id.next_practice_bt);
		next_practice_bt.setOnClickListener(this);
		last_practice_bt = (Button) findViewById(R.id.last_practice_bt);
		last_practice_bt.setOnClickListener(this);
		currTime_tv = (TextView) this.view.findViewById(R.id.currTime_tv);
		totalTime_tv = (TextView) this.view.findViewById(R.id.totalTime_tv);
		play_btn = (ImageView) this.view.findViewById(R.id.play_btn);
		play_btn.setOnClickListener(this);
		seekbar = (SeekBar) this.view.findViewById(R.id.seekbar);
		seekbar.setOnSeekBarChangeListener(this);
		listen = (LinearLayout) findViewById(R.id.listen);
		download = (ImageView) findViewById(R.id.download);
		download.setOnClickListener(this);
		playTime_ll = (LinearLayout) findViewById(R.id.playTime_ll);
		progress = (LinearLayout) findViewById(R.id.progress);
		listen_rl = (LinearLayout) findViewById(R.id.listen_rl);
		if (practices.get(0).getAudioUrl() != null
				&& !"".equals(practices.get(0).getAudioUrl())) {// 有音频
			read_content.setVisibility(View.GONE);
			read_scrollview_content
					.setLayoutParams(new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT));
		} else {
			read_content.setVisibility(View.VISIBLE);
			read_scrollview_content
					.setLayoutParams(new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT));
			progress.setVisibility(View.GONE);
			playTime_ll.setVisibility(View.GONE);
		}
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			parentHigh = parentlayout.getHeight();
			btHigh = v.getHeight();
			mStartY = (int) event.getRawY();
			mStartTop = v.getTop();
			lHigh = line.getHeight();
			break;
		case MotionEvent.ACTION_MOVE:
			float offset = event.getRawY() - mStartY;
			int top = (int) (mStartTop + offset);
			top = top > btHigh / 2 + 100 ? top : btHigh / 2 + 100;// 控制button不得拉得太高
			top = top < (parentHigh - (btHigh / 2) - 100) ? top : parentHigh
					- (btHigh / 2) - 100;// 控制button不得拉得太低
			v.layout(v.getLeft(), top, v.getRight(), top + btHigh);
			listen.layout(listen.getLeft(), listen.getTop(), listen.getRight(),
					top + btHigh / 2 + lHigh / 2);
			read_content.layout(read_content.getLeft(), read_content.getTop(),
					read_content.getRight(), top + btHigh / 2 - lHigh / 2);
			read_scrollview_content.layout(read_scrollview_content.getLeft(),
					read_scrollview_content.getTop(),
					read_scrollview_content.getRight(), top + btHigh / 2
							- lHigh / 2);
			read_scroll_pactice.layout(read_scroll_pactice.getLeft(), top
					+ btHigh / 2 + lHigh / 2, read_scroll_pactice.getRight(),
					read_scroll_pactice.getBottom());
			line.layout(line.getLeft(), top + btHigh / 2 - lHigh / 2,
					line.getRight(), top + btHigh / 2 + lHigh / 2);

		}
		return false;
	}

	public void initData() {
		if (practices != null) {
			for (int i = 0; i < practices.size(); i++) {
				practice = practices.get(i);

				if (practice.getQuestionJson() != null
						&& practice.getQuestionJson().size() > 0
						&& practice.getQuestionJson().get(0).getUserOpt() == 0) {// 该道题用户无答案
					selectedIndex = i;
					actionBarTitle.setText(practice.getName() + " "
							+ (selectedIndex + 1) + "/" + practices.size());
					playlist.select(selectedIndex);
					break;
				}
			}
			if (practices.size() > selectedIndex) {
				initialInfo(practices.get(selectedIndex));
			}
		}
	}

	/**
	 * 初始化习题列表
	 * 
	 * @param practice2
	 */
	private void initialInfo(Practice practice) {
		actionBarTitle.setText(practice.getName() + " " + (selectedIndex + 1)
				+ "/" + practices.size());
		optioncomments = new ArrayList<View>();
		read_content.setText(practice.getContent());
		if (practice.getAudioUrl() != null
				&& !"".equals(practice.getAudioUrl())) {// 有音频
			read_content.setText(practice.getContent());
			read_content.setVisibility(View.GONE);
		} else {
			progress.setVisibility(View.GONE);
			playTime_ll.setVisibility(View.GONE);
			listen_rl.setVisibility(View.GONE);
		}
		setScrollButtomPosition();
		questionlist = practice.getQuestionJson();
		read_question_layout.removeAllViews();
		optioncomments.clear();
		for (int i = 0; i < questionlist.size(); i++) {
			final Question question = questionlist.get(i);
			View contentView = this.layInflater.inflate(
					R.layout.execise_question, null);
			TextView question_tv = (TextView) contentView
					.findViewById(R.id.question_tv);
			if (practice.getAudioUrl() != null
					&& !"".equals(practice.getAudioUrl())) {// 有音频
				question_tv.setText((i + 1) + ".");
			} else {
				question_tv.setText((i + 1) + "." + question.getTitle());
			}
			read_question_layout.addView(contentView);

			RadioGroup group = new RadioGroup(view.getContext());
			group.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
			group.setOrientation(LinearLayout.VERTICAL);
			group.setPadding(74, 0, 40, 50);
			group.setBackgroundResource(R.drawable.question_bg2);
			read_question_layout.addView(group);

			// 注解部分
			View commentView = this.layInflater.inflate(
					R.layout.option_comment, null);
			// 答案
			final TextView answer_tv = (TextView) commentView
					.findViewById(R.id.answer_tv);
			// 视频播放
			ImageButton practice_vedio_study = (ImageButton) commentView
					.findViewById(R.id.practice_vedio_study);
			// 视频讲解判断
			if (question.getVideoExplainUrl() == null
					|| "".equals(question.getVideoExplainUrl())) {
				practice_vedio_study.setVisibility(View.GONE);
			} else {
				practice_vedio_study.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						Intent intent = new Intent(getApplicationContext(),
								VedioPlayActivity.class);
						intent.putExtra("flag", "1");
						intent.putExtra("vediourl",
								question.getVideoExplainUrl());
						startActivity(intent);
					}
				});
			}
			// 答案
			answer_tv.setText(Option.getOptDisplayText(question.getRightOpt()));
			final TextView resolve_tv = (TextView) commentView
					.findViewById(R.id.resolve_tv);
			// 解释
			resolve_tv.setText(question.getTextExplain());
			read_question_layout.addView(commentView);
			commentView.setVisibility(View.GONE);// 不显示
			optioncomments.add(commentView);// 放入列表中

			String correctStr = "";
			boolean isCorrect = false;
			// 判断是不是做完了
			boolean isfinish = question.getUserOpt() != 0;
			final List<Option> options = question.getAnswerJsons();
			// 设置四个选项
			if (options != null && options.size() > 0) {
				for (int oindex = 0; oindex < options.size(); oindex++) {
					final Option vmodel = options.get(oindex);
					LogUtil.e(vmodel.toString());
					if (vmodel.getOption() == question.getUserOpt()) {
						correctStr = vmodel.getContent();
					}
					final RadioButton rbtm = new RadioButton(view.getContext());
					// TODO 改变checkbox的选中的样式
					rbtm.setCompoundDrawablePadding(10);
					rbtm.setCompoundDrawablesWithIntrinsicBounds(
							R.drawable.radiobutton, 0, 0, 0);
					rbtm.setButtonDrawable(android.R.color.transparent);
					rbtm.setBackgroundResource(0);
					rbtm.setLayoutParams(new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT));
					rbtm.setPadding(5, 0, 0, 20);
					rbtm.setTextSize(14);
					rbtm.setText(Option.getOptDisplayText(vmodel.getOption())
							+ ")" + vmodel.getContent());
					rbtm.setTextColor(Color.BLACK);
					if (vmodel.getOption() == question.getUserOpt()) {// 用户已做过该题目
						isCorrect = vmodel.getOption() == question
								.getRightOpt();

						rbtm.setChecked(true);
						if (isCorrect) {
							rbtm.setPadding(5, 0, 0, 20);
							rbtm.setCompoundDrawablesWithIntrinsicBounds(
									R.drawable.right, 0, 0, 0);
						} else {
							resolve_tv.setTextColor(Color.RED);
							rbtm.setCompoundDrawablesWithIntrinsicBounds(
									R.drawable.wrong, 0, 0, 0);
						}
						rbtm.setButtonDrawable(android.R.color.transparent);
						rbtm.setBackgroundResource(0);
					}
					if (!isfinish) {
						rbtm.setCompoundDrawablesWithIntrinsicBounds(
								R.drawable.radiobutton, 0, 0, 0);
						rbtm.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								question.setUserOpt(vmodel.getOption());
								if (vmodel.getOption() == question
										.getRightOpt()) {// 用户答题正确
									answer_tv.setTextColor(Color.BLACK);
								} else {
									answer_tv.setTextColor(Color.RED);
								}
							}
						});
					} else {
						rbtm.setEnabled(false);
						rbtm.setClickable(false);
					}
					group.addView(rbtm);
				}
			}
			View shadow = this.layInflater.inflate(R.layout.shadow, null);
			read_question_layout.addView(shadow);
			if (!isfinish) {// 用户题目未完成
				last_practice_bt.setVisibility(View.GONE);
				next_practice_bt.setVisibility(View.GONE);
				commentView.setVisibility(View.GONE);
				submit_bt.setVisibility(View.VISIBLE);
			} else {// 已经完成
				submit_bt.setVisibility(View.GONE);
				showComment(contentView, question);
			}
		}
	}

	private void showComment(View contentView, Question question) {
		for (int i = 0; i < this.optioncomments.size(); i++) {
			View tv = this.optioncomments.get(i);
			tv.setVisibility(View.VISIBLE);
		}

		if (practice.getAudioUrl() != null
				&& !"".equals(practice.getAudioUrl())) {// 有音频
			TextView question_tv = (TextView) contentView
					.findViewById(R.id.question_tv);
			String title = question_tv.getText().toString();
			question_tv.setText(title + question.getTitle());
			LogUtil.e(question.getTitle());
		}
		this.read_content.setVisibility(View.VISIBLE);
		this.last_practice_bt.setVisibility(View.VISIBLE);
		this.next_practice_bt.setVisibility(View.VISIBLE);
		if (selectedIndex == 0) {
			this.last_practice_bt.setVisibility(View.INVISIBLE);
		}
		if (selectedIndex == practices.size() - 1) {
			this.next_practice_bt.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 原来的submit的方法
	 */
	private void submit() {
		double progress = (selectedIndex + 1) / (double) practices.size();

		if (NetWorkUtil.isNetConnected(this)) {
			// 有网络连接的时候
			Map<String, Object> recordInfo = new LinkedHashMap<String, Object>();

			recordInfo.put("token", AppContext.getInstance().getLoginInfo()
					.getToken());
			recordInfo.put("courseId", courseId);
			recordInfo.put("progress", progress);
			List<Map<String, String>> recdJsons = new ArrayList<Map<String, String>>();
			for (int i = 0; i < questionlist.size(); i++) {
				Question question = questionlist.get(i);
				Map<String, String> questionJson = new LinkedHashMap<String, String>();
				questionJson.put("userOpt", question.getUserOpt() + "");
				questionJson.put("qid", question.getId() + "");
				recdJsons.add(questionJson);
			}
			recordInfo.put("questionRecordJsons", recdJsons);
			LogUtil.e("练习提交封装的map：" + recordInfo.toString());
			new PracticeApi(this).PracriceRecord(recordInfo);
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("courseId", courseId);
			map.put("token", AppContext.getInstance().getLoginInfo().getToken());
			new PracticeApi(this).queryPractice(map);
			getSupportLoaderManager().initLoader(Integer.parseInt(courseId),
					null, this);
		} else {
			// 无网络连接的时候,将改变过后的数据插入到数据库中
			practices.get(selectedIndex).setQuestionJson(questionlist);
			PracticeInfo practiceInfo = new PracticeInfo();
			practiceInfo.setErrorCode(0);
			practiceInfo.setProgress(progress + "");
			practiceInfo.setPracticeJsons(practices);
			String practiceJsons = mGson.toJson(practiceInfo);
			ContentValues values = new ContentValues();
			values.put(PracticeTable.ID, courseId);
			values.put(PracticeTable.PRACTICE_JSON, practiceJsons);
			mPracticeDao.insertPractice(values);
		}

	}

	@Override
	public void onClick(View v) {
		HashMap<String, String> map = new HashMap<String, String>();
		super.onClick(v);
		switch (v.getId()) {
		case R.id.submit_bt:
			map.put("course_name", title);
			MobclickAgent.onEvent(this, "点击做题提交按钮", map);
			read_scroll_pactice.scrollTo(10, 10);
			setScrollButtomPosition();
			for (Question question : questionlist) {
				if (question != null && question.getUserOpt() == 0) {
					Toast.makeText(getApplicationContext(), "您还有没有做完的题目哦~!",
							Toast.LENGTH_LONG).show();
					return;
				}
			}
			submit_bt.setVisibility(View.GONE);
			practice = practices.get(selectedIndex);
			initialInfo(practice);
			submit();
			break;
		case R.id.play_btn:
			map.put("course_name", title);
			MobclickAgent.onEvent(this, "点击音频播放按钮", map);
			if (playEngineImpl.isPlaying()) {
				playEngineImpl.pause();
			} else {
				playEngineImpl.play();
			}

			break;

		case R.id.last_practice_bt: {
			playEngineImpl.prev();
			selectedIndex--;
			practice = practices.get(selectedIndex);
			read_scroll_pactice.scrollTo(10, 10);
			setScrollButtomPosition();
			initialInfo(practice);
			break;
		}
		case R.id.next_practice_bt:
			playEngineImpl.next();
			selectedIndex++;
			read_scroll_pactice.scrollTo(10, 10);
			if (selectedIndex < practices.size()) {// 还有题目
				practice = practices.get(selectedIndex);
				initialInfo(practice);
				setScrollButtomPosition();
			} else {// 题目已完成
				next_practice_bt.setVisibility(View.GONE);
			}
			break;
		case R.id.download:
			map.put("course_name", title);
			MobclickAgent.onEvent(this, "进入练习音频下载页面", map);
			Intent intent = new Intent(PracticeDetialActivity.this,
					ResDownloadActivity.class);
			intent.putExtra("title", title);
			intent.putExtra("course_tree", treeCourseInfo);
			intent.putExtra("practice", practices);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onTrackStart() {
		play_btn.setBackgroundResource(R.drawable.player_suspend);
		return true;
	}

	@Override
	public void onTrackChanged(int totalSeconds) {
		seekbar.setProgress(0);
		SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");// 初始化Formatter的转换格式。
		String hms = formatter.format(totalSeconds);
		seekbar.setMax(totalSeconds);
		totalTime_tv.setText(hms + "");
		play_btn.setBackgroundResource(R.drawable.player_suspend);
	}

	@Override
	public void onTrackProgress(int seconds) {
		SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");// 初始化Formatter的转换格式。
		String hms = formatter.format(seconds);
		seekbar.setProgress(seconds);
		currTime_tv.setText(hms + "");

	}

	@Override
	public void onTrackBuffering(int percent) {

	}

	@Override
	public void onTrackStop() {
		seekbar.setProgress(0);
		play_btn.setBackgroundResource(R.drawable.player_play);
	}

	@Override
	public void onTrackPause() {
		play_btn.setBackgroundResource(R.drawable.player_play);
	}

	@Override
	public void onTrackStreamError() {

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		if (!playEngineImpl.isPlaying()) {
			playEngineImpl.play();
		}

		playEngineImpl.seekTo(seekBar.getProgress());

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		playEngineImpl.stop();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return mPracticeDao.getCursorLoader(this, arg0 + "");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor == null || cursor.getCount() == 0) {
			String practiceInfoJson = ReadUtil.readFile(this,
					"wwyj_offline/practicequery/" + courseId);
			PracticeInfo practiceInfo = mGson.fromJson(practiceInfoJson,
					PracticeInfo.class);
			if (practiceInfo.getPracticeJsons() != null
					&& !practiceInfo.getPracticeJsons().isEmpty()) {
				practices.clear();
				practices.addAll((ArrayList<Practice>) practiceInfo
						.getPracticeJsons());
				LogUtil.e("更新过后的练习集合：" + practice.toString());
			}
		} else if (cursor.moveToNext()) {
			String practiceInfoJson = cursor.getString(cursor
					.getColumnIndex(PracticeTable.PRACTICE_JSON));
			PracticeInfo practiceInfo = mGson.fromJson(practiceInfoJson,
					PracticeInfo.class);
			if (practiceInfo.getPracticeJsons() != null
					&& !practiceInfo.getPracticeJsons().isEmpty()) {
				practices.clear();
				practices.addAll((ArrayList<Practice>) practiceInfo
						.getPracticeJsons());
				LogUtil.e("更新过后的练习集合：" + practice.toString());
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}

	@Override
	protected void onResume() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("courseId", courseId);
		map.put("token", AppContext.getInstance().getLoginInfo().getToken());
		new PracticeApi(PracticeDetialActivity.this).queryPractice(map);
		super.onResume();
	}

	private void setScrollButtomPosition() {
		if (practice.getQuestionJson().get(0).getUserOpt() == 0
				&& practice.getAudioUrl() != null
				&& !"".equals(practice.getAudioUrl())) {// 有音频且没做过该题目
			read_scrollview_content
					.setLayoutParams(new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT));
		} else {
			LayoutParams lp1 = (LayoutParams) read_scrollview_content
					.getLayoutParams();
			if (practice.getAudioUrl() != null
					&& !"".equals(practice.getAudioUrl())) {// 有音频
				lp1.height = 300;
			} else {
				lp1.height = 400;
			}
		}
	}
}