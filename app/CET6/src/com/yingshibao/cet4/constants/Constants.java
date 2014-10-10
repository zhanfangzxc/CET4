package com.yingshibao.cet4.constants;

/**
 * 常量类
 * 
 * @author malinkang 2014-2-28 下午3:06:01
 * 
 */

public class Constants {

	public static String LEVEL = "2";

	public static String AES_KEY = "1234567890123456";

	public static String DES_KEY = "12345678";

	// ////////////////////////////////////////////////
	// /URL
	// ///////////////////////////////////////////////////

	public static String BASE_URL = "http://112.124.28.148:80/wwyj/";
	public static String PICTURE_BASE_URL = "http://www.yingshibao.com/uploadfile/preview/";

	public static String LOGIN_URL = BASE_URL + "wsjson/login";// 登录

	public static String REGISTER_URL = BASE_URL + "wsjson/register";// 注册

	public static String SETCOLLEGE_URL = BASE_URL + "wsjson/setcollege";// 注册

	public static String WORDGROUDP_URL = BASE_URL + "wsjson/words4group";// 获取分组内单词

	public static String VERSION_URL = BASE_URL + "wsjson/latestversion";// 版本检测

	public static String COURSECOMMENT_URL = BASE_URL + "wsjson/coursecomment";// 课程评价
	public static String SETPHONEGRADE = BASE_URL + "wsjson/setphonegrade";// 注册
	public static String WORDGROUPS_URL = BASE_URL + "wsjson/wordgroups";// 获取单词分组
	public static final String ACQUIRE_COLLEGE = "http://112.124.28.148/wsjson/acquirecollege";// 获取学校信息
	public static String UNKNOWNFLAG4WORD_URL = BASE_URL
			+ "wsjson/unknownflag4word";// 更新单词学习记录

	public static String UNKNOWNWORDS_URL = BASE_URL + "wsjson/unknownwords";// 生词本

	public static String ROOTCOURSES4LEVEL_URL = BASE_URL
			+ "wsjson/rootcourses4level";// 获取根课程信息

	public static String PRACTICECOURSETREE_URL = BASE_URL
			+ "wsjson/practicecoursetree";// 课程树结构

	public static String PRACTICEQUERY_URL = BASE_URL + "wsjson/practicequery";// 课程习题查询

	public static String PRACTICERECORD_URL = BASE_URL
			+ "wsjson/practicerecord";// 课程习题提交

	public static String GET_COURSELIST_URL = "http://www.chuanke.com/?mod=interface&act=seller&do=courseList";// 课程习题提交

	public static String GET_UID_URL = "http://www.chuanke.com/?mod=interface&act=user&do=register";// 获取支付所需要的uid

	public static String FREE_COURSE = "http://www.chuanke.com/?mod=interface&act=pay&do=freeBuy";

	public static String PAY_COURSE = "http://www.chuanke.com/?mod=interface&act=pay";
	// ////////////////////////////////////////////////
	// /错误码
	// ///////////////////////////////////////////////////

	/**
	 * 成功
	 */
	public static final int ERROR_NO = 0;

	/**
	 * 失败
	 */
	public static final int ERROR_YES = 1;

	/**
	 * 无效账号
	 */
	public static final int ERROR_USERNAME_INVALID = 2;

	/**
	 * 账号被占用
	 */
	public static final int ERROR_USERNAME_USED = 3;

	/**
	 * 无效邮箱
	 */
	public static final int ERROR_MAIL_INVALID = 4;

	/**
	 * 邮箱被占用
	 */
	public static final int ERROR_MAIL_USED = 5;

	/**
	 * 账号和密码一致
	 */
	public static final int ERROR_USERNAME_PASSWORD_SAME = 6;

	/**
	 * 无效会话
	 */
	public static final int ERROR_TOKEN_INVALID = 7;

	/**
	 * 解密数据出错
	 */
	public static final int ERROR_DECRYPT = 8;

	/**
	 * 加密数据出错
	 */
	public static final int ERROR_ENCRYPT = 9;

	/**
	 * 密码无效
	 */
	public static final int ERROR_PASSWORD_INVALID = 10;

	/**
	 * 数据关系无效
	 */
	public static final int ERROR_DATA_RELATION_INVALID = 11;

	/**
	 * 请求数据过多
	 */
	public static final int ERROR_REQUEST_OVERFLOW = 12;

	// ////////////////////////////////////////////////
	// /广播意图
	// ///////////////////////////////////////////////////

	public static final String ACTION_DOWNLOAD = "com.yingshibao.cet4.download";

	public static final int ENTER_HOME = 1;
	public static final int SHOW_UPDATE_DIALOG = 2;
}
