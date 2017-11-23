package com.company.core.controller;

import com.company.core.constant.StatusConstant;
import com.company.core.constant.UserConstant;
import com.company.core.domain.SelectBO;
import com.company.core.entity.UcAgentDo;
import com.company.core.service.AgentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@Slf4j
@RequestMapping (value = "/comcon")
public class ComconFuncController {
    
    @Autowired
    AgentService agentService;
    
    /**
     * 查询机构下的所有代理
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/select_agent_active", method = RequestMethod.POST)
    @ResponseBody
    public Object selectAgent(HttpServletRequest request, HttpServletResponse response) {
        
        String instId = request.getParameter("instId");
        String status = request.getParameter("status");
        String level = request.getParameter("level");
        String type = "";
    
        List<SelectBO> resp = new ArrayList<>();
        SelectBO selectBO = initialSelectBo();
        resp.add(selectBO);
        //如果机构为空就返回空
        if(StringUtils.isBlank(instId)){
            return resp;
        }
        
        /**
         * 默认查询所有的一级代理
         * agentType = Enable
         */
        if(StringUtils.isBlank(status)){
            status = StatusConstant.STATUS_ENABLE;
        }
        if(StringUtils.isNotBlank(level)){
            if("1".equals(level)){
                type = UserConstant.USER_TYPE_DEFAULT;   //默认在代理表里- 0 代理是机构本身自己的代理号
            } else {
                type = UserConstant.USER_TYPE_AGENT;   //默认在代理表里- 1 代理机构为发展的代理
            }
        }
        
        List<UcAgentDo> ucAgentDos = agentService.getAgentList(instId, type, status);
        for(UcAgentDo ucAgentDo: ucAgentDos){
            selectBO = new SelectBO();
            selectBO.setLabel(agentService.getAgentLevelName(ucAgentDo.getAgentId()) + ucAgentDo.getAgentName());
            selectBO.setValue(ucAgentDo.getAgentId());
            resp.add(selectBO);
        }
        
        return resp;
    }
    
    
    @RequestMapping(value = "/select_downagent_active", method = RequestMethod.POST)
    @ResponseBody
    public Object selectDownAgents(HttpServletRequest request, HttpServletResponse response) {
        
        
        String agentId = request.getParameter("agentId");
        String status = request.getParameter("status");
        
        List<String> downAgents = agentService.getAgentIdListOfAgentOwn(agentId);
        List<SelectBO> resp = new ArrayList<>();
        SelectBO selectBO = initialSelectBo();
        resp.add(selectBO);
        UcAgentDo ucAgentDo = null;
        for(String agent: downAgents){
            ucAgentDo = new UcAgentDo();
            ucAgentDo = agentService.getAgent(agent);
            //如果状态为空, 默认选择所有的直接下级代理, 如果不为空, 则选择对应状态的代理
            if(StringUtils.isNotBlank(status)){
                if(!StatusConstant.STATUS_ENABLE.equals(ucAgentDo.getStatus())){
                    continue;
                }
            }
            selectBO = new SelectBO();
            selectBO.setLabel(agentService.getAgentLevelName(ucAgentDo.getAgentId()) + ucAgentDo.getAgentName());
            selectBO.setValue(ucAgentDo.getAgentId());
            resp.add(selectBO);
        }
        return resp;
    }
    
    /**
     * 初始化 代理级联查询
     * @return
     */
    private SelectBO initialSelectBo(){
        SelectBO selectBO = new SelectBO();
        selectBO.setValue("all");
        selectBO.setLabel("所有代理");
        return selectBO;
    }
    
}
