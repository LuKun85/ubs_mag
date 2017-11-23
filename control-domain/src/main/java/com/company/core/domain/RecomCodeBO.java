package com.company.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RecomCodeBO {

    private String batchId;
    private String recomCodeSeq;
    private String recomCode;
    private String status;
    private String userType;
    private String userCode;
    private String userId;
    private String pwdRequired;
    private String recomCodePwd;
    private String expireDate;
    private String price;
    private String createTime;
    private String createUser;
    private String createSource;
    
}
