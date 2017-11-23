package com.company.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.core.Enum.StatisticalTypeEnum;

/**
 * Created by APPLE on 15/12/29.
 */
@Controller
public class HomeController {
    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/home/{key:.*}")
    public String goToHomePage(@PathVariable String key) {
        StatisticalTypeEnum typeEnum = StatisticalTypeEnum.valueOf(key);
        return "home/" + typeEnum.getType();
    }
}
