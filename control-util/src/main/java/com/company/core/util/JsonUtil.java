package com.company.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

	private static Gson gson = null;
	static {
		GsonBuilder gb = new GsonBuilder();
		gb.serializeNulls();
		gson = gb.create();
	}
	public static String toJson(Object vo){
		if(vo == null){
			return null;
		}
		
		return gson .toJson(vo);
	}
}
