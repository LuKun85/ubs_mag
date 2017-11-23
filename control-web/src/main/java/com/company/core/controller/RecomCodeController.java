package com.company.core.controller;

import com.company.core.constant.StatusConstant;
import com.company.core.domain.UserBO;
import com.company.core.entity.*;
import com.company.core.form.Pagination;
import com.company.core.form.RecomCodeForm;
import com.company.core.service.AgentService;
import com.company.core.service.InstService;
import com.company.core.service.RecomCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@Slf4j
@RequestMapping (value = "/recomCode")
public class RecomCodeController extends BaseController {
    
    @Autowired
    InstService instService;
    @Autowired
    AgentService agentService;
    @Autowired
    RecomCodeService recomCodeService;
    
    /**
     * 注册码新增 - 页面
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView toAddPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
        RecomCodeForm recomCodeForm = new RecomCodeForm();
        
        List<UcInstDo> ucInstDoList = instService.getInstList();
        
        modelAndView.getModel().put("instList", ucInstDoList);
        modelAndView.setViewName("/recomCode/add_recom_code");
        return modelAndView;
    }
    
    @RequestMapping(value = "/add_new_recomCode", method = RequestMethod.POST)
    @ResponseBody
    @RequiresAuthentication
    public Map toAddNewRecomCode(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("RecomCodeForm") RecomCodeForm recomCodeForm) {
    
        UserBO userBO = getCurrentUser();
    
        log.info("新增注册码");
        try {
            UcInstDo ucInstDo = instService.getInst(recomCodeForm.getInstId());
            if(ucInstDo == null){
                return returnError("机构不存在");
            }
            if(!StatusConstant.STATUS_ENABLE.equals(ucInstDo.getStatus())){
                return returnError("机构状态不允许生成注册码");
            }
            
            
            //如果代理商号为空, 未进行选择,  则默认使用机构自身的代理
            recomCodeService.createRecomCodes(recomCodeForm, userBO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("系统异常");
        }
    
        return returnSuccess("注册码已经生成, 数目:" + recomCodeForm.getRecomCodeCount() + "个");
    }
    
    /**
     * 注册码列表 - 页面
     */
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public ModelAndView toListPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
        //获取-激活状态下的机构列表
        List<UcInstDo> ucInstDoList = instService.getInstListByStatus(StatusConstant.STATUS_ENABLE);
        modelAndView.getModel().put("instList", ucInstDoList);
    
        //获取系统默认的机构
        UcInstDo ucInstDo = instService.getTheDefaultInst();
        
        RecomCodeForm recomCodeForm = new RecomCodeForm();
        //recomCodeForm.setInstId(ucInstDo.getInstId());
        //获取该机构下的所有注册码信息
        Pagination page= recomCodeService.getAllRecomcodes(recomCodeForm);
        recomCodeForm.setPagination(page);
        
        modelAndView.getModel().put("recomCodeListForm", recomCodeForm);
        modelAndView.getModel().put("instList", ucInstDoList);
        modelAndView.setViewName("/recomCode/list_recom_code");
        return modelAndView;
    }
    
    /**
     * 查询注册码列表
     */
    @RequestMapping(value = "/query_recomCode_list", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView toQueryRecomCodeList (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("recomCodeListForm") RecomCodeForm recomCodeForm) {
        
        //获取-激活状态下的机构列表
        List<UcInstDo> ucInstDoList = instService.getInstListByStatus(StatusConstant.STATUS_ENABLE);
    
        //获取该机构下的所有注册码信息
        Pagination page= recomCodeService.getAllRecomcodes(recomCodeForm);
        recomCodeForm.setPagination(page);
    
        modelAndView.getModel().put("recomCodeListForm", recomCodeForm);
        modelAndView.getModel().put("instList", ucInstDoList);
        modelAndView.setViewName("/recomCode/list_recom_code");
        return modelAndView;
    }
    
    /**
     * 查询注册码列表
     */
    @RequestMapping(value = "/query_recomCode_list", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView toQueryRecomCodeListPost (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("recomCodeListForm") RecomCodeForm recomCodeForm) {

        //获取-激活状态下的机构列表
        List<UcInstDo> ucInstDoList = instService.getInstListByStatus(StatusConstant.STATUS_ENABLE);

//        if(StringUtils.isBlank(recomCodeForm.getLastSearchAgentId())){
//            recomCodeForm.setLastSearchAgentId(recomCodeForm.getAgentId());
//        } else if (StringUtils.isBlank(recomCodeForm.getAgentId())){
//            int pageCurrent = Integer.parseInt(recomCodeForm.getPageCurrent());
//            if(pageCurrent > 1){
//                recomCodeForm.setAgentId(recomCodeForm.getLastSearchAgentId());
//            }
//        }
    
        int pageCurrent = Integer.parseInt(recomCodeForm.getPageCurrent());
        if(pageCurrent > 1){
            recomCodeForm.setAgentId(recomCodeForm.getLastSearchAgentId());
        } else {
            recomCodeForm.setLastSearchAgentId(recomCodeForm.getAgentId());
        }
        
        //获取该机构下的所有注册码信息
        Pagination page= recomCodeService.getAllRecomcodes(recomCodeForm);
        recomCodeForm.setPagination(page);
        recomCodeForm.setLastSearchAgentId(recomCodeForm.getAgentId());
        modelAndView.getModel().put("recomCodeListForm", recomCodeForm);
        modelAndView.getModel().put("instList", ucInstDoList);
        modelAndView.setViewName("/recomCode/list_recom_code");
        return modelAndView;

    }

    
    /**
     * 下发注册码 页面
     */
    @RequestMapping(value = "/dispatchPage", method = RequestMethod.GET)
    public ModelAndView toDispatchPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        //获取注册码参数
        String recomCode = request.getParameter("recomCode");
        UcReccomCodeCntlDo ucReccomCodeCntlDo = recomCodeService.getRecomCode(recomCode);
        if(ucReccomCodeCntlDo == null){
            modelAndView.getModel().put("statusCode", 300);
            modelAndView.getModel().put("message", "注册码不存在");
            modelAndView.setViewName("/recomCode/dispatch_recom_code");
            return modelAndView;
        }
        
        //注册码状态
        if(!StatusConstant.RECOMCODE_STATUS_ENABLE.equals(ucReccomCodeCntlDo.getStatus())){
            modelAndView.getModel().put("statusCode", 300);
            modelAndView.getModel().put("message", "注册码状态不允许下发");
            modelAndView.setViewName("/recomCode/dispatch_recom_code");
            return modelAndView;
        }
    
        RecomCodeForm recomCodeForm = new RecomCodeForm();
        //代理
        String ownerAgent = ucReccomCodeCntlDo.getUserCode();
        //获取代理信息
        UcAgentDo ucAgentDo = agentService.getAgent(ownerAgent);
        if(ucAgentDo == null){
            modelAndView.getModel().put("statusCode", 300);
            modelAndView.getModel().put("message", "注册码代理不存在");
            modelAndView.setViewName("/recomCode/dispatch_recom_code");
            return modelAndView;
        }
    
        //获取机构信息
        UcInstDo ucInstDo = instService.getInst(ucAgentDo.getInstId());
        if(ucInstDo == null){
            modelAndView.getModel().put("statusCode", 300);
            modelAndView.getModel().put("message", "注册码机构不存在");
            modelAndView.setViewName("/recomCode/dispatch_recom_code");
            return modelAndView;
        }
        recomCodeForm.setInstId(ucInstDo.getInstId());
        recomCodeForm.setInstName(ucInstDo.getInstName());
        recomCodeForm.setAgentId(ucAgentDo.getAgentId());
        recomCodeForm.setAgentName(ucAgentDo.getAgentName());
        
        //获取该代理下的直属代理
        List<String> downAgentList = agentService.getAgentIdListOfAgentOwn(ucAgentDo.getAgentId());
        
        modelAndView.getModel().put("recomCodeDispatchForm", recomCodeForm);
        modelAndView.getModel().put("toAgentList", downAgentList);
        modelAndView.setViewName("/recomCode/dispatch_recom_code");
        return modelAndView;
    }
    
    
    /**
     * 批量下发注册码 - 页面
     */
    @RequestMapping(value = "/dispatchBatchPage", method = RequestMethod.GET)
    public ModelAndView toDispatchBatchPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
        RecomCodeForm recomCodeForm = new RecomCodeForm();
    
        //获取-激活状态下的机构列表
        List<UcInstDo> ucInstDoList = instService.getInstListByStatus(StatusConstant.STATUS_ENABLE);
    
        //获取该机构下的所有注册码信息
        Pagination page= recomCodeService.getAllRecomcodes(recomCodeForm);
        recomCodeForm.setPagination(page);
        
        modelAndView.getModel().put("recomCodeDispatchBatchForm", recomCodeForm);
        modelAndView.getModel().put("instList", ucInstDoList);
        modelAndView.setViewName("/recomCode/dispatch_recom_code_batch");
        return modelAndView;
        
    }
    
    
    
    
    
    /**
     * 批量下发注册码 - 实现
     */
    @RequestMapping(value = "/dispatch_recomCode_batch", method = RequestMethod.POST)
    @ResponseBody
    public Map toDispatchRemcodeBatch (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("recomCodeDispatchBatchForm") RecomCodeForm recomCodeForm) {
    
        UserBO userBO = getCurrentUser();
        
        UcAgentDo ucAgentDo = agentService.getAgent(recomCodeForm.getAgentId());
        if(ucAgentDo == null){
            return returnError("注册码所属代理不存在");
        }
    
        UcAgentDo upUcAgentDo = agentService.getAgent(recomCodeForm.getToAgentId());
        if(upUcAgentDo == null){
            return returnError("下发代理不存在");
        }
        
        UcAgentLevelDo toAgentLevel = agentService.getAgentLevel(recomCodeForm.getToAgentId());
        if(toAgentLevel == null || !recomCodeForm.getAgentId().equals(toAgentLevel.getUpAgentId())) {
            return returnError("下发代理并非所属机构下级代理");
        }
        
        //获取可用的推荐码个数
        int count = recomCodeService.getRecomCodeCount(recomCodeForm.getAgentId(), StatusConstant.STATUS_ENABLE);
        int dispatchCount = Integer.parseInt(recomCodeForm.getDispatchCount());
        if(count < dispatchCount){
            return returnError("可用的推荐码个数不够");
        }
        
        //下发注册码
        try {
            recomCodeService.dispatchRecomCode(recomCodeForm.getAgentId(), recomCodeForm.getToAgentId(), dispatchCount, userBO.getUsrName());
        } catch (Exception ex){
            ex.printStackTrace();
            return returnError("系统异常");
        }
        return returnSuccess("下发成功");
    }
    
    
    /**
     * 下发选中注册码 - 页面
     */
    @RequestMapping(value = "/dispatchSelectedPage", method = RequestMethod.GET)
    public ModelAndView toDispatchSelectedPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {

        String recomParam = request.getParameter("recomCode");
        String agentId = request.getParameter("agentId");
        
        RecomCodeForm recomCodeForm = new RecomCodeForm();
        recomCodeForm.setToDispatchRecomCodes(recomParam);
        recomCodeForm.setAgentId(agentId);
        
        //获取-激活状态下的机构列表
        List<UcAgentDo> toAgentList = agentService.getAgentIdListOfAgentOwnEnabled(agentId);
        modelAndView.getModel().put("recomCodeDispatchSelectedForm", recomCodeForm);
        modelAndView.getModel().put("toAgentList", toAgentList);
        modelAndView.setViewName("/recomCode/dispatch_recom_code_selected");
        return modelAndView;
        
    }
    
    /**
     * 批量下发选中注册码 - 实现
     */
    @RequestMapping(value = "/dispatch_recomCode_selected", method = RequestMethod.POST)
    @ResponseBody
    public Map toDispatchRemcodeSelected (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("recomCodeDispatchSelectedForm") RecomCodeForm recomCodeForm) {
        
        UserBO userBO = getCurrentUser();
        
        UcAgentDo ucAgentDo = agentService.getAgent(recomCodeForm.getToAgentId());
        if(ucAgentDo == null){
            return returnError("下发代理不存在");
        }
        
        if(!StatusConstant.STATUS_ENABLE.equals(ucAgentDo.getStatus())){
            return returnError("下发代理状态不正确");
        }
        
        log.info("下发到代理:" + recomCodeForm.getToAgentId());
        log.info("下发到代理:" + recomCodeForm.getToAgentId());
        String recomCodes = recomCodeForm.getToDispatchRecomCodes();
        String recomP = "";
        if(StringUtils.isNotBlank(recomCodes)){
            recomP = recomCodes.replace("\\u005B", "").replace("\\u005C", "");
        }
        String[] recomArray = recomP.split("\\s");
        log.info("下发到代理的注册码:" + recomArray);
        
        if(recomArray.length <=0){
            return returnError("注册码选中列表为空");
        }
        List<String> recomList = Arrays.asList(recomArray);
    
        //下发选中注册码
        String mesage = "";
        try {
            mesage = recomCodeService.dispatchRecomCodeSelected(recomList, recomCodeForm.getAgentId(), userBO.getUsrName());
        } catch (Exception ex){
            ex.printStackTrace();
            return returnError("系统异常");
        }
        return returnSuccess(mesage);
    }
    
}
