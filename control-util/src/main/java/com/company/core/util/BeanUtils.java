package com.company.core.util;

public class BeanUtils {

	public static boolean filedNotNull(Object o) {
		if (o == null) {
			return false;
		} else if (o instanceof String && "".equals(((String) o).trim())) {
			return false;
		}
		return true;
	}
	public static boolean filedNull(Object o) {
		return !filedNotNull(0);
	}
}
