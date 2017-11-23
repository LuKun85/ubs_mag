package com.company.core.Enum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ExcelSelect {
	/**
	 * @param key,value,key,value,....
	 * @see 把key值替换成value值，key，value成对出现
	 * 		否则将不处理
	 * @return
	 */
	public String[] select();
}
