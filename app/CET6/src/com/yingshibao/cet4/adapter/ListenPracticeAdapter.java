package com.yingshibao.cet4.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.yingshibao.cet4.bean.Option;
import com.yingshibao.cet4.bean.Practice;
import com.yingshibao.cet4.bean.Question;
import com.yingshibao.cet6.R;

/**
 * 听力界面的adapter
 * 
 * @author zhaoshan
 * 
 */
public class ListenPracticeAdapter extends BaseAdapter {

	private Context context;
	private List<Practice> practiceList;
	private List<Question> questionList;
	private List<Option> optionList;

	public ListenPracticeAdapter(Context context, List<Practice> practiceList) {
		super();
		this.context = context;
		this.practiceList = practiceList;
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.listen_practice_item,
					null);
			holder.answer_title = (TextView) convertView
					.findViewById(R.id.answer_title);
			holder.answer_tv = (TextView) convertView
					.findViewById(R.id.answer_tv);
			holder.resolve_tv = (TextView) convertView
					.findViewById(R.id.resolve_tv);
			holder.resolve_title = (TextView) convertView
					.findViewById(R.id.resolve_title);
			holder.option_tv = (TextView) convertView
					.findViewById(R.id.option_tv);
			holder.play_btn = (Button) convertView.findViewById(R.id.play_btn);
			holder.tip_tv = (TextView) convertView.findViewById(R.id.tip_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		questionList = practiceList.get(position).getQuestionJson();
		for (int i = 0; i < optionList.size(); i++) {

		}
		return convertView;
	}

	private class ViewHolder {
		private TextView tip_tv;// 问题
		private TextView option_tv;// 选项
		private Button play_btn;// 视频解析
		private TextView answer_title;// 答案
		private TextView answer_tv;// 答案
		private TextView resolve_title;// 问题解析
		private TextView resolve_tv;// 问题解析
	}

}
