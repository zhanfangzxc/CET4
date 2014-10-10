package com.yingshibao.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.yingshibao.cet4.constants.ParentCourseTable;
import com.yingshibao.cet4.contentprovider.Cet4ContentProvider;

public class ParentCourseDao extends BaseDao {

	public ParentCourseDao(Context context) {
		super(context);

	}

	@Override
	protected Uri getContentUri() {
		return Uri.parse(Cet4ContentProvider.SCHEME
				+ Cet4ContentProvider.AUTHORITY + "/"
				+ ParentCourseTable.TABLE_NAME);
	}

	public CursorLoader getCursorLoader(Context context, String id) {

		return getCursorLoader(context, null, "id=?", new String[] { id }, null);
	}

	/**
	 * 插入课程
	 * 
	 * @param values
	 */
	public void insertCourse(ContentValues values) {
		Cursor cursor = query(null, ParentCourseTable.ID + "=?",
				new String[] { (String) values.get(ParentCourseTable.ID) },
				null);
		if (cursor != null && cursor.getCount() > 0) {
			// 更新用户
			update(values, ParentCourseTable.ID + "=?",
					new String[] { (String) values.get(ParentCourseTable.ID) });
		} else {
			insert(values);
		}

	}
}
