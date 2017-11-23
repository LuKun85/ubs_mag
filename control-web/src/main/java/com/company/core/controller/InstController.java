package com.company.core.controller;

import com.company.core.constant.Constant;
import com.company.core.constant.ErrorException;
import com.company.core.constant.StatusConstant;
import com.company.core.domain.UserBO;
import com.company.core.entity.UcCategoryDo;
import com.company.core.entity.UcInstDo;
import com.company.core.entity.UcProdDo;
import com.company.core.form.InstForm;
import com.company.core.form.Pagination;
import com.company.core.form.RecomCodeForm;
import com.company.core.service.InstService;
import com.company.core.service.ProdCategoryService;
import com.company.core.shiro.MonitorRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping (value = "/inst")
public class InstController extends BaseController {
    
    @Autowired
    InstService instService;
    @Autowired
    ProdCategoryService prodCategoryService;
    
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView toAddPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
//        List<UcCategoryDo> ucCategoryDoList = prodCategoryService.getCategoryIdList(Constant.CATEGORY_DEFAULT, StatusConstant.STATUS_ENABLE);
//        modelAndView.getModel().put("categoryList", ucCategoryDoList);
        
        modelAndView.setViewName("/inst/add_inst");
        return modelAndView;
    }
    
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public ModelAndView toListPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
        InstForm instForm = new InstForm();
        instForm.setPageCurrent("0");
        instForm.setPageNo("0");
        instForm.setPageSize("10");
    
        //获取-所有的机构列表
        List<UcInstDo> allInstList = instService.getInstList();
        Pagination pagination = instService.getInstListPage(instForm);

        instForm.setPagination(pagination);
        modelAndView.getModel().put("instListForm", instForm);
        modelAndView.getModel().put("instList", allInstList);
        modelAndView.setViewName("/inst/list_inst");
        return modelAndView;
    }
    
    @RequestMapping(value = "/query_inst_list", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView toQueryInstList (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("instListForm") InstForm instForm) {
        
        //获取-所有的机构列表
        List<UcInstDo> allInstList = instService.getInstList();
        Pagination pagination = instService.getInstListPage(instForm);
        
        instForm.setPagination(pagination);
        modelAndView.getModel().put("instForm", instForm);
        modelAndView.getModel().put("instList", allInstList);
        modelAndView.setViewName("/inst/list_inst");
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/detailPage", method = RequestMethod.GET)
    public ModelAndView toDetailPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        String instId = request.getParameter("instId");
        log.info("查询机构号instId=" + instId);
        
        InstForm instForm = new InstForm();
        instForm.setInstId(instId);
        
        instService.formatInstFormFromInst(instForm);  // 基本信息
        instService.formatInstFormFromInstInfo(instForm); //附加信息
        instService.formatInstFormFromFee(instForm); //费率信息
    
        List<UcProdDo> ucProdDos = prodCategoryService.getProdList();
        modelAndView.getModel().put("prodList", ucProdDos);
        
        modelAndView.getModel().put("instDetailForm", instForm);
        modelAndView.setViewName("/inst/detail_inst");
        return modelAndView;
    }
    
    @RequestMapping(value = "/add_new_inst", method = RequestMethod.POST)
    @ResponseBody
    @RequiresAuthentication
    public Map toAddNewInst(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("instForm") InstForm instForm) {
        
        UserBO userBO = getCurrentUser();
        
        log.info("机构开户");
        Boolean dupshortname = instService.checkIfDupInstByName("", instForm.getInstShortName());
        if(dupshortname){
            return returnSuccess("机构简称重复");
        }
        Boolean dupname = instService.checkIfDupInstByName(instForm.getInstName(), "");
        if(dupname){
            return returnSuccess("机构全称重复");
        }
        
        //检查费率
        String error = instService.checkFees(instForm);
        if(StringUtils.isNotBlank(error)){
            return returnError(error);
        }
        
        //如果category == all, 则默认为0
        if("all".equals(instForm.getCategory())){
            instForm.setCategory(Constant.CATEGORY_DEFAULT);
        }
        
        //新增机构
        String inst = "";
        try {
            inst = instService.createNewInst(instForm, userBO);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ErrorException){
                return returnError(e.getMessage());
            }else {
                return returnError("系统异常");
            }
        }
        
        return returnSuccess("新增机构成功, 机构号为:" + inst);
        
    }
    
    @RequestMapping(value = "/update_inst", method = RequestMethod.POST)
    public Map<String, Object> toUpdateInst (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("instUpdateForm") InstForm instForm) {
    
        try {
            UserBO userBO = getCurrentUser();

            //检查费率
            String error = instService.checkFees(instForm);
            if(StringUtils.isNotBlank(error)){
                return returnError(error);
            }
            
            instService.updateInst(instForm, userBO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("数据更新失败");
        }
    
        return returnError("数据更新成功");

    }
    
    @RequestMapping(value = "/activate_inst", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> toActivateInst (HttpServletRequest request, HttpServletResponse response) {
        
        try {
            
            String instId = request.getParameter("instId");
            if(StringUtils.isBlank(instId)){
            
            }
            UcInstDo ucInstDo = instService.getInst(instId);
            if(ucInstDo == null){
                return returnError("机构不存在");
            }
            if(StatusConstant.STATUS_CANNEL.equals(ucInstDo.getStatus())){
                return returnError("机构已注销");
            }
            if(StatusConstant.STATUS_ENABLE.equals(ucInstDo.getStatus())){
                return returnError("机构已经激活,不需要重复激活");
            }
            
            UserBO userBO = getCurrentUser();
            
            //检验
            Map<String, String> result = instService.activateCheck(instId);
            if(result.containsKey("error")){
                return returnError(result.get("error"));
            }
            
            //激活
            instService.activateInst(instId, userBO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("数据更新失败");
        }
        
        return returnError("机构激活成功");
        
    }
    
    @RequestMapping(value = "/disable_inst", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> toDisableInst (HttpServletRequest request, HttpServletResponse response) {
        
        try {
            
            String instId = request.getParameter("instId");
            if(StringUtils.isBlank(instId)){
            
            }
            UcInstDo ucInstDo = instService.getInst(instId);
            if(ucInstDo == null){
                return returnError("机构不存在");
            }
            if(StatusConstant.STATUS_CANNEL.equals(ucInstDo.getStatus())){
                return returnError("机构已注销");
            }
            if(StatusConstant.STATUS_DISABLE.equals(ucInstDo.getStatus())){
                return returnError("机构已经挂起,不需要重复挂起");
            }
            
            UserBO userBO = getCurrentUser();
            
            instService.disableInst(instId, userBO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("数据更新失败");
        }
        
        return returnError("机构挂起成功");
        
    }
    
    
    @RequestMapping(value = "/cancel_inst", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> toCancelInst (HttpServletRequest request, HttpServletResponse response) {
        
        try {
            
            String instId = request.getParameter("instId");
            if(StringUtils.isBlank(instId)){
            
            }
            UcInstDo ucInstDo = instService.getInst(instId);
            if(ucInstDo == null){
                return returnError("机构不存在");
            }
            if(StatusConstant.STATUS_CANNEL.equals(ucInstDo.getStatus())){
                return returnError("机构已注销, 不需要重复注销");
            }
            
            UserBO userBO = getCurrentUser();
            
            instService.cancelInst(instId, userBO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("数据更新失败");
        }
        
        return returnError("机构挂起成功");
        
    }
    
 
    
    
    @RequestMapping(value = "/toInstFeePage", method = RequestMethod.GET)
    @ResponseBody
    @RequiresAuthentication
    public Map toInstFeePage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("instForm") InstForm instForm) {
        
        UserBO userBO = getCurrentUser();
        
        log.info("机构开户");
        Boolean dupshortname = instService.checkIfDupInstByName("", instForm.getInstShortName());
        if(dupshortname){
            return returnSuccess("机构简称重复");
        }
        Boolean dupname = instService.checkIfDupInstByName(instForm.getInstName(), "");
        if(dupname){
            return returnSuccess("机构全称重复");
        }
        
        //新增机构
        String inst = "";
        try {
            inst = instService.createNewInst(instForm, userBO);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ErrorException){
                return returnError(e.getMessage());
            }else {
                return returnError("系统异常");
            }
        }
        
        return returnSuccess("新增机构成功, 机构号为:" + inst);
        
    }
    
}
