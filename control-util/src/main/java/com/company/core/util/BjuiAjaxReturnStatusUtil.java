package com.company.core.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.company.core.constant.AjaxReturnStatus;

public class BjuiAjaxReturnStatusUtil {

	public static String succeed() {
		AjaxReturnStatus b = new AjaxReturnStatus();
		b.setStatusCode(200);
		b.setMessage("成功");
		b.setCloseCurrent(true);
		return JsonUtil.toJson(b);
	}
	public static String succeedDel() {
		AjaxReturnStatus b = new AjaxReturnStatus();
		b.setStatusCode(200);
		b.setMessage("成功");
		b.setCloseCurrent(false);
		return JsonUtil.toJson(b);
	}

	public static String succeed(String message) {
		AjaxReturnStatus b = new AjaxReturnStatus();
		b.setStatusCode(200);
		b.setMessage(message);
		b.setCloseCurrent(true);
		return JsonUtil.toJson(b);
	}
	public static String succeedDel(String message) {
		AjaxReturnStatus b = new AjaxReturnStatus();
		b.setStatusCode(200);
		b.setMessage(message);
		b.setCloseCurrent(false);
		return JsonUtil.toJson(b);
	}

	public static String error(String message) {
		AjaxReturnStatus b = new AjaxReturnStatus();
		b.setStatusCode(300);
		b.setMessage(message);
		b.setCloseCurrent(false);
		return JsonUtil.toJson(b);
	}

	public static void retJson(HttpServletResponse response, String jo){
		response.setContentType("text/plain" + ";charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(jo);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
