package com.company.core.biz;

import com.company.core.entity.UcFeeDo;
import com.company.core.entity.UcFeeDoExample;
import com.company.core.mapper.UcFeeDoMapper;
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
public class CommonBiz {

    
    public String getLast6BytesOfPhone (String phone){
        return phone.substring(phone.length() - 6);
    }

}
