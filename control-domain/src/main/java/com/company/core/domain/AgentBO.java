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
public class AgentBO {

    private String instId;
    private String agentId;
    private String agentName;
    private String agentShortName;
    private String status;
    private String createUser;
    private String createTime;
    
}
