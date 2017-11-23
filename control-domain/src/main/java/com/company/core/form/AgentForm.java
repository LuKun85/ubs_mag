package com.company.core.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AgentForm extends BaseForm {
    
    private String agentId;
    private String instId;
    private String agentType;
    private String agentName;
    private String agentShortName;
    private String businessLicense;
    private String category;
    private String categoryId;
    private String agentOk;
    private String agentCountLimit;
    private String limitArea;
    private String limitAreaCode;
    private String legalPersonName;
    private String legalPersonPhone;
    private String legalPersonMail;
    private String legalPersonIdType;
    private String legalPersonId;
    private String legalPersonAddress;
    
    private String contactName;
    private String contactPhone;
    private String contactMail;
    private String contactIdType;
    private String contactCertId;
    private String contactAddress;
    
    //费率信息
    private String defaultFeeFixed; //默认单笔固定
    private String defaultFeeRate;  //默认交易费率
    private String EffectiveFeeFixed; //实收交易单笔
    private String EffectiveFeeRate;  //实收费率
    
    private String status;
    
}
