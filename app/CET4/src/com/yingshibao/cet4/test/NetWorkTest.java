package com.yingshibao.cet4.test;

import java.util.HashMap;
import java.util.Map;

import com.yingshibao.cet4.api.WordApi;

import android.test.AndroidTestCase;

public class NetWorkTest extends AndroidTestCase {

	public void WordTest(){
		Map<String,String> map = new HashMap<String, String>();
		map.put("token", "3857eebbbf224f57ba44fc8e128f9d58");
		map.put("wordCourseId", "3");
		WordApi word = new WordApi(getContext());
		word.getWordGroup(map);
	}
}
