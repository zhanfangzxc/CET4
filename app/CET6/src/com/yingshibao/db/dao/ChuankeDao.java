package com.yingshibao.db.dao;

import android.content.Context;
import android.net.Uri;

import com.yingshibao.cet4.constants.ChuanKeCourseTable;
import com.yingshibao.cet4.contentprovider.Cet4ContentProvider;

public class ChuankeDao extends BaseDao {

	public ChuankeDao(Context context) {
		super(context);
	}

	@Override
	protected Uri getContentUri() {
		return Uri.parse(Cet4ContentProvider.SCHEME
				+ Cet4ContentProvider.AUTHORITY + "/"
				+ ChuanKeCourseTable.TABLE_NAME);
	}

}
