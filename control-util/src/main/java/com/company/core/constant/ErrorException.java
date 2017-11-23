/**
 * 
 */
package com.company.core.constant;

/**
 *
 * @Author: weiwankun
 * @Date: 2017/11/11
 */
public class ErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -930457678038938439L;
	
	private String errorMesg;

	public ErrorException(String errorMesg) {
		this.errorMesg = errorMesg;
	}

	public String getErrorMesg() {
		return errorMesg;
	}
}
