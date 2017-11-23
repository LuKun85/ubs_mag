package com.company.core.constant;

public class SystemConstant extends ConfigurableContants {

    static {
        init("/system.properties");
    }

    // shiro cache config
    public static String       IP_SERVICE          = getProperty("redis.ip");
    public static String       PORT_SERVICE        = getProperty("redis.port");

    //root 绕过权限
    public static String       ROOT                = getProperty("root.name");
    public static final String USER_SESSION_KEY    = "User_Session_key";

    public static final String EMAIL_HOST_NAME     = getProperty("email.hostName");
    public static final String EMAIL_CHARSET       = getProperty("email.charset");
    public static final String EMAIL_SANDER        = getProperty("email.sender");
    public static final String EMAIL_USER_NAME     = getProperty("email.userName");
    public static final String EMAIL_USER_PASSWORD = getProperty("email.userPassword");
    
    public static final String DEFAULT_SOURCE_CODE = getProperty("default.source.code");
    public static final String DEFAULT_CATEGORY = getProperty("default.category");
    public static final String DEFAULT_CATEGORY_ID = getProperty("default.category.id");

}
