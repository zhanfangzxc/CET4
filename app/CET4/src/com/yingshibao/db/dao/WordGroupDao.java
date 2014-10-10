package com.yingshibao.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.yingshibao.cet4.constants.WordGroupTable;
import com.yingshibao.cet4.contentprovider.Cet4ContentProvider;

public class WordGroupDao extends BaseDao {

	public WordGroupDao(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Uri getContentUri() {
		return Uri.parse(Cet4ContentProvider.SCHEME
				+ Cet4ContentProvider.AUTHORITY + "/"
				+ WordGroupTable.TABLE_NAME);
	}

	public CursorLoader getCursorLoader(Context context, String id) {
		return getCursorLoader(context, null, "id=?", new String[] { id }, null);
	}

	/**
	 * 插入单词分组信息
	 * 
	 * @param values
	 */
	public void insertWordGroup(ContentValues values) {
		Cursor cursor = query(null, WordGroupTable.ID + "=?",
				new String[] { (String) values.get(WordGroupTable.ID) }, null);
		if (cursor != null && cursor.getCount() > 0) {
			update(values, WordGroupTable.ID + "=?",
					new String[] { (String) values.get(WordGroupTable.ID) });
		} else {
			insert(values);
		}

	}
}
