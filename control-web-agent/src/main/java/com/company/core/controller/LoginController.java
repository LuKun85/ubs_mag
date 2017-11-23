package com.company.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.core.constant.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.core.constant.SystemConstant;
import com.company.core.domain.MenuBO;
import com.company.core.domain.UserBO;
import com.company.core.form.LoginForm;
import com.company.core.service.MenuService;
import com.company.core.service.UserService;
import com.company.core.shiro.CaptchaUsernamePasswordToken;
import com.company.core.shiro.MonitorRealm;

@Controller
@Slf4j
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MenuService                 menuService;
    @Autowired
    UserService                 userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String goToLoginPage() {
        return "login";
    }

    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, HttpSession session,
                        HttpServletRequest request) {

        Map<String, String> resultMap = new HashMap<String, String>();
        String username = loginForm.getUsername();
        String host = request.getRemoteHost();
        loginForm.setHost(host);

        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            resultMap = login(currentUser, loginForm);
        } else {//重复登录
            currentUser.logout();
            resultMap = login(currentUser, loginForm);
        }

        if ("200".equals(resultMap.get("statusCode"))) {
            UserBO userBO = userService.get(username);
            session.setAttribute(SystemConstant.USER_SESSION_KEY, userBO);
            return "redirect:/main";
        } else {
            request.setAttribute("error", resultMap);
            return "login";
        }
    }

    private Map<String, String> login(Subject currentUser, LoginForm loginForm) {
        Map<String, String> resultMap = new HashMap<String, String>();
        CaptchaUsernamePasswordToken captchaUsernamePasswordToken = new CaptchaUsernamePasswordToken(
            loginForm.getUsername(), loginForm.getPassword(), true, loginForm.getHost(),
            loginForm.getCaptcha());
        try {
            currentUser.login(captchaUsernamePasswordToken);
            
            log.info("判断用户类型");  //不是机构, 代理, 商户类型, 不允许登录  wwk
            UserBO userBO = userService.get(loginForm.getUsername());
            if(StringUtils.isNotBlank(userBO.getUsrType()) && (UserConstant.USER_INST.equals(userBO.getUsrType()) ||
                    UserConstant.USER_AGENT.equals(userBO.getUsrType()) || UserConstant.USER_USER.equals(userBO.getUsrType()))){
                resultMap.put("statusCode", "200");
                resultMap.put("message", "登录成功!");
            } else {
                currentUser.logout();
                resultMap.put("statusCode", "300");
                resultMap.put("message", "此账户类型不允许登录代理平台!");
            }
        } catch (UnknownAccountException uae) {
            resultMap.put("statusCode", "300");
            resultMap.put("message", "用户账户不存在!");
        } catch (IncorrectCredentialsException ice) {
            resultMap.put("statusCode", "300");
            resultMap.put("message", "用户密码错误!");
        } catch (LockedAccountException lae) {
            resultMap.put("statusCode", "300");
            resultMap.put("message", "账户已被锁定!");
        } catch (AuthenticationException ae) {
            resultMap.put("statusCode", "300");
            resultMap.put("message", "认证异常!");
        }
        return resultMap;
    }

    @RequiresAuthentication
    @RequestMapping(value = "/logout")
    public String logOut(HttpSession session, HttpServletRequest request) {
        UserBO user = (UserBO) session.getAttribute(SystemConstant.USER_SESSION_KEY);
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
            logger.debug("用户" + user.getUsrName() + "退出登录");
        }
        return "forward:/login";
    }

    @RequiresAuthentication
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String goMain(HttpSession session, HttpServletRequest request,
                         HttpServletResponse response, Map<String, Object> model) {
        MonitorRealm.ShiroUser shiroUser = (MonitorRealm.ShiroUser) SecurityUtils.getSubject()
            .getPrincipal();
        if (shiroUser == null) {
            return "redirect:/login";
        }
        String userid = shiroUser.getId();
        List<MenuBO> menuBOList = new ArrayList<MenuBO>();
        if ("admin".equals(shiroUser.getLoginName())) {
            menuBOList = menuService.getAllEnabledMenu();
        } else {
            menuBOList = menuService.getAllEnabledMenuByUserId(userid);
        }
        model.put("menuBOList", menuBOList);
        model.put("loginName", shiroUser);
        return "main";
    }
}
