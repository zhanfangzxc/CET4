package com.yingshibao.cet4.adapter;

import java.util.List;

import com.yingshibao.cet4.R;
import com.yingshibao.cet4.bean.WordGroup;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * 单词分组界面adapter
 * 
 * @author zhaoshan
 * 
 */
public class WordGroupAdapter extends BaseAdapter {

	private List<WordGroup> groups;

	private Context context;

	public WordGroupAdapter(List<WordGroup> groups, Context context) {
		this.groups = groups;
		this.context = context;
	}

	@Override
	public int getCount() {
		return this.groups.size();
	}

	@Override
	public Object getItem(int position) {
		return this.groups.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		WordGroup wordGroup = this.groups.get(position);
		WordGroup prewordGroup = null;// 前一个分组
		if (position > 0) {
			prewordGroup = this.groups.get(position - 1);
		}
		View view = convertView;
		ViewHolder holder;
		if (null == view) {
			view = LayoutInflater.from(this.context).inflate(
					R.layout.word_group_adapter, null);
			holder = new ViewHolder();
			holder.groupnum_tv = (TextView) view.findViewById(R.id.groupnum_tv);
			holder.group_item_layout = (RelativeLayout) view
					.findViewById(R.id.group_item_layout);
			holder.group_num_layout = (LinearLayout) view
					.findViewById(R.id.group_num_layout);
			holder.progress_layout = (LinearLayout) view
					.findViewById(R.id.progress_layout);
			holder.total_tv = (TextView) view.findViewById(R.id.total_tv);
			holder.progress_tv = (TextView) view.findViewById(R.id.progress_tv);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.total_tv.setText("共" + wordGroup.getTotalWordCount() + "词");
		holder.groupnum_tv.setText(position + 1 + "");
		holder.progress_tv.setText((int) (wordGroup.getProgress() * wordGroup
				.getTotalWordCount()) + "/" + wordGroup.getTotalWordCount());
		if (wordGroup.getProgress() != 0 || prewordGroup == null
				|| prewordGroup.getProgress() == 1) {//
			if (wordGroup.getProgress() == 1) {
				holder.total_tv.setText("完成");
				holder.progress_tv.setVisibility(View.GONE);
			}
			// 已经有学习进度的，或者是第一组的，或者前一组已经学完的
			holder.group_item_layout
					.setBackgroundResource(R.drawable.word_unlock_bg);
			holder.progress_tv.setVisibility(View.VISIBLE);
			holder.total_tv.setVisibility(View.VISIBLE);
			holder.total_tv.setTextColor(Color.WHITE);
		} else {
			holder.group_item_layout
					.setBackgroundResource(R.drawable.word_lock_bg);
			holder.progress_tv.setVisibility(View.GONE);
			holder.total_tv.setVisibility(view.GONE);
		}
		return view;
	}

	class ViewHolder {
		RelativeLayout group_item_layout;
		LinearLayout group_num_layout;
		LinearLayout progress_layout;
		TextView groupnum_tv;
		TextView total_tv;
		TextView progress_tv;
	}
}
