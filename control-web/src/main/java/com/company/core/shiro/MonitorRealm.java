package com.company.core.shiro;

import java.io.Serializable;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.company.core.controller.CaptchaImageCreateController;
import com.company.core.domain.FuncBO;
import com.company.core.domain.RoleBO;
import com.company.core.domain.UserBO;
import com.company.core.service.UserAuthorService;
import com.company.core.service.UserService;
import com.company.core.shiro.cache.ShiroSession;
import com.company.core.shiro.cache.ShiroSessionDao;

public class MonitorRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(MonitorRealm.class);

    @Autowired
    UserService                 userService;

    @Autowired
    UserAuthorService           userAuthorService;

    @Autowired
    ShiroSessionDao             sessionDao;

    /**
     * 给ShiroDbRealm提供编码信息，用于密码密码比对
     * 描述
     */
    public MonitorRealm() {
        super();
        CustomCredentialsMatcher customCredentialsMatcher = new CustomCredentialsMatcher();
        setCredentialsMatcher(customCredentialsMatcher);
    }

    /**
     * 认证回调函数, 登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;

        String parm = token.getCaptcha();
        String c = (String) SecurityUtils.getSubject().getSession()
            .getAttribute(CaptchaImageCreateController.CAPTCHA_KEY);
        // 忽略大小写
        if (!parm.equalsIgnoreCase(c)) {
            throw new IncorrectCaptchaException("验证码错误！");
        }

        UserBO userBO = userService.get(token.getUsername());
        if (userBO != null) {
            if (userBO.getUsrDisableTag().equals("0")) {
                throw new DisabledAccountException();
            }
            ShiroUser shiroUser = new ShiroUser(userBO.getUsrId(), userBO.getUsrName(), userBO);
            // 把账号信息放到Session中，并更新缓存,用于会话管理
            Subject subject = SecurityUtils.getSubject();
            Serializable sessionId = subject.getSession().getId();
            ShiroSession session = (ShiroSession) sessionDao.doReadSessionWithoutExpire(sessionId);
            session.setAttribute("userId", userBO.getUsrId());
            session.setAttribute("loginName", userBO.getUsrName());
            sessionDao.update(session);

            return new SimpleAuthenticationInfo(shiroUser, userBO.getUsrPwd(), getName());
        } else {
            return null;
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName()).iterator().next();
        UserBO userBO = userAuthorService.getAuthorByUserId(shiroUser.getId());
        if (!userBO.getRoleBOList().isEmpty()) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            for (RoleBO roleBO : userBO.getRoleBOList()) {
                info.addRole(roleBO.getRoleName());
                for (FuncBO funcBO : roleBO.getFuncBOList()) {
                    String permission = funcBO.getFuncUrl();
                    if (permission != null && !permission.equals("")) {
                        info.addStringPermission(permission);
                    }
                }
            }
            return info;
        } else {
            return null;
        }
    }

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {

        private static final long serialVersionUID = -1748602382963711884L;
        private String            id;
        private String            loginName;
        private UserBO            user;

        public ShiroUser() {

        }

        /**
         * 构造函数
         * @param id
         * @param loginName
         * @param user
         *
         */
        public ShiroUser(String id, String loginName, UserBO user) {
            this.id = id;
            this.loginName = loginName;
            this.user = user;
        }

        /**
         * 返回 id 的值
         * @return id
         */
        public String getId() {
            return id;
        }

        /**
         * 返回 loginName 的值
         * @return loginName
         */
        public String getLoginName() {
            return loginName;
        }

        /**
         * 返回 user 的值
         * @return user
         */
        public UserBO getUser() {
            return user;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return loginName;
        }
    }
}
