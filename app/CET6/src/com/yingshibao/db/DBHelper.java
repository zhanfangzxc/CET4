package com.yingshibao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.bean.ChuanKeUser;
import com.yingshibao.cet4.constants.ChuanKeCourseTable;
import com.yingshibao.cet4.constants.ChuanKeUserTable;
import com.yingshibao.cet4.constants.DownloadTable;
import com.yingshibao.cet4.constants.ParentCourseTable;
import com.yingshibao.cet4.constants.PracticeTable;
import com.yingshibao.cet4.constants.UnKnownWordsTable;
import com.yingshibao.cet4.constants.UserTable;
import com.yingshibao.cet4.constants.WordGroupTable;
import com.yingshibao.cet4.constants.WordInfoTable;

/**
 * 
 * @author malinkang 2014-1-16 下午2:48:49
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

	private static DBHelper instance;

	private static final String DATABASE_NAME = "yingshibao.db";
	// 数据库版本号
	private static final int DATABASE_VERSION = 1;
	// 创建用户表
	private static final String CREATE_USER_TABLE = "create table "
			+ UserTable.TABLE_NAME + "(" + BaseColumns._ID
			+ " integer primary key autoincrement," + UserTable.USER_PASSWORD
			+ " text," + UserTable.USER_NAME + " text," + UserTable.LOGIN_INFO
			+ " text" + ");";
	// 创建用户表
	private static final String CREATE_PARENT_COURSE_TABLE = "create table "
			+ ParentCourseTable.TABLE_NAME + "(" + BaseColumns._ID
			+ " integer primary key autoincrement,"
			+ ParentCourseTable.COURSE_JSON + " text," + ParentCourseTable.ID
			+ " text" + ");";
	// 创建练习表
	private static final String CREATE_PRACTICE_TABLE = "create table "
			+ PracticeTable.TABLE_NAME + "(" + BaseColumns._ID
			+ " integer primary key autoincrement,"
			+ PracticeTable.PRACTICE_JSON + " text,"
			+ PracticeTable.PRACTICE_PROCESS + " text," + PracticeTable.ID
			+ " text" + ");";

	// 创建单词分组表
	private static final String CREATE_WORD_GROUP_TABLE = "create table "
			+ WordGroupTable.TABLE_NAME + "(" + BaseColumns._ID
			+ " integer primary key autoincrement,"
			+ WordGroupTable.WORD_GROUP_JSON + " text," + WordGroupTable.ID
			+ " text" + ");";

	// 创建单词信息表
	private static final String CREATE_WORD_Info_TABLE = "create table "
			+ WordInfoTable.TABLE_NAME + "(" + BaseColumns._ID
			+ " integer primary key autoincrement,"
			+ WordInfoTable.WORD_INFO_JSON + " text," + WordInfoTable.ID
			+ " text" + ");";

	// 创建生词本表
	private static final String CREATE_UNKNOWNS__WORDS_TABLE = "create table "
			+ UnKnownWordsTable.TABLE_NAME + "(" + BaseColumns._ID
			+ " integer primary key autoincrement,"
			+ UnKnownWordsTable.UNKNOWN_WORDS_JSON + " text,"
			+ UnKnownWordsTable.ID + " text" + ");";
	// 创建生词本表
	private static final String CREATE_DOWNLOAD_TABLE = "create table "
			+ DownloadTable.TABLE_NAME + "(" + BaseColumns._ID
			+ " integer primary key autoincrement," + DownloadTable.COURSE_ID
			+ " text," + DownloadTable.DOWNLOAD_ID + " text,"
			+ DownloadTable.URL + " text," + DownloadTable.VALUE + " text"
			+ ");";

	private static final String CREATE_CHUANKE_COURSE_TABLE = "create table "
			+ ChuanKeCourseTable.TABLE_NAME + "(" + BaseColumns._ID
			+ " integer primary key autoincrement,"
			+ ChuanKeCourseTable.COURSE_INFO + " text" + ");";

	private static final String CREATE_CHUANKE_USER_TABLE = "create table "
			+ ChuanKeUserTable.TABLE_NAME + "(" + BaseColumns._ID
			+ " integer primary key autoincrement,"
			+ ChuanKeUserTable.USER_NAME + " text" + ","
			+ ChuanKeUserTable.UID + " text" + ");";

	private DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// 单例
	public synchronized static DBHelper getInstance() {
		if (instance == null) {
			instance = new DBHelper(AppContext.getInstance());
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createOtherTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	private void createOtherTable(SQLiteDatabase db) {
		db.execSQL(CREATE_USER_TABLE);
		db.execSQL(CREATE_PARENT_COURSE_TABLE);
		db.execSQL(CREATE_PRACTICE_TABLE);
		db.execSQL(CREATE_WORD_GROUP_TABLE);
		db.execSQL(CREATE_WORD_Info_TABLE);
		db.execSQL(CREATE_UNKNOWNS__WORDS_TABLE);
		db.execSQL(CREATE_DOWNLOAD_TABLE);
		db.execSQL(CREATE_CHUANKE_COURSE_TABLE);
		db.execSQL(CREATE_CHUANKE_USER_TABLE);
	}
}
