package com.company.core.biz;

import com.company.core.entity.UcReccomCodeCntlDo;
import com.company.core.entity.UcReccomCodeCntlDoExample;
import com.company.core.mapper.UcReccomCodeCntlDoMapper;
import lombok.extern.slf4j.Slf4j;
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
public class UCRecomCodeControlBiz {
    
    @Autowired
    UcReccomCodeCntlDoMapper ucReccomCodeCntlDoMapper;
    
    public List<UcReccomCodeCntlDo> selectByExample(){
        
        UcReccomCodeCntlDoExample ucReccomCodeCntlDoExample = new UcReccomCodeCntlDoExample();
        ucReccomCodeCntlDoExample.createCriteria().andReccomCodeIsNull();
        return ucReccomCodeCntlDoMapper.selectByExample(ucReccomCodeCntlDoExample);
    }
    
    public List<UcReccomCodeCntlDo> selectByExample(UcReccomCodeCntlDoExample ucReccomCodeCntlDoExample){
  
        return ucReccomCodeCntlDoMapper.selectByExample(ucReccomCodeCntlDoExample);
    }
    
    public int countByExample(UcReccomCodeCntlDoExample ucReccomCodeCntlDoExample){
        
        return ucReccomCodeCntlDoMapper.countByExample(ucReccomCodeCntlDoExample);
    }
    
    public int insertSelective(UcReccomCodeCntlDo ucReccomCodeCntlDo){

        return ucReccomCodeCntlDoMapper.insertSelective(ucReccomCodeCntlDo);
    }
    
    public int updateSelective(UcReccomCodeCntlDo ucReccomCodeCntlDo){
        
        return ucReccomCodeCntlDoMapper.updateByPrimaryKey(ucReccomCodeCntlDo);
    }
    
    public UcReccomCodeCntlDo selectByPrimaryKey(String recomCode){
        
        return ucReccomCodeCntlDoMapper.selectByPrimaryKey(recomCode);
    }

}
