package com.yingshibao.cet4.adapter;

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yingshibao.cet6.R;

public class DownloadFinishAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<File> mFiles;

	public DownloadFinishAdapter(Context context, ArrayList<File> file) {
		mContext = context;
		mFiles = file;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFiles.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = View
				.inflate(mContext, R.layout.layout_download__item, null);
		TextView filename = (TextView) view.findViewById(R.id.tv_filename);
		filename.setText(mFiles.get(position).getName());
		ImageView delete = (ImageView) view.findViewById(R.id.iv_delete);
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new Builder(mContext);
				builder.setMessage("确定要删除吗？")
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}
								})
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										mFiles.get(position).delete();
										mFiles.remove(position);
										notifyDataSetChanged();
									}
								}).show();

			}
		});
		return view;
	}
}
