package com.zjk.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by jason on 16-10-18.
 */
public class GsonUtil {

	public static final String DATE_PATTEN = "yyyy-MM-dd hh:mm:ss";

	public static String toJson(Object obj) {
		GsonBuilder builder = new GsonBuilder().setDateFormat(DATE_PATTEN);
		Gson gson = builder.create();
		return gson.toJson(obj);
	}

	public static <T> T toObj(String json, Class<T> clazz) {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat(DATE_PATTEN);
		Gson gson = builder.create();
		return gson.fromJson(json, clazz);
	}

	public static <T> List<T> jsonToList(String json) {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat(DATE_PATTEN);
		Gson gson = builder.create();
		return gson.fromJson(json, new TypeToken<List<T>>() {
		}.getType());
	}
}
