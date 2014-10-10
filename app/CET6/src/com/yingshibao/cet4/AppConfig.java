package com.yingshibao.cet4;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rey.app.model.Word;
import com.yingshibao.cet4.bean.Course;
import com.yingshibao.cet4.bean.Question;
import com.yingshibao.cet4.util.DESUtil;

/**
 * AppConfig 应用程序配置类
 * 
 * @author Rey
 * 
 */
public class AppConfig {

	public static String AES_KEY = "1234567890123456";
	public static String DES_KEY = "12345678";

	public static int readCurrNum = 0;// 记录当前阅读题目用户做到了第几题

	public static String SERVER_IP = "http://112.124.28.148:80/wwyj/";

	public static String POST_TYPE = "?_type=json";

	public static String CONTENT_TYPE = "application/json";

	public static String WORD_FILE = "/yingshibao/word/";

	public static String WORD_DB_NAME = "_word.db";

	public static int WORD_TYPE = 1;

	public static int LISTEN_TYPE = 3;

	public static int READ_TYPE = 2;

	public static int LIB_TYPE = 4;

	public static Course course;// 当前学习的顶级课程类型

	public static String version_action = "wsjson/latestversion";// 版本检测
	public static String login_action = "wsjson/login";// 登录
	public static String registe_action = "wsjson/register";// 注册
	public static String wordgroups_action = "wsjson/wordgroups";// 获取单词分组
	public static String words4group_action = "wsjson/words4group";// 获取分组内单词
	public static String unknownflag4word_action = "wsjson/unknownflag4word";// 更新单词学习记录
	public static String unknownwords_action = "wsjson/unknownwords";// 生词本
	public static String rootcourses4level_action = "wsjson/rootcourses4level";// 获取根课程信息
	public static String coursecomment_action = "wsjson/coursecomment";// 课程评价
	public static String setcollege_action = "wsjson/setcollege";// 设置学校
	public static String practicecoursetree_action = "wsjson/practicecoursetree";// 课程树结构
	public static String practicequery_action = "wsjson/practicequery";// 课程习题查询
	public static String practicerecord_action = "wsjson/practicerecord";// 课程习题查询

	/**
	 * 获取连接地址
	 * 
	 * @param actionpath
	 * @return
	 */
	public static String requestURL(String actionpath) {
		return SERVER_IP + actionpath;
	}

	/**
	 * version版本检测 level //产品等级,0:无效1:CET4,2:CET6 type
	 * //产品类型,0:无效,1:桌面,2:安卓,3:IOS
	 */
	public static byte[] version(int type, int level) throws AppException {
		JSONObject version_info = new JSONObject();
		byte[] body;
		try {
			version_info.put("type", type);
			version_info.put("level", level);
			body = version_info.toString().getBytes("UTF-8");
		} catch (JSONException e) {
			throw AppException.json(e);
		} catch (Exception e) {
			throw AppException.json(e);
		}
		return body;
	}

	/**
	 * login 登陆body
	 */
	public static byte[] login(String username, String password, String level)
			throws AppException {
		JSONObject login_info = new JSONObject();
		byte[] body;
		try {
			login_info.put("username", username);
			login_info.put("password", password);
			login_info.put("requestCourseLevel", level);
			body = DESUtil.encryptDESBytes(login_info.toString(), DES_KEY);
		} catch (JSONException e) {
			throw AppException.json(e);
		} catch (Exception e) {
			throw AppException.json(e);
		}
		return body;
	}

	/**
	 * registe 注册body
	 */
	public static byte[] registe(String username, String password, int level)
			throws AppException {
		JSONObject registe_info = new JSONObject();
		byte[] body;
		try {
			registe_info.put("username", username);
			registe_info.put("password", password);
			registe_info.put("requestCourseLevel", level);
			body = DESUtil.encryptDESBytes(registe_info.toString(), DES_KEY);
		} catch (JSONException e) {
			throw AppException.json(e);
		} catch (Exception e) {
			throw AppException.json(e);
		}
		return body;
	}

	/**
	 * 评价信息
	 */
	public static byte[] assess(String token, int courseId, String info,
			int level) throws AppException {
		JSONObject assess_info = new JSONObject();
		byte[] body;
		try {
			assess_info.put("token", token);
			assess_info.put("courseId", courseId);
			assess_info.put("comment", info);
			assess_info.put("level", level);
			body = assess_info.toString().getBytes("UTF-8");
		} catch (JSONException e) {
			throw AppException.json(e);
		} catch (Exception e) {
			throw AppException.json(e);
		}
		return body;
	}

	/**
	 * 课程类型树形结构（阅读，听力）
	 * 
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public static byte[] practicecoursetree(String token, int courseId)
			throws AppException {
		JSONObject cource_info = new JSONObject();
		byte[] body;
		try {
			cource_info.put("token", token);
			cource_info.put("rootCourseId", courseId);
			body = cource_info.toString().getBytes("UTF-8");
			// body=DES.encryptDESBytes(getwordgroup_info.toString(), _KEY);
		} catch (JSONException e) {
			throw AppException.json(e);
		} catch (Exception e) {
			throw AppException.json(e);
		}
		return body;
	}

	/**
	 * 获取根课程信息
	 * 
	 * @param token
	 * @param requestCourseLevel
	 * @return
	 * @throws AppException
	 * 
	 */
	public static byte[] rootcourses4level(String token, int requestCourseLevel)
			throws AppException {
		JSONObject cource_info = new JSONObject();
		byte[] body;
		try {
			cource_info.put("token", token);
			cource_info.put("requestCourseLevel", requestCourseLevel);
			body = cource_info.toString().getBytes("UTF-8");
		} catch (JSONException e) {
			throw AppException.json(e);
		} catch (Exception e) {
			throw AppException.json(e);
		}
		return body;
	}

	/**
	 * wordgroups 单词分组
	 * 
	 * @return
	 * @throws AppException
	 */
	public static byte[] wordgroups(String token, int wordCourseId)
			throws AppException {
		JSONObject getwordgroup_info = new JSONObject();
		byte[] body;
		try {
			getwordgroup_info.put("token", token);
			getwordgroup_info.put("wordCourseId", wordCourseId);
			body = getwordgroup_info.toString().getBytes("UTF-8");
			// body=DES.encryptDESBytes(getwordgroup_info.toString(), _KEY);
		} catch (JSONException e) {
			throw AppException.json(e);
		} catch (Exception e) {
			throw AppException.json(e);
		}
		return body;
	}

	/**
	 * 学校
	 * 
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public static byte[] school(String token, int id) throws AppException {
		JSONObject college_info = new JSONObject();
		byte[] body;
		try {
			college_info.put("token", token);
			college_info.put("collegeId", id);
			body = college_info.toString().getBytes("UTF-8");
		} catch (JSONException e) {
			throw AppException.json(e);
		} catch (Exception e) {
			throw AppException.json(e);
		}
		return body;
	}

	/**
	 * word 单词
	 * 
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public static byte[] word(String token, String id) throws AppException {
		JSONObject getwordgroup_info = new JSONObject();
		byte[] body;
		try {
			getwordgroup_info.put("token", token);
			getwordgroup_info.put("groupId", id);
			body = getwordgroup_info.toString().getBytes("UTF-8");
		} catch (JSONException e) {
			throw AppException.json(e);
		} catch (Exception e) {
			throw AppException.json(e);
		}
		return body;
	}

	/**
	 * 批量同步单词学习状态
	 * 
	 * @param token
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public static byte[] unknownflag4words(String token, List<Word> words)
			throws AppException {
		JSONObject studtedwords_info = new JSONObject();
		byte[] body;
		try {
			studtedwords_info.put("token", token);
			studtedwords_info.put("progress", 0);
			JSONArray courseJsons = new JSONArray();
			for (int i = 0; i < words.size(); i++) {
				Word word = words.get(i);
				JSONObject wordJson = new JSONObject();
				wordJson.put("wordId", word.getId());
				wordJson.put("unknownFlag", word.getKnow());
				courseJsons.put(wordJson);
			}
			studtedwords_info.put("flagWords", courseJsons);
			body = studtedwords_info.toString().getBytes("UTF-8");
		} catch (JSONException e) {
			throw AppException.json(e);
		} catch (Exception e) {
			throw AppException.json(e);
		}
		return body;
	}

	/**
	 * 同步单个单词学习状态
	 * 
	 * @param token
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public static byte[] unknownflag4word(String token, Word word, int progress)
			throws AppException {
		JSONObject studtedwords_info = new JSONObject();
		byte[] body;
		try {
			studtedwords_info.put("token", token);
			studtedwords_info.put("progress", progress);
			JSONArray courseJsons = new JSONArray();

			JSONObject wordJson = new JSONObject();
			wordJson.put("wordId", word.getId());
			wordJson.put("unknownFlag", word.getKnow());
			courseJsons.put(wordJson);
			studtedwords_info.put("flagWords", courseJsons);

			body = studtedwords_info.toString().getBytes("UTF-8");
		} catch (JSONException e) {
			throw AppException.json(e);
		} catch (Exception e) {
			throw AppException.json(e);
		}
		return body;
	}

	/**
	 * 生词本
	 * 
	 * @param token
	 * @return
	 * @throws AppException
	 */
	public static byte[] unknownwords(String token) throws AppException {
		JSONObject words_info = new JSONObject();
		byte[] body;
		try {
			words_info.put("token", token);
			words_info.put("pageSize", 10000);
			words_info.put("level", 1);
			words_info.put("pageIndex", 0);
			body = words_info.toString().getBytes("UTF-8");
		} catch (JSONException e) {
			throw AppException.json(e);
		} catch (Exception e) {
			throw AppException.json(e);
		}
		return body;
	}

	/**
	 * 课程习题查询
	 * 
	 * @param token
	 * @param courseId
	 * @return
	 * @throws AppException
	 */
	public static byte[] practicequery(String token, int courseId)
			throws AppException {
		JSONObject query_info = new JSONObject();
		byte[] body;
		try {
			query_info.put("token", token);
			query_info.put("courseId", courseId);
			body = query_info.toString().getBytes("UTF-8");
		} catch (JSONException e) {
			throw AppException.json(e);
		} catch (Exception e) {
			throw AppException.json(e);
		}
		return body;
	}

	/**
	 * 记录课程习题做题结果
	 * 
	 * @param token
	 * @param courseId
	 * @return
	 * @throws AppException
	 */
	public static byte[] practicerecord(String token, float progress,
			List<Question> questionlist) throws AppException {
		JSONObject recordInfo = new JSONObject();
		byte[] body;
		try {
			recordInfo.put("token", token);
			recordInfo.put("progress", progress);
			JSONArray recdJsons = new JSONArray();
			for (int i = 0; i < questionlist.size(); i++) {
				Question question = questionlist.get(i);
				JSONObject questionJson = new JSONObject();
				questionJson.put("userOpt", question.getUserOpt());
				questionJson.put("qid", question.getId());
				recdJsons.put(questionJson);
			}
			recordInfo.put("questionRecordJsons", recdJsons);
			body = recordInfo.toString().getBytes("UTF-8");
		} catch (JSONException e) {
			throw AppException.json(e);
		} catch (Exception e) {
			throw AppException.json(e);
		}
		return body;
	}

}
