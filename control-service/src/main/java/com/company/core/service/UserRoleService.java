package com.company.core.service;

import java.util.List;

import com.company.core.domain.RoleBO;

/**
 * Created by APPLE on 16/1/13.
 */
public interface UserRoleService {
    List<RoleBO> getRolesByUserId(String userId);
}
