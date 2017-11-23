package com.company.core.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RecomCodeForm  extends BaseForm {
    
    private String instId;
    private String agentId;
    private String recomCodeType;
    private String category;
    private String categoryId;
    private String pwdRequired;
    private String recomCodePwd;
    private String recomCodeCount;
    private String expireDays;
    private String price;
    
    //查询用到的信息
    private String userId;
    private String status;
    private String regStatus;
    private String batchId;
    private String recomCode;
    
    //注册码页面
    private String instName;
    private String agentName;
    private String toAgentId;
    private String dispatchCount;
    
    //
    private List<String> toDispatchList;
    private String toDispatchRecomCodes;
    
    //上次查询agentId
    private String lastSearchAgentId;
    
}
