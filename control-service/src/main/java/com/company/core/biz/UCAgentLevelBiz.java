package com.company.core.biz;

import com.company.core.entity.*;
import com.company.core.mapper.UcAgentDoMapper;
import com.company.core.mapper.UcAgentInfoDoMapper;
import com.company.core.mapper.UcAgentLevelDoMapper;
import com.company.core.mapper.UcAgentUserRelDoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service
@Slf4j
public class UCAgentLevelBiz {

    @Autowired
    UcAgentLevelDoMapper ucAgentLevelDoMapper;

    
    public List<UcAgentLevelDo> getAllDownAgentLevelList(String upAgentId){
    
        UcAgentLevelDoExample ucAgentLevelDoExample = new UcAgentLevelDoExample();
        UcAgentLevelDoExample.Criteria criteria = ucAgentLevelDoExample.createCriteria();
        criteria.andUpAgentIdEqualTo(upAgentId);
        return ucAgentLevelDoMapper.selectByExample(ucAgentLevelDoExample);
    
    }
    
    public List<String> getAllDownAgentIdList(String upAgentId){
        
        List<String> agentIdList = new ArrayList<>();
        UcAgentLevelDoExample ucAgentLevelDoExample = new UcAgentLevelDoExample();
        UcAgentLevelDoExample.Criteria criteria = ucAgentLevelDoExample.createCriteria();
        criteria.andUpAgentIdEqualTo(upAgentId);
        List<UcAgentLevelDo> list = ucAgentLevelDoMapper.selectByExample(ucAgentLevelDoExample);
        if(list != null && list.size() >0){
            for(UcAgentLevelDo s: list){
                agentIdList.add(s.getAgentId());
            }
        }
        return agentIdList;
    }
    
    
    public UcAgentLevelDo getAgentLevel(String agentId){
        
        return ucAgentLevelDoMapper.selectByPrimaryKey(agentId);

    }
    
}
