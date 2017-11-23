package com.company.core.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.core.constant.ShiroPermissionsConstant;
import com.company.core.domain.FuncBO;
import com.company.core.form.FuncManageForm;
import com.company.core.form.Pagination;
import com.company.core.mapper.SeqMapper;
import com.company.core.service.FuncService;

@Controller
public class FunctionController {

    @Autowired
    SeqMapper seqMapper;

    @Autowired
    FuncService funcService;


    private static final Logger logger = LoggerFactory.getLogger(FunctionController.class);

    @RequiresPermissions(ShiroPermissionsConstant.FUNC_ADD)
    @RequestMapping(value="/funcManagement/addnewpage", method= RequestMethod.GET)
    public String goAddNewPage(@ModelAttribute("funcManageForm")FuncManageForm funcManageForm){

        return "fun/addnewfunc";
    }

    @ResponseBody
    @RequiresPermissions(ShiroPermissionsConstant.FUNC_ADD)
    @RequestMapping(value="/funcManagement/addnew", method= RequestMethod.POST)
    public Map addNewRole(@ModelAttribute("funcManageForm")FuncManageForm funcManageForm){
        Map resultMap = new HashMap();
        String funcName = funcManageForm.getFuncName();
        //String userPass = userManageForm.getPass();

        FuncBO funcBO = new FuncBO();
        funcBO.setFuncId(String.valueOf(seqMapper.getTblBTSSysFuncIdSeq()));
        funcBO.setFuncName(funcManageForm.getFuncName());
        funcBO.setFuncFatherId(funcManageForm.getFuncFatherId());
        funcBO.setFuncDesc(funcManageForm.getFuncDesc());
        funcBO.setFuncDisableTag("1");
        funcBO.setFuncTag(funcManageForm.getFuncTag());
        funcBO.setFuncLevel(funcManageForm.getFuncLevel());
        funcBO.setFuncUrl(funcManageForm.getFuncUrl());
        funcBO.setFuncIcon(funcManageForm.getFuncIcon());
        funcBO.setFuncPriority(funcManageForm.getFuncPriority());
        if(funcBO.getFuncTag().length() > 1){
            resultMap.put("statusCode", 300);
            resultMap.put("message", "标签最长只能有1位!");
            return resultMap;
        }

        resultMap = funcService.addNewFunc(funcBO);

        return resultMap;
    }

    @RequiresPermissions(ShiroPermissionsConstant.FUNC_QUERY)
    @RequestMapping(value = "/funcManagement/query_all_func", method = RequestMethod.GET)
    public String queryAllFunc(@ModelAttribute("funcManageForm") FuncManageForm funcManageForm) {

        FuncBO funcBO = new FuncBO();
        String pageCurrent = funcManageForm.getPageCurrent();
        String pageSize = funcManageForm.getPageSize();

        funcBO.setPageCurrent(Integer.valueOf(pageCurrent));
        funcBO.setPageSize(Integer.valueOf(pageSize));

        Pagination<FuncBO> funcBOPagination = funcService.getAllFunc(funcBO);

        funcManageForm.setPagination(funcBOPagination);

        return "fun/funcmanalist";

    }

    @RequiresPermissions(ShiroPermissionsConstant.FUNC_QUERY)
    @RequestMapping(value = "/funcManagement/query_a_func", method = RequestMethod.POST)
    public String queryTheFunc(@ModelAttribute("funcManageForm") FuncManageForm funcManageForm) {

        FuncBO funcBO = new FuncBO();
        if (!(funcManageForm.getFuncName() == null || funcManageForm.getFuncName().trim().equals(""))) {
            funcBO.setFuncName(funcManageForm.getFuncName());
        }
        String pageCurrent = funcManageForm.getPageCurrent();
        String pageSize = funcManageForm.getPageSize();

        funcBO.setPageCurrent(Integer.valueOf(pageCurrent));
        funcBO.setPageSize(Integer.valueOf(pageSize));


        Pagination<FuncBO> funcBOPagination = funcService.getTheFunc(funcBO);

        funcManageForm.setPagination(funcBOPagination);

        return "fun/funcmanalist";
    }

    @ResponseBody
    @RequiresPermissions(ShiroPermissionsConstant.FUNC_AUTHORITY)
    @RequestMapping(value="/funcManagement/funcenable", method= RequestMethod.POST)
    public Map funcEnable(@ModelAttribute("funcManageForm") FuncManageForm funcManageForm, HttpServletRequest request){
        String id = request.getParameter("id");

        Map resultMap = new HashMap();
        try {
            resultMap = funcService.setFuncEnable(id);
        }catch (Exception ex){
            logger.error(ex.getMessage());
            resultMap.put("statusCode", 300);
            resultMap.put("message", "操作失败!");

        }
        return resultMap;
    }

    @ResponseBody
    @RequiresPermissions(ShiroPermissionsConstant.FUNC_AUTHORITY)
    @RequestMapping(value="/funcManagement/funcdisable", method= RequestMethod.POST)
    public Map funcDisable( @ModelAttribute("funcManageForm") FuncManageForm funcManageForm,HttpServletRequest request){
        String id = request.getParameter("id");
        Map resultMap = new HashMap();
        try {
            resultMap = funcService.setFuncDisable(id);
        }catch (Exception ex){
            logger.error(ex.getMessage());
            resultMap.put("statusCode", 300);
            resultMap.put("message", "操作失败!");

        }
        return resultMap;

    }

    @RequiresPermissions(ShiroPermissionsConstant.FUNC_UP)
    @RequestMapping(value="/funcManagement/edit", method= RequestMethod.GET)
    public String modifyFuncDetails(HttpServletRequest request, @ModelAttribute("funcManageForm") FuncManageForm funcManageForm){

        String sid = request.getParameter("sid");
        FuncBO funcBO = funcService.getById(sid);
        funcManageForm.setFuncId(sid);
        funcManageForm.setFuncName(funcBO.getFuncName());
        if(funcBO.getFuncDisableTag().trim().equals("1")){
            funcManageForm.setFuncDisableTag("启用");

        }else{
            funcManageForm.setFuncDisableTag("禁用");
        }
        funcManageForm.setFuncFatherId(funcBO.getFuncFatherId());
        funcManageForm.setFuncDesc(funcBO.getFuncDesc());
        funcManageForm.setFuncTag(funcBO.getFuncTag());
        funcManageForm.setFuncLevel(funcBO.getFuncLevel());
        funcManageForm.setFuncUrl(funcBO.getFuncUrl());
        funcManageForm.setFuncIcon(funcBO.getFuncIcon());
        funcManageForm.setFuncPriority(funcBO.getFuncPriority());

//        Pagination<FuncBO> funcBOPagination = funcService.getFuncList(sid);
//        funcManageForm.setPagination(funcBOPagination);


        return "fun/modifyfuncdetails";
    }


    @ResponseBody
    @RequiresPermissions(ShiroPermissionsConstant.FUNC_UP)
    @RequestMapping(value="/funcManagement/updatefunc", method= RequestMethod.POST)
    public Map updateFuncDetails(@ModelAttribute("funcManageForm") FuncManageForm funcManageForm,HttpServletRequest request){
        String sourceId = request.getParameter("sid");
        Map resultMap = new HashMap();
        FuncBO funcBO = new FuncBO();
        funcBO.setFuncId(funcManageForm.getFuncId());
        funcBO.setFuncFatherId(funcManageForm.getFuncFatherId());
        funcBO.setFuncDesc(funcManageForm.getFuncDesc());
        funcBO.setFuncRemark(funcManageForm.getFuncRemark());
        funcBO.setFuncTag(funcManageForm.getFuncTag());
        funcBO.setFuncLevel(funcManageForm.getFuncLevel());
        funcBO.setFuncUrl(funcManageForm.getFuncUrl());
        funcBO.setFuncIcon(funcManageForm.getFuncIcon());
        funcBO.setFuncPriority(funcManageForm.getFuncPriority());
        if(funcBO.getFuncTag().length() > 1){
            resultMap.put("statusCode", 300);
            resultMap.put("message", "标签位最长只能有1位!");
            return resultMap;
        }
        try {
            resultMap = funcService.updateFuncInfo(funcBO);
        }catch (Exception ex){
            logger.error(ex.getMessage());
            resultMap.put("statusCode", 300);
            resultMap.put("message", "操作失败!");

        }
        return resultMap;

    }
}
