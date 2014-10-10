package com.yingshibao.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.yingshibao.cet4.bean.User;
import com.yingshibao.cet4.constants.UserTable;
import com.yingshibao.cet4.contentprovider.Cet4ContentProvider;

public class UserDao extends BaseDao {

	public UserDao(Context context) {
		super(context);

	}

	@Override
	protected Uri getContentUri() {
		return Uri.parse(Cet4ContentProvider.SCHEME
				+ Cet4ContentProvider.AUTHORITY + "/" + UserTable.TABLE_NAME);
	}

	/**
	 * 插入用户
	 * 
	 * @param values
	 */
	public void insertUser(ContentValues values) {
		Cursor cursor = query(null, UserTable.USER_NAME + "=?",
				new String[] { (String) values.get(UserTable.USER_NAME) }, null);
		if (cursor != null && cursor.getCount() > 0) {
			// 更新用户
			update(values, UserTable.USER_NAME + "=?",
					new String[] { (String) values.get(UserTable.USER_NAME) });
		} else {
			insert(values);
		}
	}

	// 获取用户名帐号和密码
	public User getUser() {
		Cursor cursor = query(null, new String[] { UserTable.USER_NAME,
				UserTable.USER_PASSWORD }, null, null, null);
		User user = new User();
		if (cursor.moveToNext()) {
			user.setPassword(cursor.getString(cursor
					.getColumnIndex(UserTable.USER_PASSWORD)));
			user.setUsername(cursor.getString(cursor
					.getColumnIndex(UserTable.USER_NAME)));

		}
		return user;
	}

	/**
	 * 删除用户
	 */
	public void deleteUser() {
		delete();
	}
}
