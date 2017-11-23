package com.company.core.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by fireWorks on 2016/2/25.
 */
public class IncorrectCaptchaException extends AuthenticationException {
    /** 描述  */
    private static final long serialVersionUID = 6146451562810994591L;

    public IncorrectCaptchaException() {
        super();
    }

    public IncorrectCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCaptchaException(String message) {
        super(message);
    }

    public IncorrectCaptchaException(Throwable cause) {
        super(cause);
    }
}
