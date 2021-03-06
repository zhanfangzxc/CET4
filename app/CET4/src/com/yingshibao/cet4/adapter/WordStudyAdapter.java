package com.yingshibao.cet4.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.yingshibao.cet4.R;
import com.yingshibao.cet4.bean.Word;

public class WordStudyAdapter extends BaseAdapter {

	private Context context;

	private List<Word> words;

	public WordStudyAdapter(Context context, List<Word> words) {
		this.context = context;
		this.words = words;
	}

	@Override
	public int getCount() {
		return words.size();
	}

	@Override
	public Object getItem(int position) {
		return words.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(
					R.layout.word_study_adapter, null);
			holder = new ViewHolder();
			holder.content_tv = (TextView) view.findViewById(R.id.content_tv);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		Word word = words.get(position);
		SpannableString spannableString1 = new SpannableString(position+1+"."+"\t"
				+ word.getName());
		holder.content_tv.setText(spannableString1);
		if (word.getUnknownFlag() == 0) {
			holder.content_tv.setTextColor(Color.BLUE);
		} else if (word.getUnknownFlag() == 1) {
			holder.content_tv.setTextColor(Color.RED);
		} else {
			holder.content_tv.setTextColor(Color.DKGRAY);
		}
		return view;
	}

	private class ViewHolder {
		public TextView content_tv;
	}

}
