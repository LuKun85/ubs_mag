package com.company.core.service;

import java.util.List;
import java.util.Map;

import com.company.core.domain.RoleBO;
import com.company.core.entity.UcInstDo;
import com.company.core.form.Pagination;

/**
 * Created by fireWorks on 2016/2/2.
 */
public interface RoleService {

    public Pagination<RoleBO> getRoleList(String uid);

    RoleBO getById(String roleId);

    Map addNewRole(RoleBO roleBO);

    Pagination<RoleBO> getAllRole(RoleBO roleBO);

    Pagination<RoleBO> getTheRole(RoleBO roleBO);

    Map setRoleDisable(String roleId) throws Exception;

    Map setRoleEnable(String roleId) throws Exception;

    Map cancelRoleResouce(String sid, String rid) throws Exception;

    Map setAuthory(String roleId, List<String> list);
    
}
