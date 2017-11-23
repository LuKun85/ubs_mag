package com.company.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.company.core.domain.RoleBO;
import com.company.core.entity.TblBTSSysRoleDO;
import com.company.core.entity.TblBTSSysUsrRoleDO;
import com.company.core.entity.TblBTSSysUsrRoleDOExample;
import com.company.core.mapper.TblBTSSysRoleDOMapper;
import com.company.core.mapper.TblBTSSysUsrRoleDOMapper;
import com.company.core.service.UserRoleService;

/**
 * Created by APPLE on 16/1/13.
 */
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    TblBTSSysUsrRoleDOMapper tblBTSSysUsrRoleDOMapper;

    @Autowired
    TblBTSSysRoleDOMapper    tblBTSSysRoleDOMapper;

    public List<RoleBO> getRolesByUserId(String userId) {
        TblBTSSysUsrRoleDOExample tblBTSSysUsrRoleDOExample = new TblBTSSysUsrRoleDOExample();
        tblBTSSysUsrRoleDOExample.createCriteria().andUsrIdEqualTo(userId);
        List<TblBTSSysUsrRoleDO> tblBTSSysUsrRoleDOList = tblBTSSysUsrRoleDOMapper
            .selectByExample(tblBTSSysUsrRoleDOExample);
        List<RoleBO> roleBOList = new ArrayList<RoleBO>();
        for (TblBTSSysUsrRoleDO tblBTSSysUsrRoleDO : tblBTSSysUsrRoleDOList) {
            TblBTSSysRoleDO tblBTSSysRoleDO = tblBTSSysRoleDOMapper
                .selectByPrimaryKey(tblBTSSysUsrRoleDO.getRoleId());

        }
        return null;
    }
}
