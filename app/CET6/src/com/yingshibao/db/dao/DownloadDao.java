package com.yingshibao.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mozillaonline.providers.downloads.DownloadProvider;
import com.mozillaonline.providers.downloads.DownloadProvider.DatabaseHelper;
import com.yingshibao.cet4.AppContext;

public class DownloadDao {

	private DatabaseHelper databaseHelper;

	public DownloadDao() {
		// TODO Auto-generated constructor stub
		Context context = AppContext.getInstance();
		databaseHelper = new DownloadProvider.DatabaseHelper(context);

	}

	public String getFileName(String url) {
		String fileName = null;
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor cursor = db.query("downloads", new String[] { "title" },
				"uri=?", new String[] { url }, null, null, null);
		if (cursor.moveToNext()) {
			fileName = cursor.getString(cursor.getColumnIndex("title"));
		}
		return fileName;
	}
}
