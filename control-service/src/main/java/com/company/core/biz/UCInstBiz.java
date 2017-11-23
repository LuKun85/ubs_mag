package com.company.core.biz;

import com.company.core.entity.UcInstDo;
import com.company.core.entity.UcInstDoExample;
import com.company.core.entity.UcInstInfoDo;
import com.company.core.entity.UcInstInfoDoExample;
import com.company.core.mapper.UcInstDoMapper;
import com.company.core.mapper.UcInstInfoDoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service
@Slf4j
public class UCInstBiz {

    @Autowired
    UcInstDoMapper ucInstDoMapper;
    @Autowired
    UcInstInfoDoMapper ucInstInfoDoMapper;
    
    
    public List<UcInstDo> selectByExample(){
    
        UcInstDoExample ucInstDoExample = new UcInstDoExample();
        ucInstDoExample.createCriteria().andInstIdIsNotNull();
        return ucInstDoMapper.selectByExample(ucInstDoExample);
        
    }
    
    public int countByExample(UcInstDoExample ucInstDoExample){
        
        return ucInstDoMapper.countByExample(ucInstDoExample);
        
    }
    
    
    public List<UcInstDo>  selectByExample(UcInstDoExample ucInstDoExample){
        
        return ucInstDoMapper.selectByExample(ucInstDoExample);
        
    }
    
    public List<UcInstDo> selectByStatus(String status){
        
        UcInstDoExample ucInstDoExample = new UcInstDoExample();
        ucInstDoExample.createCriteria().andStatusEqualTo(status);
        return ucInstDoMapper.selectByExample(ucInstDoExample);
        
    }
    
    public UcInstDo selectByPrimaryKey(String instId){
        
        return  ucInstDoMapper.selectByPrimaryKey(instId);
    }
    
    
    public List<UcInstInfoDo> selectByName(String name){
    
        UcInstInfoDoExample ucInstInfoDoExample = new UcInstInfoDoExample();
        ucInstInfoDoExample.createCriteria().andInstNameEqualTo(name);
        return ucInstInfoDoMapper.selectByExample(ucInstInfoDoExample);
        
    }
    
    public List<UcInstInfoDo> selectByShortName(String name){
    
        UcInstInfoDoExample ucInstInfoDoExample = new UcInstInfoDoExample();
        ucInstInfoDoExample.createCriteria().andInstShortNameEqualTo(name);
        return ucInstInfoDoMapper.selectByExample(ucInstInfoDoExample);
        
    }

    public int insertInst(UcInstDo ucInstDo){
        
        return ucInstDoMapper.insertSelective(ucInstDo);
        
    }
    
    
    public int updateInst(UcInstDo ucInstDo){
        
        return ucInstDoMapper.updateByPrimaryKey(ucInstDo);
        
    }
    
    public int insertInstInfo(UcInstInfoDo ucInstInfoDo){
        
        return ucInstInfoDoMapper.insertSelective(ucInstInfoDo);
        
    }
    
    public int updateInstInfo(UcInstInfoDo ucInstInfoDo){
        
        return ucInstInfoDoMapper.updateByPrimaryKey(ucInstInfoDo);
        
    }
    
    public UcInstInfoDo getInstInfo(String instId){
        
        return ucInstInfoDoMapper.selectByPrimaryKey(instId);
        
    }
    
    

}
