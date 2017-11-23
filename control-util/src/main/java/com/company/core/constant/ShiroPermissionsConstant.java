package com.company.core.constant;

//#===========================
//#=权限常量表                                          =
//#=增加权限时，注意 增加到相应方法上       =
//#=2016-05-06               =
//#===========================
public class ShiroPermissionsConstant {

    // 用户操作
    public static final String USER_ADD       = "user:add";
    public static final String USER_DEL       = "user:del";
    public static final String USER_UP        = "user:up";
    public static final String USER_QUERY     = "user:query";
    public static final String USER_AUTHORITY = "user:authority";

    // 角色操作
    public static final String ROLE_ADD       = "role:add";
    public static final String ROLE_DEL       = "role:del";
    public static final String ROLE_UP        = "role:up";
    public static final String ROLE_QUERY     = "role:query";
    public static final String ROLE_AUTHORITY = "role:authority";

    // 权限操作
    public static final String FUNC_ADD       = "func:add";
    public static final String FUNC_DEL       = "func:del";
    public static final String FUNC_UP        = "func:up";
    public static final String FUNC_QUERY     = "func:query";
    public static final String FUNC_AUTHORITY = "func:authority";

}
