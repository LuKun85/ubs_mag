package com.company.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.core.constant.SystemConstant;
import com.company.core.domain.FuncBO;
import com.company.core.domain.RoleBO;
import com.company.core.domain.UserBO;
import com.company.core.entity.TblBTSSysFunctionDO;
import com.company.core.entity.TblBTSSysFunctionDOExample;
import com.company.core.entity.TblBTSSysFunctionVO;
import com.company.core.entity.TblBTSSysRoleVO;
import com.company.core.entity.TblBTSSysUsrDO;
import com.company.core.entity.TblBTSSysUsrVO;
import com.company.core.mapper.TblBTSSysFunctionDOMapper;
import com.company.core.mapper.TblBTSSysUsrDOMapper;
import com.company.core.mapper.UserAuthorMapper;
import com.company.core.service.UserAuthorService;

@Service("userAuthorService")
public class UserAuthorServiceImpl implements UserAuthorService {

    @Autowired
    UserAuthorMapper          userAuthorMapper;

    @Autowired
    TblBTSSysUsrDOMapper      tblBTSSysUsrDOMapper;

    @Autowired
    TblBTSSysFunctionDOMapper tblBTSSysFunctionDOMapper;

    public UserBO getAuthorByUserId(String userId) {
        List<RoleBO> roleBOList = new ArrayList<RoleBO>();
        TblBTSSysUsrDO user = tblBTSSysUsrDOMapper.selectByPrimaryKey(userId);

        if (SystemConstant.ROOT.equals(user.getUsrName())) {
            TblBTSSysFunctionDOExample example = new TblBTSSysFunctionDOExample();
            example.createCriteria();
            List<TblBTSSysFunctionDO> rootPrimary = tblBTSSysFunctionDOMapper
                .selectByExample(example);

            List<FuncBO> funcBOList = new ArrayList<FuncBO>();
            FuncBO func = null;
            for (TblBTSSysFunctionDO function : rootPrimary) {
                func = new FuncBO();
                func.setFuncUrl(function.getFuncUrl());
                funcBOList.add(func);
            }
            RoleBO role = new RoleBO();
            role.setFuncBOList(funcBOList);
            role.setRoleName("root");
            roleBOList.add(role);
        } else {
            TblBTSSysUsrVO tblBTSSysUsrVO = userAuthorMapper.getAuthorByUserId(userId);
            List<TblBTSSysRoleVO> tblBTSSysRoleVOList = null;
            if (tblBTSSysUsrVO != null) {
                tblBTSSysRoleVOList = tblBTSSysUsrVO.getTblBTSSysRoleVOList();
            }
            if (tblBTSSysRoleVOList != null) {
                for (TblBTSSysRoleVO tblBTSSysRoleVO : tblBTSSysRoleVOList) {
                    RoleBO roleBO = new RoleBO();
                    roleBO.setRoleId(tblBTSSysRoleVO.getRoleId());
                    roleBO.setRoleName(tblBTSSysRoleVO.getRoleName());
                    List<TblBTSSysFunctionVO> tblBTSSysFunctionVOList = tblBTSSysRoleVO
                        .getTblBTSSysFunctionVOList();
                    List<FuncBO> funcBOList = new ArrayList<FuncBO>();
                    for (TblBTSSysFunctionVO tblBTSSysFunctionVO : tblBTSSysFunctionVOList) {
                        FuncBO funcBO = new FuncBO();
                        funcBO.setFuncId(tblBTSSysFunctionVO.getFuncId());
                        funcBO.setFuncUrl(tblBTSSysFunctionVO.getFuncUrl());
                        funcBOList.add(funcBO);
                    }
                    roleBO.setFuncBOList(funcBOList);
                    roleBOList.add(roleBO);
                }
            }
        }

        UserBO userBO = new UserBO();
        userBO.setUsrId(userId);
        userBO.setRoleBOList(roleBOList);
        return userBO;
    }
}
