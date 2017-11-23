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
public class SelectBO {

    private String label;
    private String value;
    private String description;
    private String value_;
    private String description_;
    
}
