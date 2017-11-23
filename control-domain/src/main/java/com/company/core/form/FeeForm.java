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
public class FeeForm extends BaseForm {
    
    private String instId;
    private String agentId;
    
    //费率信息
    private String defaultFeeFixed; //默认单笔固定
    private String defaultFeeRate;  //默认交易费率
    private String EffectiveFeeFixed; //实收交易单笔
    private String EffectiveFeeRate;  //实收费率
    
}
