package com.company.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.company.core.domain.RoleBO;
import com.company.core.entity.TblBTSSysFunctionDO;
import com.company.core.entity.TblBTSSysFunctionDOExample;
import com.company.core.entity.TblBTSSysRoleDO;
import com.company.core.entity.TblBTSSysRoleDOExample;
import com.company.core.entity.TblBTSSysRoleFuncDO;
import com.company.core.entity.TblBTSSysRoleFuncDOKey;
import com.company.core.entity.TblBTSSysUsrRoleDO;
import com.company.core.entity.TblBTSSysUsrRoleDOKey;
import com.company.core.form.Pagination;
import com.company.core.mapper.TblBTSSysFunctionDOMapper;
import com.company.core.mapper.TblBTSSysRoleDOMapper;
import com.company.core.mapper.TblBTSSysRoleFuncDOMapper;
import com.company.core.mapper.TblBTSSysUsrRoleDOMapper;
import com.company.core.service.RoleService;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fireWorks on 2016/2/2.
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    TblBTSSysRoleDOMapper     roleDOMapper;

    @Autowired
    TblBTSSysUsrRoleDOMapper  usrRoleDOMapper;

    @Autowired
    TblBTSSysFunctionDOMapper functionDOMapper;

    @Autowired
    TblBTSSysRoleFuncDOMapper roleFuncDOMapper;

    public Pagination<RoleBO> getRoleList(String uid) {
        List<RoleBO> roleList = new ArrayList<RoleBO>();

        TblBTSSysRoleDOExample tblBTSSysRoleDOExample = new TblBTSSysRoleDOExample();
        tblBTSSysRoleDOExample.createCriteria().andRoleDisableTagEqualTo("1");

        int count = roleDOMapper.countByExample(tblBTSSysRoleDOExample);
        Pagination pagination = new Pagination(count, 1, 100);

        List<TblBTSSysRoleDO> roleDOList = roleDOMapper.selectByExample(tblBTSSysRoleDOExample);

        List<RoleBO> roleBOList = new ArrayList<RoleBO>();
        for (TblBTSSysRoleDO roleDO : roleDOList) {
            RoleBO roleBO = new RoleBO();
            roleBO.setRoleId(roleDO.getRoleId());
            roleBO.setRoleName(roleDO.getRoleName());
            roleBO.setRoleRemark(roleDO.getRoleRemark());
            roleBO.setRoleDisableTag(roleDO.getRoleDisableTag());
            TblBTSSysUsrRoleDOKey usrRoleDOKey = new TblBTSSysUsrRoleDOKey();
            usrRoleDOKey.setUsrId(uid);
            usrRoleDOKey.setRoleId(roleBO.getRoleId());
            TblBTSSysUsrRoleDO usrRoleDO = usrRoleDOMapper.selectByPrimaryKey(usrRoleDOKey);
            if (usrRoleDO == null) {
                roleBO.setChecked("未授权");
            } else {
                roleBO.setChecked("已授权");
            }
            roleBOList.add(roleBO);
        }
        pagination.addResult(roleBOList);

        return pagination;

    }

    public Map addRoleResouce(String sid, String rid) throws Exception {
        Map resultMap = new HashMap();

        TblBTSSysFunctionDOExample functionDOExample = new TblBTSSysFunctionDOExample();
        TblBTSSysFunctionDO functionDO = functionDOMapper.selectByPrimaryKey(sid);

        TblBTSSysRoleFuncDO roleFuncDO = new TblBTSSysRoleFuncDO();
        roleFuncDO.setRoleId(rid);
        roleFuncDO.setFuncId(sid);
        roleFuncDO.setRoleFuncRemark(functionDO.getFuncRemark());
        roleFuncDOMapper.insert(roleFuncDO);
        resultMap.put("statusCode", 200);
        resultMap.put("message", "操作成功!");

        return resultMap;
    }

    public Map cancelRoleResouce(String sid, String rid) throws Exception {

        Map resultMap = new HashMap();
        TblBTSSysRoleFuncDOKey roleFuncDOKey = new TblBTSSysRoleFuncDOKey();
        roleFuncDOKey.setFuncId(sid);
        roleFuncDOKey.setRoleId(rid);
        roleFuncDOMapper.deleteByPrimaryKey(roleFuncDOKey);
        resultMap.put("statusCode", 200);
        resultMap.put("message", "操作成功!");

        return resultMap;
    }

    public Map setRoleEnable(String roleId) throws Exception {
        Map resultMap = new HashMap();
        TblBTSSysRoleDOExample roleDOExample = new TblBTSSysRoleDOExample();
        roleDOExample.createCriteria().andRoleIdEqualTo(roleId);
        TblBTSSysRoleDO roleDO = new TblBTSSysRoleDO();
        roleDO.setRoleDisableTag("1");
        roleDOMapper.updateByExampleSelective(roleDO, roleDOExample);
        resultMap.put("statusCode", 200);
        resultMap.put("message", "操作成功!");

        return resultMap;

    }

    public Map setRoleDisable(String roleId) throws Exception {
        Map resultMap = new HashMap();
        TblBTSSysRoleDOExample roleDOExample = new TblBTSSysRoleDOExample();
        roleDOExample.createCriteria().andRoleIdEqualTo(roleId);
        TblBTSSysRoleDO roleDO = new TblBTSSysRoleDO();
        roleDO.setRoleDisableTag("0");
        roleDOMapper.updateByExampleSelective(roleDO, roleDOExample);
        resultMap.put("statusCode", 200);
        resultMap.put("message", "操作成功!");

        return resultMap;

    }

    public Pagination<RoleBO> getTheRole(RoleBO roleBO) {

        TblBTSSysRoleDOExample tblBTSSysRoleDOExample = new TblBTSSysRoleDOExample();
        TblBTSSysRoleDOExample.Criteria cri = tblBTSSysRoleDOExample.createCriteria();
        if (!(roleBO.getRoleName() == null || roleBO.getRoleName().trim().equals(""))) {
            cri.andRoleNameEqualTo(roleBO.getRoleName());
        }

        int count = roleDOMapper.countByExample(tblBTSSysRoleDOExample);
        Pagination pagination = new Pagination(count, roleBO.getPageCurrent(),
            roleBO.getPageSize());
        PageHelper.startPage(roleBO.getPageCurrent(), roleBO.getPageSize());
        List<TblBTSSysRoleDO> roleDOList = roleDOMapper.selectByExample(tblBTSSysRoleDOExample);

        List<RoleBO> roleBOList = new ArrayList<RoleBO>();
        for (TblBTSSysRoleDO roleDO : roleDOList) {
            RoleBO roleBO1 = new RoleBO();
            roleBO1.setRoleId(roleDO.getRoleId());
            roleBO1.setRoleName(roleDO.getRoleName());
            if (roleDO.getRoleDisableTag().trim().equals("1")) {
                roleBO1.setRoleDisableTag("启用");
            } else {
                roleBO1.setRoleDisableTag("禁用");
            }
            roleBO1.setRoleRemark(roleDO.getRoleRemark());
            roleBOList.add(roleBO1);
        }
        pagination.addResult(roleBOList);

        return pagination;

    }

    public Pagination<RoleBO> getAllRole(RoleBO roleBO) {
        List<RoleBO> userList = new ArrayList<RoleBO>();

        TblBTSSysRoleDOExample tblBTSSysRoleDOExample = new TblBTSSysRoleDOExample();
        TblBTSSysRoleDOExample.Criteria cri = tblBTSSysRoleDOExample.createCriteria();
        int count = roleDOMapper.countByExample(tblBTSSysRoleDOExample);
        Pagination pagination = new Pagination(count, roleBO.getPageCurrent(),
            roleBO.getPageSize());
        PageHelper.startPage(roleBO.getPageCurrent(), roleBO.getPageSize());
        List<TblBTSSysRoleDO> roleDOList = roleDOMapper.selectByExample(tblBTSSysRoleDOExample);

        List<RoleBO> roleBOList = new ArrayList<RoleBO>();
        for (TblBTSSysRoleDO roleDO : roleDOList) {
            RoleBO roleBO1 = new RoleBO();
            roleBO1.setRoleId(roleDO.getRoleId());
            roleBO1.setRoleName(roleDO.getRoleName());
            if (roleDO.getRoleDisableTag().trim().equals("1")) {
                roleBO1.setRoleDisableTag("启用");
            } else {
                roleBO1.setRoleDisableTag("禁用");
            }
            roleBO1.setRoleRemark(roleDO.getRoleRemark());
            roleBOList.add(roleBO1);
        }
        pagination.addResult(roleBOList);

        return pagination;

    }

    public Map addNewRole(RoleBO roleBO) {
        Map resultMap = new HashMap();

        TblBTSSysRoleDO refSysRoleDO = new TblBTSSysRoleDO();
        refSysRoleDO.setRoleName(roleBO.getRoleName());
        refSysRoleDO.setRoleRemark(roleBO.getRoleRemark());
        refSysRoleDO.setRoleId(roleBO.getRoleId());
        refSysRoleDO.setRoleDisableTag(roleBO.getRoleDisableTag());
        int rt = roleDOMapper.insert(refSysRoleDO);
        if (rt != 0) {
            resultMap.put("statusCode", 200);
            resultMap.put("message", "操作成功!");
        } else {
            resultMap.put("statusCode", 200);
            resultMap.put("message", "操作失败!");
        }
        return resultMap;

    }

    public RoleBO getById(String roleId) {
        TblBTSSysRoleDOExample tblBTSSysRoleDOExample = new TblBTSSysRoleDOExample();
        tblBTSSysRoleDOExample.createCriteria().andRoleIdEqualTo(roleId);
        List<TblBTSSysRoleDO> tblBTSSysRoleDOList = roleDOMapper
            .selectByExample(tblBTSSysRoleDOExample);
        return convertRoleDOToRoleBO(tblBTSSysRoleDOList.get(0));
    }

    private RoleBO convertRoleDOToRoleBO(TblBTSSysRoleDO tblBTSSysRoleDO) {
        RoleBO roleBO = new RoleBO();
        roleBO.setRoleId(tblBTSSysRoleDO.getRoleId());
        roleBO.setRoleName(tblBTSSysRoleDO.getRoleName());
        roleBO.setRoleRemark(tblBTSSysRoleDO.getRoleRemark());
        roleBO.setRoleDisableTag(tblBTSSysRoleDO.getRoleDisableTag());
        return roleBO;
    }

    @Transactional(rollbackFor = Exception.class)
    public Map setAuthory(String roleId, List<String> list) {
        Map resultMap = new HashMap();
        List<TblBTSSysRoleFuncDO> roleFuncDOList = new ArrayList<TblBTSSysRoleFuncDO>();
        try {
            roleFuncDOMapper.deleteAllAuthory(roleId);
            for (String funcId : list) {
                TblBTSSysFunctionDOExample functionDOExample = new TblBTSSysFunctionDOExample();
                TblBTSSysFunctionDO functionDO = functionDOMapper.selectByPrimaryKey(funcId);

                TblBTSSysRoleFuncDO roleFuncDO = new TblBTSSysRoleFuncDO();
                roleFuncDO.setRoleId(roleId);
                roleFuncDO.setFuncId(funcId);
                roleFuncDO.setRoleFuncRemark(functionDO.getFuncRemark());
                roleFuncDOList.add(roleFuncDO);
            }
            roleFuncDOMapper.insertBatch(roleFuncDOList);
            resultMap.put("statusCode", 200);
            resultMap.put("message", "操作成功!");
        } catch (Exception e) {
            resultMap.put("statusCode", 300);
            resultMap.put("message", "操作失败!");
            throw e;
        }
        return resultMap;
    }
}
