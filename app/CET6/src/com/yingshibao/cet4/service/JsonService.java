package com.yingshibao.cet4.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.rey.app.model.Course;
import com.rey.app.model.Info;
import com.rey.app.model.LoginInfo;
import com.rey.app.model.Option;
import com.rey.app.model.Practice;
import com.rey.app.model.Question;
import com.rey.app.model.Teacher;
import com.rey.app.model.Word;
import com.rey.app.model.WordGroup;
import com.rey.app.model.WordGroupInfo;
import com.rey.app.model.WordInfo;
import com.yingshibao.cet4.AppException;

public class JsonService {

	/**
	 * 处理登录
	 * 
	 * @param text
	 * @return
	 * @throws AppException
	 */
	public static LoginInfo getLoginInfo(String text) throws AppException {
		LoginInfo loginInfo = new LoginInfo();
		try {
			JSONObject jsonObject = new JSONObject(text);
			loginInfo.setToken(jsonObject.getString("token"));
			String errorCode = jsonObject.getString("errorCode");
			loginInfo.setErrorCode(errorCode == null ? "" : errorCode);
			JSONArray courseJsons = jsonObject.getJSONArray("courseJsons");
			if (courseJsons != null && courseJsons.length() > 0) {
				for (int i = 0; i < courseJsons.length(); i++) {
					JSONObject courseObject = (JSONObject) courseJsons.get(i);
					Course course = new Course();
					course.setCid(courseObject.getInt("id"));
					course.setCpid(courseObject.getInt("pid"));
					// course.setLevel(courseObject.getInt("level"));
					course.setLevel(1);
					course.setType(courseObject.getInt("type"));
					course.setCourseName(courseObject.getString("name"));
					course.setDescription(courseObject.getString("description"));
					course.setProgress(courseObject.getString("progress"));
					JSONObject teacherObj = courseObject
							.getJSONObject("teacher");
					if (teacherObj != null) {
						Teacher teacher = new Teacher();
						teacher.setId(teacherObj.getInt("id"));
						teacher.setName(teacherObj.getString("name"));
						teacher.setInfo(teacherObj.getString("info"));
						course.setTeacher(teacher);
					}
					loginInfo.getCourseJsons().add(course);
					setCourses(course, courseObject.getJSONArray("children"));
				}

			}
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return loginInfo;
	}

	/**
	 * 处理课程类型子节点信息
	 * 
	 * @param loginInfo
	 * @param courseJsons
	 * @throws JSONException
	 */
	private static void setCourses(Course pcourse, JSONArray courseJsons)
			throws JSONException {
		if (courseJsons != null && courseJsons.length() > 0) {
			for (int i = 0; i < courseJsons.length(); i++) {
				JSONObject courseObject = (JSONObject) courseJsons.get(i);
				Course course = new Course();
				course.setCid(courseObject.getInt("id"));
				course.setCpid(courseObject.getInt("pid"));
				// course.setLevel(courseObject.getInt("level"));
				course.setLevel(pcourse.getLevel() + 1);
				course.setType(courseObject.getInt("type"));
				course.setCourseName(courseObject.getString("name"));
				course.setDescription(courseObject.getString("description"));
				course.setProgress(courseObject.getString("progress"));
				pcourse.getChildren().add(course);
				setCourses(course, courseObject.getJSONArray("children"));
			}

		}
	}

	public static LoginInfo getCourcesInfo(String text) throws AppException {
		LoginInfo loginInfo = new LoginInfo();
		try {
			JSONObject jsonObject = new JSONObject(text);
			String errorCode = jsonObject.getString("errorCode");
			loginInfo.setErrorCode(errorCode == null ? "" : errorCode);
			JSONArray courseJsons = jsonObject.getJSONArray("courseJsons");
			if (courseJsons != null && courseJsons.length() > 0) {
				for (int i = 0; i < courseJsons.length(); i++) {
					JSONObject courseObject = (JSONObject) courseJsons.get(i);
					com.rey.app.model.Course course = new com.rey.app.model.Course();
					course.setCid(courseObject.getInt("id"));
					course.setCpid(courseObject.getInt("pid"));
					// course.setLevel(courseObject.getInt("level"));
					course.setLevel(1);
					course.setType(courseObject.getInt("type"));
					course.setCourseName(courseObject.getString("name"));
					course.setDescription(courseObject.getString("description"));
					course.setProgress(courseObject.getString("progress"));
					course.setVideoPath(courseObject.getString("videoUrl"));
					JSONObject teacherObj = courseObject
							.getJSONObject("teacher");
					if (teacherObj != null) {
						Teacher teacher = new Teacher();
						teacher.setId(teacherObj.getInt("id"));
						teacher.setName(teacherObj.getString("name"));
						teacher.setInfo(teacherObj.getString("info"));
						course.setTeacher(teacher);
					}
					loginInfo.getCourseJsons().add(course);
					setChildCourses(course,
							courseObject.getJSONArray("children"));
				}

			}
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return loginInfo;
	}

	/**
	 * 处理课程类型子节点信息
	 * 
	 * @param loginInfo
	 * @param courseJsons
	 * @throws JSONException
	 */
	private static void setChildCourses(Course pcourse, JSONArray courseJsons)
			throws JSONException {
		if (courseJsons != null && courseJsons.length() > 0) {
			for (int i = 0; i < courseJsons.length(); i++) {
				JSONObject courseObject = (JSONObject) courseJsons.get(i);
				Course course = new Course();
				course.setCid(courseObject.getInt("id"));
				course.setCpid(courseObject.getInt("pid"));
				// course.setLevel(courseObject.getInt("level"));
				course.setLevel(pcourse.getLevel() + 1);
				course.setType(courseObject.getInt("type"));
				course.setCourseName(courseObject.getString("name"));
				course.setDescription(courseObject.getString("description"));
				course.setProgress(courseObject.getString("progress"));
				course.setVideoPath(courseObject.getString("videoUrl"));
				pcourse.getChildren().add(course);
				setChildCourses(course, courseObject.getJSONArray("children"));
			}

		}
	}

	public static WordGroupInfo getWordGroups(String text) throws AppException {
		WordGroupInfo wordGroupInfo = new WordGroupInfo();
		List<WordGroup> wordGroups = new ArrayList<WordGroup>();
		// wordGroupInfo.setWordGroups(wordGroups);
		try {
			JSONObject jsonObject = new JSONObject(text);
			wordGroupInfo.setErrorCode(jsonObject.getInt("errorCode"));
			wordGroupInfo.setGroupCount(jsonObject.getInt("groupCount"));
			wordGroupInfo.setKnownWordsCount(jsonObject
					.getInt("knownWordsCount"));
			wordGroupInfo.setUnknownWordsCount(jsonObject
					.getInt("unknownWordsCount"));
			wordGroupInfo
					.setTotalWordCount(jsonObject.getInt("totalWordCount"));

			JSONArray wordJsonGroups = jsonObject
					.getJSONArray("wordJsonGroups");
			for (int i = 0; i < wordJsonGroups.length(); i++) {
				JSONObject groupObject = (JSONObject) wordJsonGroups.get(i);
				WordGroup wordGroup = new WordGroup();
				wordGroup.setId(groupObject.getString("id"));
				int total = groupObject.getInt("totalWordCount");
				wordGroup.setTotal(total);
				// wordGroup.setCurrentNow(groupObject.getInt("progress"));
				double progress = groupObject.getDouble("progress");
				wordGroup.setName("" + (i + 1));
				int num = (int) Math.round(progress * total);
				wordGroup.setCurrent(num);
				wordGroups.add(wordGroup);
			}
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return wordGroupInfo;
	}

	public static WordInfo getWordInfo(String text, String id)
			throws AppException {
		WordInfo wordInfo = new WordInfo();
		List<Word> words = new ArrayList<Word>();
		wordInfo.setWords(words);
		try {
			JSONObject jsonObject = new JSONObject(text);
			wordInfo.setErrorCode(jsonObject.getInt("errorCode"));
			wordInfo.setProgress(1);
			JSONArray wordsArray = jsonObject.getJSONArray("wordJsons");
			for (int i = 0; i < wordsArray.length(); i++) {
				Word word = new Word();
				JSONObject wordObject = wordsArray.getJSONObject(i);
				word.setG_id(id);
				word.setId(wordObject.getString("id"));
				word.setComment(wordObject.getString("mean").trim());
				word.setContent(wordObject.getString("name").trim());
				word.setYinbiao(wordObject.getString("soundmark").trim());
				// JSONObject videoObject =
				// wordObject.getJSONObject("vedioResource");
				// word.setVideo(videoObject.getString("resURL"));
				// JSONObject voiceObject =
				// wordObject.getJSONObject("engVoiceResource");
				// word.setVoice(voiceObject.getString("resURL"));
				// JSONArray exampleArray =
				// wordObject.getJSONArray("sentences");
				// String example = "";
				// for (int j = 0; j < exampleArray.length(); j++) {
				// JSONObject sentenceObject = exampleArray.getJSONObject(j);
				// example += sentenceObject.getString("sentence") + "\n";
				// }
				word.setVideo(wordObject.getString("videoUrl").trim());
				word.setVoice(wordObject.getString("audioUrl").trim());
				word.setExample(wordObject.getString("example").trim());
				word.setKnow(wordObject.getInt("unknownFlag"));
				words.add(word);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return wordInfo;
	}

	/**
	 * 取提交后的状态信息
	 * 
	 * @param text
	 * @return
	 * @throws AppException
	 */
	public static Info getStatus(String text) throws AppException {
		Info info = new Info();
		try {
			JSONObject jsonObject = new JSONObject(text);
			info.setErrorCode(jsonObject.getString("errorCode"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 取练习题目信息列表
	 * 
	 * @param text
	 * @return
	 * @throws AppException
	 */
	public static List<Practice> getPractices(String text) throws AppException {
		Log.i("text", text);
		List<Practice> practices = null;
		try {
			JSONObject jsonObject = new JSONObject(text);
			JSONArray practiceJsons = jsonObject.getJSONArray("practiceJsons");
			if (practiceJsons != null) {
				practices = new ArrayList<Practice>();
				for (int i = 0; i < practiceJsons.length(); i++) {
					JSONObject practiceObject = (JSONObject) practiceJsons
							.get(i);
					Practice practice = new Practice();
					practices.add(practice);

					practice.setId(practiceObject.getInt("id"));
					practice.setName(practiceObject.getString("name"));
					practice.setContent(practiceObject.getString("content"));
					practice.setAudioUrl(practiceObject.getString("audioUrl") == null ? ""
							: practiceObject.getString("audioUrl"));

					JSONArray questionJson = practiceObject
							.getJSONArray("questionJson");
					List<Question> questions = null;
					if (questionJson != null) {
						questions = new ArrayList<Question>();
						practice.setQuestions(questions);
						for (int j = 0; j < questionJson.length(); j++) {
							JSONObject questionObj = (JSONObject) questionJson
									.get(j);
							Question question = new Question();
							questions.add(question);
							question.setId(questionObj.getInt("id"));
							question.setTitle(questionObj.getString("title"));
							question.setTextExplain(questionObj
									.getString("textExplain"));
							question.setVideoExplainUrl(questionObj
									.getString("videoExplainUrl"));
							question.setRightOpt(questionObj.getInt("rightOpt"));
							question.setUserOpt(questionObj.getInt("userOpt"));

							List<Option> options = null;
							JSONArray answerJsons = questionObj
									.getJSONArray("answerJsons");
							if (answerJsons != null) {
								options = new ArrayList<Option>();
								question.setOptions(options);
								for (int k = 0; k < answerJsons.length(); k++) {
									JSONObject optObj = (JSONObject) answerJsons
											.get(k);
									Option opt = new Option();
									options.add(opt);
									opt.setId(optObj.getInt("option"));
									opt.setContent(optObj.getString("content"));
								}
							}
						}
					}
				}
			}
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return practices;
	}
}
