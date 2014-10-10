package com.yingshibao.cet4.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.yingshibao.cet4.constants.ChuanKeCourseTable;
import com.yingshibao.cet4.constants.ChuanKeUserTable;
import com.yingshibao.cet4.constants.DownloadTable;
import com.yingshibao.cet4.constants.ParentCourseTable;
import com.yingshibao.cet4.constants.PracticeTable;
import com.yingshibao.cet4.constants.UnKnownWordsTable;
import com.yingshibao.cet4.constants.UserTable;
import com.yingshibao.cet4.constants.WordGroupTable;
import com.yingshibao.cet4.constants.WordInfoTable;
import com.yingshibao.db.DBHelper;

/**
 * 
 * @author malinkang 2014-1-14 下午5:07:29
 * 
 */

public class Cet4ContentProvider extends ContentProvider {

	public static final String SCHEME = "content://";

	public static String AUTHORITY = "com.malinkang.cet4";

	private final static int ACCOUNT = 0;
	private final static int PARENT_COURSE = 1;
	private final static int PRACTICE = 2;
	private final static int WORD_INFO = 3;
	private final static int WORD_GROUP = 4;
	private final static int UNKNOWN_WORDS = 5;
	private final static int DOWNLOAD = 6;
	private final static int CHUANKE_COURSE = 7;
	private final static int BIND_USER = 8;
	private static UriMatcher mUriMatcher;

	static {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI(AUTHORITY, UserTable.TABLE_NAME, ACCOUNT);
		mUriMatcher.addURI(AUTHORITY, ParentCourseTable.TABLE_NAME,
				PARENT_COURSE);
		mUriMatcher.addURI(AUTHORITY, PracticeTable.TABLE_NAME, PRACTICE);
		mUriMatcher.addURI(AUTHORITY, WordInfoTable.TABLE_NAME, WORD_INFO);
		mUriMatcher.addURI(AUTHORITY, WordGroupTable.TABLE_NAME, WORD_GROUP);
		mUriMatcher.addURI(AUTHORITY, UnKnownWordsTable.TABLE_NAME,
				UNKNOWN_WORDS);
		mUriMatcher.addURI(AUTHORITY, DownloadTable.TABLE_NAME, DOWNLOAD);
		mUriMatcher.addURI(AUTHORITY, ChuanKeCourseTable.TABLE_NAME,
				CHUANKE_COURSE);
		mUriMatcher.addURI(AUTHORITY, ChuanKeUserTable.TABLE_NAME, BIND_USER);
	}

	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = getWritableDatabase();
		String table = matchTable(uri);
		Cursor cursor = db.query(table, projection, selection, selectionArgs,
				null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = getWritableDatabase();
		String table = matchTable(uri);
		long rowId = db.insert(table, null, values);
		getContext().getContentResolver().notifyChange(uri, null);
		return ContentUris.withAppendedId(uri, rowId);

	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		SQLiteDatabase db = getWritableDatabase();
		String table = matchTable(uri);
		for (ContentValues contentValues : values) {
			db.insert(table, null, contentValues);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return values.length;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = getWritableDatabase();
		String table = matchTable(uri);
		int account = db.delete(table, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return account;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = getWritableDatabase();
		String table = matchTable(uri);
		int account = db.update(table, values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return account;
	}

	private String matchTable(Uri uri) {
		String table = null;
		switch (mUriMatcher.match(uri)) {
		case ACCOUNT:
			table = UserTable.TABLE_NAME;
			break;
		case PARENT_COURSE:
			table = ParentCourseTable.TABLE_NAME;
			break;
		case PRACTICE:
			table = PracticeTable.TABLE_NAME;
			break;
		case WORD_INFO:
			table = WordInfoTable.TABLE_NAME;
			break;
		case WORD_GROUP:
			table = WordGroupTable.TABLE_NAME;
			break;
		case UNKNOWN_WORDS:
			table = UnKnownWordsTable.TABLE_NAME;
			break;
		case DOWNLOAD:
			table = DownloadTable.TABLE_NAME;
			break;
		case CHUANKE_COURSE:
			table = ChuanKeCourseTable.TABLE_NAME;
			break;
		case BIND_USER:
			table = ChuanKeUserTable.TABLE_NAME;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		return table;
	}

	private SQLiteDatabase getWritableDatabase() {
		return DBHelper.getInstance().getWritableDatabase();
	}

}
