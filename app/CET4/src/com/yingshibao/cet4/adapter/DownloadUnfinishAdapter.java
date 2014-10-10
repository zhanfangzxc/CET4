package com.yingshibao.cet4.adapter;

import java.text.DateFormat;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mozillaonline.providers.DownloadManager;
import com.yingshibao.cet4.R;

public class DownloadUnfinishAdapter extends CursorAdapter {
	private Context mContext;
	private Cursor mCursor;
	private Resources mResources;
	private DateFormat mDateFormat;
	private DateFormat mTimeFormat;

	final private int mTitleColumnId;
	final private int mStatusColumnId;
	final private int mTotalBytesColumnId;
	final private int mCurrentBytesColumnId;
	final private int mMediaTypeColumnId;
	final private int mDateColumnId;
	final private int mIdColumnId;
	DownloadManager mDownloadManager;

	public DownloadUnfinishAdapter(Context context, Cursor cursor,
			DownloadManager downloadManager) {
		super(context, cursor);
		mContext = context;
		mCursor = cursor;
		mDownloadManager = downloadManager;
		mResources = mContext.getResources();
		mDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		mTimeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

		mIdColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_ID);
		mTitleColumnId = cursor
				.getColumnIndexOrThrow(DownloadManager.COLUMN_TITLE);
		mStatusColumnId = cursor
				.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS);
		cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_REASON);
		mTotalBytesColumnId = cursor
				.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
		mCurrentBytesColumnId = cursor
				.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
		mMediaTypeColumnId = cursor
				.getColumnIndexOrThrow(DownloadManager.COLUMN_MEDIA_TYPE);
		mDateColumnId = cursor
				.getColumnIndexOrThrow(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP);
	}

	public void bindView(View convertView) {
		final long downloadId = mCursor.getLong(mIdColumnId);
		String title = mCursor.getString(mTitleColumnId);
		long totalBytes = mCursor.getLong(mTotalBytesColumnId);
		long currentBytes = mCursor.getLong(mCurrentBytesColumnId);
		final int status = mCursor.getInt(mStatusColumnId);

		if (title.length() == 0) {
			title = mResources.getString(R.string.missing_title);
		}
		setTextForView(convertView, R.id.tv_filename, title);
		int progress = getProgressValue(totalBytes, currentBytes);
		boolean indeterminate = status == DownloadManager.STATUS_PENDING;
		ProgressBar progressBar = (ProgressBar) convertView
				.findViewById(R.id.pb);
		final ImageView downloadBtn = (ImageView) convertView
				.findViewById(R.id.iv_download);
		ImageView deleteBtn = (ImageView) convertView
				.findViewById(R.id.iv_delete);
		progressBar.setIndeterminate(indeterminate);
		if (!indeterminate) {
			progressBar.setProgress(progress);
		}
		if (status == DownloadManager.STATUS_PAUSED) {
			downloadBtn.setImageResource(R.drawable.download_continue);
		} else {
			downloadBtn.setImageResource(R.drawable.download_pause);
		}
		deleteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDownloadManager.remove(downloadId);

			}
		});
		downloadBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (status == DownloadManager.STATUS_PAUSED) {
					mDownloadManager.resumeDownload(downloadId);
					downloadBtn.setImageResource(R.drawable.download_continue);
				} else {
					mDownloadManager.pauseDownload(downloadId);
					downloadBtn.setImageResource(R.drawable.download_pause);
				}

			}
		});
	}

	public int getProgressValue(long totalBytes, long currentBytes) {
		if (totalBytes == -1) {
			return 0;
		}
		return (int) (currentBytes * 100 / totalBytes);
	}

	private void setTextForView(View parent, int textViewId, String text) {
		TextView view = (TextView) parent.findViewById(textViewId);
		view.setText(text);
	}

	// CursorAdapter overrides

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return View.inflate(context, R.layout.layout_download_item, null);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		bindView(view);
	}
}
