package com.company.core.biz;

import com.company.core.entity.*;
import com.company.core.mapper.TblBtsUsrMapDoMapper;
import com.company.core.mapper.UcFeeDoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service
@Slf4j
public class TblBtsUserMapBiz {


    @Autowired
    TblBtsUsrMapDoMapper tblBtsUsrMapDoMapper;

    public TblBtsUsrMapDo selectByPrimaryKey(TblBtsUsrMapDoKey tblBtsUsrMapDoKey){
        return tblBtsUsrMapDoMapper.selectByPrimaryKey(tblBtsUsrMapDoKey);
    }
    
    public List<TblBtsUsrMapDo> selectByExample(TblBtsUsrMapDoExample tblBtsUsrMapDoExample){
        return tblBtsUsrMapDoMapper.selectByExample(tblBtsUsrMapDoExample);
    }
    
    public List<String> getLoginList(String userType, String userCode){
        
        List<String> idList = new ArrayList<>();
        TblBtsUsrMapDoExample tblBtsUsrMapDoExample = new TblBtsUsrMapDoExample();
        tblBtsUsrMapDoExample.createCriteria().andUsrTypeEqualTo(userType).andUserCodeEqualTo(userCode);
        List<TblBtsUsrMapDo> tblBtsUsrMapDos = tblBtsUsrMapDoMapper.selectByExample(tblBtsUsrMapDoExample);
        if(tblBtsUsrMapDos == null || tblBtsUsrMapDos.size() <=0 ){
            return idList;
        }
        
        for(TblBtsUsrMapDo t:tblBtsUsrMapDos){
            idList.add(t.getLoginSeq());
        }
        return idList;
    }
    
    public int insert(TblBtsUsrMapDo tblBtsUsrMapDo){
        return tblBtsUsrMapDoMapper.insertSelective(tblBtsUsrMapDo);
    }
    
    
}
