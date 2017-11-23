package com.company.core.biz;

import com.company.core.constant.StatusConstant;
import com.company.core.entity.*;
import com.company.core.mapper.UcInstDoMapper;
import com.company.core.mapper.UcInstInfoDoMapper;
import com.company.core.mapper.UcProdDoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Slf4j
@Service
public class UCProdBiz {

    @Autowired
    UcProdDoMapper ucProdDoMapper;

    
    public List<UcProdDo> selectProdListEnabled(){
    
        UcProdDoExample ucProdDoExample = new UcProdDoExample();
        ucProdDoExample.createCriteria().andProdIdIsNotNull().andProdStatusEqualTo(StatusConstant.STATUS_ENABLE);
        return ucProdDoMapper.selectByExample(ucProdDoExample);
    }
    
    
}
