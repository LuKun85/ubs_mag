package com.company.core.service;

import java.util.List;

import com.company.core.domain.MenuBO;

/**
 * Created by APPLE on 16/1/12.
 */
public interface MenuService {
    List<MenuBO> getAllEnabledMenu();

    List<MenuBO> getAllEnabledMenuByUserId(String userid);
}
