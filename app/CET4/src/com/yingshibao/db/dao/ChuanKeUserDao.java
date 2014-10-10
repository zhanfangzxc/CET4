package com.yingshibao.db.dao;

import com.yingshibao.cet4.constants.ChuanKeUserTable;
import com.yingshibao.cet4.contentprovider.Cet4ContentProvider;

import android.content.Context;
import android.net.Uri;

public class ChuanKeUserDao extends BaseDao {

	public ChuanKeUserDao(Context context) {
		super(context);
	}

	@Override
	protected Uri getContentUri() {
		return Uri.parse(Cet4ContentProvider.SCHEME
				+ Cet4ContentProvider.AUTHORITY + "/"
				+ ChuanKeUserTable.TABLE_NAME);
	}

}
