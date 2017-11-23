package com.company.core.controller;

import com.company.core.domain.UserBO;
import com.company.core.form.InstForm;
import com.company.core.service.InstService;
import com.company.core.shiro.MonitorRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class BaseController {
    
   
    public Map returnSuccess(String message){
        Map result = new HashMap();
        result.put("statusCode", 200);
        result.put("message", message);
        return result;
    }
    
    public Map returnError(String message){
        Map result = new HashMap();
        result.put("statusCode", 300);
        result.put("message", message);
        return result;
    }
    
    public UserBO getCurrentUser(){
        Subject currentUser = SecurityUtils.getSubject();
        MonitorRealm.ShiroUser shiroUser = (MonitorRealm.ShiroUser) currentUser.getPrincipal();
        return shiroUser.getUser();
    }
    
}
