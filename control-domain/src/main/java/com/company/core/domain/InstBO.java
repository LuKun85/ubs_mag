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
public class InstBO {

    private String instId;
    private String instName;
    private String instShortName;
    private String status;
    private String createUser;
    private String createTime;
    
}
