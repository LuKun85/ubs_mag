package com.company.core.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.company.core.constant.UserConstant;
import com.company.core.entity.UcAgentDo;
import com.company.core.entity.UcInstDo;
import com.company.core.service.AgentService;
import com.company.core.service.InstService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.core.constant.ShiroPermissionsConstant;
import com.company.core.constant.SystemConstant;
import com.company.core.domain.RoleBO;
import com.company.core.domain.UserBO;
import com.company.core.form.AccountOauthForm;
import com.company.core.form.Pagination;
import com.company.core.form.UserForm;
import com.company.core.form.UserManageForm;
import com.company.core.mapper.SeqMapper;
import com.company.core.service.RoleService;
import com.company.core.service.UserService;
import com.company.core.shiro.CustomCredentialsMatcher;
import com.company.core.shiro.MonitorRealm;

/**
 * Created by fireWorks on 2016/2/26.
 */
@Controller
@RequestMapping("userManagement")
public class UserManagementController extends BaseController{
    @Autowired
    UserService                 userService;
    @Autowired
    RoleService                 roleService;
    @Autowired
    SeqMapper                   seqMapper;
    @Autowired
    AgentService  agentService;
    @Autowired
    InstService instService;

    private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

    @RequiresPermissions(ShiroPermissionsConstant.USER_UP)
    @RequestMapping(value = "goToChangepwdPage", method = RequestMethod.GET)
    public String goToChangepwdPage(@ModelAttribute("userForm") UserForm userForm,
                                    HttpServletRequest request) {
        String loginName = request.getParameter("loginName");
        String loginId = request.getParameter("loginId");
        String adminuser = SystemConstant.ROOT;
        Subject currentUser = SecurityUtils.getSubject();
        MonitorRealm.ShiroUser shiroUser = (MonitorRealm.ShiroUser) currentUser.getPrincipal();
        userForm.setUserid(loginId);
        userForm.setUsername(loginName);
        request.setAttribute("admin", adminuser);
        return "user/changepwd";
    }

    @ResponseBody
    @RequiresPermissions(ShiroPermissionsConstant.USER_UP)
    @RequestMapping(value = "changepwd", method = RequestMethod.POST)
    public Map changepwd(@ModelAttribute("userForm") UserForm userForm) {
        CustomCredentialsMatcher customCredentialsMatcher = new CustomCredentialsMatcher();
        UserBO userBO = new UserBO();
        String userid = userForm.getUserid();
        String username = userForm.getUsername();

        String oldpassword = userForm.getOldpassword();
        if (oldpassword != null && !oldpassword.isEmpty()) {
            oldpassword = customCredentialsMatcher.encrypt(oldpassword);
            userBO.setUsrPwd(oldpassword);
        }

        String newpassword = userForm.getPass();
        newpassword = customCredentialsMatcher.encrypt(newpassword);

        userBO.setPass(newpassword);
        userBO.setUsrId(userid);
        userBO.setUsrName(username);

        return userService.updatePwd(userBO);

    }

    @RequiresPermissions(ShiroPermissionsConstant.USER_ADD)
    @RequestMapping(value = "addnewpage", method = RequestMethod.GET)
    public String goAddNewPage(@ModelAttribute("userForm") UserForm userForm) {
        return "user/addnewuser";
    }

    @ResponseBody
    @RequiresPermissions(ShiroPermissionsConstant.USER_ADD)
    @RequestMapping(value = "addnew", method = RequestMethod.POST)
    public Map addNewUser(@ModelAttribute("userManageForm") UserManageForm userManageForm) {
        Map resultMap = new HashMap();
        String userName = userManageForm.getUsername();
        String userPass = userManageForm.getPass();
    
        UserBO userBO = new UserBO();
        userBO.setUsrType(UserConstant.USER_ADMIN);  //wwk
    
        //wwk 具体看绑定哪个用户
        String instId = userManageForm.getInstId();
        String agentId = userManageForm.getAgentId();
        int i = 0;
        Map<String, String> ifMerCorrect = new HashMap<String, String>();
        if (StringUtils.isNotBlank(instId)) {
            i ++ ;
            UcInstDo ucInstDo = instService.getInst(instId);
            if(ucInstDo == null){
                resultMap.put("statusCode", 300);
                resultMap.put("message", "关联机构不存在");
                return resultMap;
            }
            userBO.setUsrType(UserConstant.USER_INST);  //wwk
            userBO.setUsrCode(instId);
        }
        if (StringUtils.isNotBlank(agentId)) {
            i ++;
            UcAgentDo ucAgentDo = agentService.getAgent(agentId);
            if(ucAgentDo == null){
                resultMap.put("statusCode", 300);
                resultMap.put("message", "关联代理商不存在");
                return resultMap;
            }
            userBO.setUsrType(UserConstant.USER_AGENT);  //wwk
            userBO.setUsrCode(agentId);
        }
        if (i > 1) {
            resultMap.put("statusCode", 300);
            resultMap.put("message", "只能关联一个用户");
            return resultMap;
        }
        
        userBO.setUsrId(String.valueOf(seqMapper.getTblBTSSysUsrIdSeq()));
        userBO.setUsrName(userName);
        userBO.setUsrEmail(userManageForm.getUsrEmail());
        CustomCredentialsMatcher customCredentialsMatcher = new CustomCredentialsMatcher();
        userPass = customCredentialsMatcher.encrypt(userPass);
        userBO.setUsrPwd(userPass);
        userBO.setUsrDisableTag("1");
        Subject currentUser = SecurityUtils.getSubject();
        MonitorRealm.ShiroUser shiroUser = (MonitorRealm.ShiroUser) currentUser.getPrincipal();
        userBO.setUsrCreateBy(shiroUser.getLoginName());
        userBO.setUsrUpdateBy(shiroUser.getLoginName());
    
        resultMap = userService.addNewUsr(userBO);

        return resultMap;

    }

    @RequiresPermissions(ShiroPermissionsConstant.USER_QUERY)
    @RequestMapping(value = "query_all_user", method = RequestMethod.GET)
    public String queryAllUser(@ModelAttribute("userManageForm") UserManageForm userManageForm) {

        UserBO userBO = new UserBO();
        String pageCurrent = userManageForm.getPageCurrent();
        String pageSize = userManageForm.getPageSize();

        userBO.setPageCurrent(Integer.valueOf(pageCurrent));
        userBO.setPageSize(Integer.valueOf(pageSize));

        Pagination<UserBO> userBOPagination = userService.getAllUsr(userBO);

        userManageForm.setPagination(userBOPagination);

        return "user/usermanalist";

    }

    @RequiresPermissions(ShiroPermissionsConstant.USER_QUERY)
    @RequestMapping(value = "query_a_user", method = RequestMethod.POST)
    public String queryTheUser(@ModelAttribute("userManageForm") UserManageForm userManageForm) {

        UserBO userBO = new UserBO();
        if (!(userManageForm.getUsername() == null
              || userManageForm.getUsername().trim().equals(""))) {
            userBO.setUsrName(userManageForm.getUsername());
        }
        String pageCurrent = userManageForm.getPageCurrent();
        String pageSize = userManageForm.getPageSize();

        userBO.setPageCurrent(Integer.valueOf(pageCurrent));
        userBO.setPageSize(Integer.valueOf(pageSize));

        Pagination<UserBO> userBOPagination = userService.getTheUsr(userBO);

        userManageForm.setPagination(userBOPagination);

        return "user/usermanalist";

    }

    //获取用户角色列表  wwk
    @RequiresPermissions(ShiroPermissionsConstant.USER_AUTHORITY)
    @RequestMapping(value = "modifyacctoauth", method = RequestMethod.GET)
    public String modifyAcctDetails(HttpServletRequest request,
                                    @ModelAttribute("accountOauthForm") AccountOauthForm accountOauthForm) {
        String id = request.getParameter("id");
        UserBO userBO = userService.getById(id);
        accountOauthForm.setUsrId(id);
        accountOauthForm.setUsrName(userBO.getUsrName());
        if (userBO.getUsrDisableTag().trim().equals("1")) {
            accountOauthForm.setUsrDisableTag("启用");
        } else {
            accountOauthForm.setUsrDisableTag("禁用");
        }
        Pagination<RoleBO> roleBOPagination = roleService.getRoleList(id);
        accountOauthForm.setPagination(roleBOPagination);
        return "user/modifyacctoauth";
    }

    @ResponseBody
    @RequiresPermissions(ShiroPermissionsConstant.USER_AUTHORITY)
    @RequestMapping(value = "acctenable", method = RequestMethod.POST)
    public Map acctEnable(@ModelAttribute("accountOauthForm") AccountOauthForm accountOauthForm,
                          HttpServletRequest request) {
        String id = request.getParameter("id");

        Map resultMap = new HashMap();
        try {
            resultMap = userService.setAcctEnable(id);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            resultMap.put("statusCode", 300);
            resultMap.put("message", "操作失败!");

        }
        return resultMap;
    }

    @ResponseBody
    @RequiresPermissions(ShiroPermissionsConstant.USER_AUTHORITY)
    @RequestMapping(value = "acctdisable", method = RequestMethod.POST)
    public Map acctDisable(@ModelAttribute("accountOauthForm") AccountOauthForm accountOauthForm,
                           HttpServletRequest request) {
        String id = request.getParameter("id");
        Map resultMap = new HashMap();
        try {
            resultMap = userService.setAcctDisable(id);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            resultMap.put("statusCode", 300);
            resultMap.put("message", "操作失败!");

        }
        return resultMap;

    }

    
    //分配用户权限角色 wwk
    @ResponseBody
    @RequiresPermissions(ShiroPermissionsConstant.USER_AUTHORITY)
    @RequestMapping(value = "addauthority", method = RequestMethod.POST)
    public Map addAuthorityForUsr(@ModelAttribute("accountOauthForm") AccountOauthForm accountOauthForm,
                                  HttpServletRequest request) {
        String roleId = request.getParameter("id");
        String userId = request.getParameter("uid");
        Map resultMap = new HashMap();
        try {
            resultMap = userService.addAcctAuthority(roleId, userId);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            resultMap.put("statusCode", 300);
            resultMap.put("message", "操作失败!");

        }
        return resultMap;

    }

    @ResponseBody
    @RequiresPermissions(ShiroPermissionsConstant.USER_AUTHORITY)
    @RequestMapping(value = "cancelauthority", method = RequestMethod.POST)
    public Map cancelAuthorityForUsr(@ModelAttribute("accountOauthForm") AccountOauthForm accountOauthForm,
                                     HttpServletRequest request) {
        String roleId = request.getParameter("id");
        String userId = request.getParameter("uid");
        Map resultMap = new HashMap();
        try {
            resultMap = userService.cancelAcctAuthority(roleId, userId);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            resultMap.put("statusCode", 300);
            resultMap.put("message", "操作失败!");

        }
        return resultMap;

    }
}
