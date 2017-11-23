package com.company.core.biz;

import com.company.core.entity.UcReccomCodeCntlDo;
import com.company.core.entity.UcReccomCodeCntlDoExample;
import com.company.core.entity.UcRecomCodeUserRelDo;
import com.company.core.entity.UcRecomCodeUserRelDoExample;
import com.company.core.mapper.UcReccomCodeCntlDoMapper;
import com.company.core.mapper.UcRecomCodeUserRelDoMapper;
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
public class UCRecomCodeUserRelBiz {
    
    @Autowired
    UcRecomCodeUserRelDoMapper ucRecomCodeUserRelDoMapper;
    
    
    public UcRecomCodeUserRelDo selectByCode (String code){
    
        UcRecomCodeUserRelDoExample ucRecomCodeUserRelDoExample = new UcRecomCodeUserRelDoExample();
        ucRecomCodeUserRelDoExample.createCriteria().andReccomCodeEqualTo(code);
        List<UcRecomCodeUserRelDo> ucRecomCodeUserRelDoList = ucRecomCodeUserRelDoMapper.selectByExample(ucRecomCodeUserRelDoExample);
        if(ucRecomCodeUserRelDoList != null & ucRecomCodeUserRelDoList.size() >0){
            return ucRecomCodeUserRelDoList.get(0);
        }
        return null;
    }
    


}
