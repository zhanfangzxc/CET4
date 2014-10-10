package com.yingshibao.cet4.util;
//package com.rey.app.util;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteException;
//
//import com.rey.app.AppConfig;
//import com.rey.app.AppContext;
//import com.rey.app.AppException;
//import com.rey.app.model.Course;
//import com.rey.app.model.Word;
//import com.rey.app.model.WordGroup;
//
//public class DBUtils {
//
//	public static boolean existDB(String path) {
//		SQLiteDatabase db = null;
//		try {
//			db = SQLiteDatabase.openDatabase(path, null,
//					SQLiteDatabase.OPEN_READWRITE);
//		} catch (SQLiteException e) {
//			AppException.run(e);
//		}
//		if (db != null) {
//			db.close();
//		}
//		return db != null ? true : false;
//	}
//
//	public static File getDBFile(String path, String name) {
//		File dirFile = new File(path);
//		if (!dirFile.exists())
//			dirFile.mkdirs();
//
//		// 数据库文件是否创建成功
//		boolean isFileCreateSuccess = false;
//		// 判断文件是否存在，不存在则创建该文件
//		File dbFile = new File(path + name);
//		if (!dbFile.exists()) {
//			try {
//				isFileCreateSuccess = dbFile.createNewFile();// 创建文件
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else
//			isFileCreateSuccess = true;
//
//		// 返回数据库文件对象
//		if (isFileCreateSuccess)
//			return dbFile;
//		else
//			return null;
//	}
//
//	public static SQLiteDatabase createWordDatabase() {
//		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
//				getDBFile(FileUtil.getSDPath() + AppConfig.WORD_FILE,
//						AppContext.getInstance().getUsername()+AppConfig.WORD_DB_NAME), null);
//		db.execSQL("create table IF NOT EXISTS progress(id int,current varchar(6))");
//		db.execSQL("create table IF NOT EXISTS wordgroup(id int,name varchar(10) ,totalwordcount int,current int)");//current 已经学习的个数
//		db.execSQL("create table IF NOT EXISTS words(id varchar(50),g_id int,content varchar(200), yinbiao varchar(50), comment varchar(500),example varchar(1000),voice varchar(100),isknow int,video varchar(500),issyn int)");
//		return db;
//	}
//	
//	public static void addCources(List<Course> cources){
//		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
//				getDBFile(FileUtil.getSDPath() + AppConfig.WORD_FILE,
//						AppContext.getInstance().getUsername()+AppConfig.WORD_DB_NAME), null);
//		db.beginTransaction(); // 开始事务
//		try {
//			for (Course course : cources) {
//				db.execSQL("INSERT INTO progress VALUES(?, ?)",
//						new Object[] { course.getCid(), course.getProgress()});
//			}
//			db.setTransactionSuccessful(); // 设置事务成功完成
//		} finally {
//			db.endTransaction(); // 结束事务
//			db.close();
//		}
//	}
//	
//	public static void addGroups(List<WordGroup> grouplist) {
//		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
//					getDBFile(FileUtil.getSDPath() + AppConfig.WORD_FILE,
//							AppContext.getInstance().getUsername()+AppConfig.WORD_DB_NAME), null);		
//		db.beginTransaction(); // 开始事务
//		try {
//			for (WordGroup group : grouplist) {
//				db.execSQL("INSERT INTO wordgroup VALUES(?, ?, ?, ?)", new Object[] {
//						group.getId(),group.getName(),group.getTotal(), group.getCurrent() });
//			}
//			db.setTransactionSuccessful(); // 设置事务成功完成
//		} catch (Exception e) {
//		} finally {
//			db.endTransaction();
//			db.close();// 结束事务
//		}
//	}
//
//	/**
//	 * 添加单词
//	 * 
//	 * @param typelist
//	 */
//	public static void addWords(List<Word> wordlist) {
//		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
//				getDBFile(FileUtil.getSDPath() + AppConfig.WORD_FILE,
//						AppContext.getInstance().getUsername()+AppConfig.WORD_DB_NAME), null);
//		db.beginTransaction(); // 开始事务
//		try {
//			for (Word word : wordlist) {
//				db.execSQL(
//						"INSERT INTO words VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? ,?)",
//						new Object[] { word.getId(), word.getG_id(),
//								word.getContent(), word.getYinbiao(),
//								word.getComment(), word.getExample(),
//								word.getVoice(), word.getKnow(),
//								word.getVideo(), word.getIssyn()});
//			}
//			db.setTransactionSuccessful(); // 设置事务成功完成
//		} finally {
//			db.endTransaction(); // 结束事务
//			db.close();
//		}
//		
//	}
//
//	public static SQLiteDatabase openOrCreateDatabase(String path, String name) {
//		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
//				getDBFile(path, name), null);
//		return db;
//	}
//}
