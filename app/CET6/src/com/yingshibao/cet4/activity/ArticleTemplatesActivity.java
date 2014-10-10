package com.yingshibao.cet4.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.bean.Article;
import com.yingshibao.cet6.R;

public class ArticleTemplatesActivity extends BaseActivity {
	private ArrayList<Article> articles;
	private ListView list;
	SharedPreferences preferences = PreferenceManager
			.getDefaultSharedPreferences(AppContext.getInstance());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level3_course);
		actionBarTitle.setText("文章模板");
		list = (ListView) findViewById(R.id.lv_level3course);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://www.yingshibao.com:8080/Articles/Model",
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, String content) {

						preferences.edit().putString("article", content)
								.commit();
						initList(content);

					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {

						initList(preferences.getString("article", content));
					}

				});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
	}

	private void initList(String content) {
		Gson gson = new Gson();
		articles = gson.fromJson(content, new TypeToken<ArrayList<Article>>() {
		}.getType());

		if (articles != null) {
			final ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < articles.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("level3_text", articles.get(i).getTitle());
				data.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
					data, R.layout.layout_level3_item,
					new String[] { "level3_text" },
					new int[] { R.id.level3_tv });
			list.setAdapter(adapter);
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("title", articles.get(position).getTitle());
					MobclickAgent.onEvent(ArticleTemplatesActivity.this,
							"查看作文模板", map);
					Intent intent = new Intent(ArticleTemplatesActivity.this,
							ArticleContentActivity.class);
					intent.putExtra("content", articles.get(position)
							.getContent());
					intent.putExtra("title", articles.get(position).getTitle());
					startActivity(intent);
				}
			});
		}
	}
}
