package com.yingshibao.db.dao;

import com.yingshibao.cet4.constants.UnKnownWordsTable;
import com.yingshibao.cet4.constants.WordInfoTable;
import com.yingshibao.cet4.contentprovider.Cet4ContentProvider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

public class UnknowWordsDao extends BaseDao {

	public UnknowWordsDao(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Uri getContentUri() {
		return Uri.parse(Cet4ContentProvider.SCHEME
				+ Cet4ContentProvider.AUTHORITY + "/"
				+ UnKnownWordsTable.TABLE_NAME);
	}

	public CursorLoader getCursorLoader(Context context, String id) {
		return getCursorLoader(context, null, "id=?", new String[] { id }, null);
	}

	/**
	 * 插入单词信息
	 * 
	 * @param values
	 */
	public void insertUnKnownsWordsInfo(ContentValues values) {
		Cursor cursor = query(null, UnKnownWordsTable.ID + "=?",
				new String[] { (String) values.get(UnKnownWordsTable.ID) },
				null);
		if (cursor != null && cursor.getCount() > 0) {
			// 更新用户
			update(values, UnKnownWordsTable.ID + "=?",
					new String[] { (String) values.get(UnKnownWordsTable.ID) });
		} else {
			insert(values);
		}

	}

}
