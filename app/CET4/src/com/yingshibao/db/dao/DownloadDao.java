package com.yingshibao.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mozillaonline.providers.downloads.DownloadInfo;
import com.mozillaonline.providers.downloads.DownloadProvider;
import com.mozillaonline.providers.downloads.DownloadProvider.DatabaseHelper;
import com.yingshibao.cet4.AppContext;

public class DownloadDao {

	private DatabaseHelper databaseHelper;

	public DownloadDao() {
		Context context = AppContext.getInstance();
		databaseHelper = new DownloadProvider.DatabaseHelper(context);

	}

	public DownloadInfo getFileName(String url) {
		DownloadInfo downloadInfo = null;
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor cursor = db.query("downloads", null, "uri=?",
				new String[] { url }, null, null, null);
		if (cursor.moveToNext()) {
			downloadInfo = new DownloadInfo();
			downloadInfo.mTitle = cursor.getString(cursor
					.getColumnIndex("title"));
			downloadInfo.mStatus = cursor.getInt(cursor
					.getColumnIndex("status"));
		}
		return downloadInfo;
	}
}
