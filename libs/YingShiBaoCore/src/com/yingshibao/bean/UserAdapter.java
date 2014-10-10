package com.yingshibao.bean;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UserAdapter implements JsonSerializer<User> {

	@Override
	public JsonElement serialize(User user, Type arg1,
			JsonSerializationContext arg2) {

		JsonObject jsonObject1 = new JsonObject();

		jsonObject1.addProperty("username", user.getUserName());
		jsonObject1.addProperty("password", user.getPassword());
		jsonObject1.addProperty("errorCode", user.getErrorCode());
		jsonObject1.addProperty("token", user.getToken());
		jsonObject1.addProperty("nickname", user.getNickname());
		jsonObject1.addProperty("avatar", user.getAvatar());
		jsonObject1.addProperty("grade", user.getGrade());
		jsonObject1.addProperty("phone", user.getPhone());
		if (user.getCollege() != null) {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("id", user.getCollege().getId());
			jsonObject2.addProperty("name", user.getCollege().getName());
			jsonObject2.addProperty("abbr", user.getCollege().getAbbr());
			jsonObject1.add("college", jsonObject2);
		}

		return jsonObject1;

	}
}