package com.company.core.form;

public class AjaxReturnStatus {

	public int statusCode;

	public String message;

	public boolean closeCurrent;

	public String forward;

	public String forwardConfirm;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isCloseCurrent() {
		return closeCurrent;
	}

	public void setCloseCurrent(boolean closeCurrent) {
		this.closeCurrent = closeCurrent;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getForwardConfirm() {
		return forwardConfirm;
	}

	public void setForwardConfirm(String forwardConfirm) {
		this.forwardConfirm = forwardConfirm;
	}

}
