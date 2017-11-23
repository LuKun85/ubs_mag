package com.company.core.biz;

import com.company.core.constant.UserConstant;
import com.company.core.entity.UcAgentDo;
import com.company.core.entity.UcAgentDoExample;
import com.company.core.entity.UcAgentInfoDo;
import com.company.core.entity.UcAgentInfoDoExample;
import com.company.core.mapper.UcAgentDoMapper;
import com.company.core.mapper.UcAgentInfoDoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service
@Slf4j
public class UCAgentBiz {

    @Autowired
    UcAgentDoMapper ucAgentDoMapper;
    @Autowired
    UcAgentInfoDoMapper ucAgentInfoDoMapper;
    
    
    public List<UcAgentDo> selectAllAgents(){
    
        UcAgentDoExample ucAgentDoExample = new UcAgentDoExample();
        ucAgentDoExample.createCriteria().andInstIdIsNotNull().andAgentIdIsNotNull();
        return ucAgentDoMapper.selectByExample(ucAgentDoExample);
        
    }
    
    public List<UcAgentDo> selectAllAgents(String instId, String type, String status, String agentName){
        
        UcAgentDoExample ucAgentDoExample = new UcAgentDoExample();
        UcAgentDoExample.Criteria criteria = ucAgentDoExample.createCriteria();
        if(StringUtils.isNotBlank(instId)){
            criteria.andInstIdEqualTo(instId);
        }
        if(StringUtils.isNotBlank(type)){
            criteria.andAgentTypeEqualTo(type);
        }
        if(StringUtils.isNotBlank(status)){
            criteria.andStatusEqualTo(status);
        }
        if(StringUtils.isNotBlank(agentName)){
            criteria.andAgentNameLike( "%" + agentName + "%");
        }
        return ucAgentDoMapper.selectByExample(ucAgentDoExample);
        
    }
    
    public UcAgentDo selectAgent(String agent){
    
        UcAgentDoExample ucAgentDoExample = new UcAgentDoExample();
        ucAgentDoExample.createCriteria().andAgentIdEqualTo(agent);
        List<UcAgentDo> ucAgentDos = ucAgentDoMapper.selectByExample(ucAgentDoExample);
        if(ucAgentDos != null && ucAgentDos.size() > 0){
            return ucAgentDos.get(0);
        } else {
            return null;
        }
    }
    
    public UcAgentInfoDo selectAgentInfo(String agent){

        return ucAgentInfoDoMapper.selectByPrimaryKey(agent);
    }
    
    public UcAgentDo selectDefaultAgent(String inst){
        
        UcAgentDoExample ucAgentDoExample = new UcAgentDoExample();
        ucAgentDoExample.createCriteria().andInstIdEqualTo(inst).andAgentTypeEqualTo(UserConstant.USER_TYPE_DEFAULT);
        List<UcAgentDo> ucAgentDos = ucAgentDoMapper.selectByExample(ucAgentDoExample);
        if(ucAgentDos != null && ucAgentDos.size() > 0){
            return ucAgentDos.get(0);
        } else {
            return null;
        }
    }
    
    public int countByExample(UcAgentDoExample ucAgentDoExample){
        return ucAgentDoMapper.countByExample(ucAgentDoExample);
    }
    
    public List<UcAgentDo> selectByExample(UcAgentDoExample ucAgentDoExample){
        return ucAgentDoMapper.selectByExample(ucAgentDoExample);
    }
    
    public List<UcAgentDo> selectAgentsByInstId(String instId){
        
        UcAgentDoExample ucAgentDoExample = new UcAgentDoExample();
        ucAgentDoExample.createCriteria().andInstIdEqualTo(instId);
        return ucAgentDoMapper.selectByExample(ucAgentDoExample);
        
    }
    
    public List<UcAgentInfoDo> selectByName(String name){
        
        UcAgentInfoDoExample ucAgentInfoDoExample = new UcAgentInfoDoExample();
        ucAgentInfoDoExample.createCriteria().andAgentNameEqualTo(name);
        return ucAgentInfoDoMapper.selectByExample(ucAgentInfoDoExample);
        
    }
    
    public List<UcAgentInfoDo> selectByShortName(String name){
        
        UcAgentInfoDoExample ucAgentInfoDoExample = new UcAgentInfoDoExample();
        ucAgentInfoDoExample.createCriteria().andAgentShortNameEqualTo(name);
        return ucAgentInfoDoMapper.selectByExample(ucAgentInfoDoExample);
        
    }
    
    public int insertAgent(UcAgentDo ucAgentDo){
        
        return ucAgentDoMapper.insertSelective(ucAgentDo);
        
    }
    
    public int insertAgentInfo(UcAgentInfoDo ucAgentInfoDo){
        
        return ucAgentInfoDoMapper.insertSelective(ucAgentInfoDo);
        
    }
    
    public int updateAgent(UcAgentDo ucAgentDo){
        
        return ucAgentDoMapper.updateByPrimaryKeySelective(ucAgentDo);
        
    }
    
    public int updateAgentInfo(UcAgentInfoDo ucAgentInfoDo){
        
        return ucAgentInfoDoMapper.updateByPrimaryKeySelective(ucAgentInfoDo);
        
    }
    
    
    

}
