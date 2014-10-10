package com.yingshibao.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.yingshibao.cet4.constants.PracticeTable;
import com.yingshibao.cet4.contentprovider.Cet4ContentProvider;

public class PracticeDao extends BaseDao {

	public PracticeDao(Context context) {
		super(context);

	}

	@Override
	protected Uri getContentUri() {
		return Uri
				.parse(Cet4ContentProvider.SCHEME + Cet4ContentProvider.AUTHORITY
						+ "/" + PracticeTable.TABLE_NAME);
	}

	public CursorLoader getCursorLoader(Context context, String id) {
		return getCursorLoader(context, null, "id=?", new String[] { id }, null);
	}

	/**
	 * 插入课程
	 * 
	 * @param values
	 */
	public void insertPractice(ContentValues values) {
		Cursor cursor = query(null, PracticeTable.ID + "=?",
				new String[] { (String) values.get(PracticeTable.ID) }, null);
		if (cursor != null && cursor.getCount() > 0) {
			// 更新用户
			update(values, PracticeTable.ID + "=?",
					new String[] { (String) values.get(PracticeTable.ID) });
		} else {
			insert(values);
		}

	}
}
