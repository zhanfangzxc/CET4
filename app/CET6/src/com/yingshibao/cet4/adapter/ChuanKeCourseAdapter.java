package com.yingshibao.cet4.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yingshibao.cet4.bean.DataBean;
import com.yingshibao.cet6.R;

public class ChuanKeCourseAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<DataBean> mDataBeans;

	public ChuanKeCourseAdapter(Context context, ArrayList<DataBean> dataBeans) {
		mContext = context;
		mDataBeans = dataBeans;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	class ViewHolder {
		TextView className;
		ImageButton arrow_iv;
	}

	@Override
	public int getCount() {
		return mDataBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final DataBean dataBean = mDataBeans.get(position);
		View view = null;
		ViewHolder holder = null;
		if (convertView == null) {
			view = mInflater.inflate(R.layout.layout_chuanke_course_item, null,
					false);
			holder = new ViewHolder();
			holder.className = (TextView) view.findViewById(R.id.tv_classname);
			holder.arrow_iv = (ImageButton) view.findViewById(R.id.arrow_iv);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		String className = dataBean.getCourseName();
		holder.className.setText(dataBean.getCourseName());
		// TODO 点击课程名称显示具体的课程信息
		return view;
	}
}
