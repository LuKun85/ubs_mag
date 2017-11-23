package com.company.core.biz;

import com.company.core.entity.*;
import com.company.core.mapper.UcFeeDoMapper;
import com.company.core.mapper.UcInstDoMapper;
import com.company.core.mapper.UcInstInfoDoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service
@Slf4j
public class UCFeeBiz {


    @Autowired
    UcFeeDoMapper  ucFeeDoMapper;
    
    public int insertFee (UcFeeDo ucFeeDo){
        return ucFeeDoMapper.insertSelective(ucFeeDo);
    }
    
    public List<UcFeeDo> selectFeeList (String userType, String userCode, String status){
    
        UcFeeDoExample ucFeeDoExample = new UcFeeDoExample();
        UcFeeDoExample.Criteria criteria = ucFeeDoExample.createCriteria();
        criteria.andUserTypeEqualTo(userType).andUserCodeEqualTo(userCode);
        if(StringUtils.isNotBlank(status)){
            criteria.andStatusEqualTo(status);
        }
        return ucFeeDoMapper.selectByExample(ucFeeDoExample);
    }
    
    
    public UcFeeDo selectByPrimaryKey (UcFeeDo ucFeeDo){
        return ucFeeDoMapper.selectByPrimaryKey(ucFeeDo);
    }
    
    
    public UcFeeDo getFee (UcFeeDo ucFeeDo){
        return ucFeeDoMapper.selectByPrimaryKey(ucFeeDo);
    }
    
    public int updateFee (UcFeeDo ucFeeDo){
        return ucFeeDoMapper.updateByPrimaryKeySelective(ucFeeDo);
    }
    
    public int compareFees(String fisrtFee, String secondFee) {
        
        if(StringUtils.isBlank(fisrtFee)){
            fisrtFee = "0";
        }
        if(StringUtils.isBlank(secondFee)){
            secondFee = "0";
        }
        
        BigDecimal newFirst = new BigDecimal(fisrtFee).divide(new BigDecimal("1"), 6, BigDecimal.ROUND_HALF_UP);
        
        BigDecimal newSecond = new BigDecimal(secondFee).divide(new BigDecimal("1"), 6, BigDecimal.ROUND_HALF_UP);
        
        return  newFirst.compareTo(newSecond);
        
    }
    
    
    public Boolean zeroFee (String fee) {
        
        BigDecimal zerof = new BigDecimal("0");
        BigDecimal bf = new BigDecimal(fee);
        
        if(zerof.compareTo(bf) == 0){
            return true;
        }
        return false;
    }
    
}
