package com.company.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.company.core.domain.FuncBO;
import com.company.core.domain.RoleBO;
import com.company.core.entity.TblBTSSysFunctionDO;
import com.company.core.entity.TblBTSSysFunctionDOExample;
import com.company.core.entity.TblBTSSysRoleFuncDO;
import com.company.core.entity.TblBTSSysRoleFuncDOKey;
import com.company.core.form.Pagination;
import com.company.core.mapper.TblBTSSysFunctionDOMapper;
import com.company.core.mapper.TblBTSSysRoleFuncDOMapper;
import com.company.core.service.FuncService;

/**
 * Created by fireWorks on 2016/2/3.
 */
@Service("funcService")
public class FuncServiceImpl implements FuncService {

    private static final Logger logger = LoggerFactory.getLogger(FuncServiceImpl.class);

    @Autowired
    TblBTSSysFunctionDOMapper   functionDOMapper;

    @Autowired
    TblBTSSysRoleFuncDOMapper   roleFuncDOMapper;

    public Pagination<FuncBO> getFuncList(String rid) {
        List<RoleBO> roleList = new ArrayList<RoleBO>();

        TblBTSSysFunctionDOExample tblBTSSysFunctionDOExample = new TblBTSSysFunctionDOExample();
        tblBTSSysFunctionDOExample.createCriteria().andFuncDisableTagEqualTo("1");

        int count = functionDOMapper.countByExample(tblBTSSysFunctionDOExample);
        Pagination pagination = new Pagination(count, 1, 100);

        List<TblBTSSysFunctionDO> funcDOList = functionDOMapper
            .selectByExample(tblBTSSysFunctionDOExample);

        List<FuncBO> funcBOList = new ArrayList<FuncBO>();
        for (TblBTSSysFunctionDO functionDO : funcDOList) {
            FuncBO funcBO = new FuncBO();
            funcBO.setFuncId(functionDO.getFuncId());
            funcBO.setFuncName(functionDO.getFuncName());
            funcBO.setFuncDesc(functionDO.getFuncDesc());
            funcBO.setFuncRemark(functionDO.getFuncRemark());
            funcBO.setFuncDisableTag(functionDO.getFuncDisableTag());
            TblBTSSysRoleFuncDOKey roleFuncDOKey = new TblBTSSysRoleFuncDOKey();
            roleFuncDOKey.setFuncId(funcBO.getFuncId());
            roleFuncDOKey.setRoleId(rid);
            TblBTSSysRoleFuncDO roleFuncDO = roleFuncDOMapper.selectByPrimaryKey(roleFuncDOKey);
            if (roleFuncDO == null) {
                funcBO.setChecked("未授权");
            } else {
                funcBO.setChecked("已授权");
            }
            funcBOList.add(funcBO);
        }
        pagination.addResult(funcBOList);

        return pagination;

    }

    public Map setFuncEnable(String funcId) throws Exception {
        Map resultMap = new HashMap();
        TblBTSSysFunctionDOExample functionDOExample = new TblBTSSysFunctionDOExample();
        functionDOExample.createCriteria().andFuncIdEqualTo(funcId);
        TblBTSSysFunctionDO functionDO = new TblBTSSysFunctionDO();
        functionDO.setFuncDisableTag("1");
        functionDOMapper.updateByExampleSelective(functionDO, functionDOExample);
        resultMap.put("statusCode", 200);
        resultMap.put("message", "操作成功!");

        return resultMap;

    }

    public Map setFuncDisable(String funcId) throws Exception {
        Map resultMap = new HashMap();
        TblBTSSysFunctionDOExample functionDOExample = new TblBTSSysFunctionDOExample();
        functionDOExample.createCriteria().andFuncIdEqualTo(funcId);
        TblBTSSysFunctionDO functionDO = new TblBTSSysFunctionDO();
        functionDO.setFuncDisableTag("0");
        functionDOMapper.updateByExampleSelective(functionDO, functionDOExample);
        resultMap.put("statusCode", 200);
        resultMap.put("message", "操作成功!");

        return resultMap;

    }

    public Pagination<FuncBO> getTheFunc(FuncBO funcBO) {
        List<RoleBO> userList = new ArrayList<RoleBO>();

        TblBTSSysFunctionDOExample tblBTSSysFunctionDOExample = new TblBTSSysFunctionDOExample();
        TblBTSSysFunctionDOExample.Criteria cri = tblBTSSysFunctionDOExample.createCriteria();
        if (!(funcBO.getFuncName() == null || funcBO.getFuncName().trim().equals(""))) {
            cri.andFuncNameEqualTo(funcBO.getFuncName());
        }

        int count = functionDOMapper.countByExample(tblBTSSysFunctionDOExample);
        Pagination pagination = new Pagination(count, funcBO.getPageCurrent(),
            funcBO.getPageSize());
        PageHelper.startPage(funcBO.getPageCurrent(), funcBO.getPageSize());
        List<TblBTSSysFunctionDO> functionDOList = functionDOMapper
            .selectByExample(tblBTSSysFunctionDOExample);

        List<FuncBO> funcBOList = new ArrayList<FuncBO>();
        for (TblBTSSysFunctionDO functionDO : functionDOList) {
            FuncBO funcBO1 = new FuncBO();
            funcBO1.setFuncId(functionDO.getFuncId());
            funcBO1.setFuncName(functionDO.getFuncName());
            funcBO1.setFuncFatherId(functionDO.getFuncFatherId());
            funcBO1.setFuncDesc(functionDO.getFuncDesc());
            funcBO1.setFuncRemark(functionDO.getFuncRemark());
            if (functionDO.getFuncDisableTag().trim().equals("1")) {
                funcBO1.setFuncDisableTag("启用");
            } else {
                funcBO1.setFuncDisableTag("禁用");
            }
            funcBO1.setFuncTag(functionDO.getFuncTag());
            funcBO1.setFuncLevel(functionDO.getFuncLevel());
            funcBO1.setFuncUrl(functionDO.getFuncUrl());
            funcBO1.setFuncIcon(functionDO.getFuncIcon());
            funcBO1.setFuncPriority(functionDO.getFuncPriority());
            funcBOList.add(funcBO1);
        }
        pagination.addResult(funcBOList);

        return pagination;

    }

    public Pagination<FuncBO> getAllFunc(FuncBO funcBO) {
        List<RoleBO> userList = new ArrayList<RoleBO>();

        TblBTSSysFunctionDOExample tblBTSSysFunctionDOExample = new TblBTSSysFunctionDOExample();
        TblBTSSysFunctionDOExample.Criteria cri = tblBTSSysFunctionDOExample.createCriteria();

        int count = functionDOMapper.countByExample(tblBTSSysFunctionDOExample);
        Pagination pagination = new Pagination(count, funcBO.getPageCurrent(),
            funcBO.getPageSize());
        PageHelper.startPage(funcBO.getPageCurrent(), funcBO.getPageSize());
        List<TblBTSSysFunctionDO> functionDOList = functionDOMapper
            .selectByExample(tblBTSSysFunctionDOExample);

        List<FuncBO> funcBOList = new ArrayList<FuncBO>();
        for (TblBTSSysFunctionDO functionDO : functionDOList) {
            FuncBO funcBO1 = new FuncBO();
            funcBO1.setFuncId(functionDO.getFuncId());
            funcBO1.setFuncName(functionDO.getFuncName());
            funcBO1.setFuncFatherId(functionDO.getFuncFatherId());
            funcBO1.setFuncDesc(functionDO.getFuncDesc());
            funcBO1.setFuncRemark(functionDO.getFuncRemark());
            // funcBO1.setFuncDisableTag(functionDO.getFuncDisableTag());
            if (functionDO.getFuncDisableTag().trim().equals("1")) {
                funcBO1.setFuncDisableTag("启用");
            } else {
                funcBO1.setFuncDisableTag("禁用");
            }
            funcBO1.setFuncTag(functionDO.getFuncTag());
            funcBO1.setFuncLevel(functionDO.getFuncLevel());
            funcBO1.setFuncUrl(functionDO.getFuncUrl());
            funcBO1.setFuncIcon(functionDO.getFuncIcon());
            funcBO1.setFuncPriority(functionDO.getFuncPriority());
            funcBOList.add(funcBO1);
        }
        pagination.addResult(funcBOList);

        return pagination;

    }

    public Map addNewFunc(FuncBO funcBO) {
        Map resultMap = new HashMap();

        TblBTSSysFunctionDO refSysFunctionDO = new TblBTSSysFunctionDO();
        refSysFunctionDO.setFuncId(funcBO.getFuncId());
        refSysFunctionDO.setFuncName(funcBO.getFuncName());
        refSysFunctionDO.setFuncFatherId(funcBO.getFuncFatherId());
        refSysFunctionDO.setFuncDesc(funcBO.getFuncDesc());
        refSysFunctionDO.setFuncDisableTag(funcBO.getFuncDisableTag());
        refSysFunctionDO.setFuncTag(funcBO.getFuncTag());
        refSysFunctionDO.setFuncLevel(funcBO.getFuncLevel());
        refSysFunctionDO.setFuncUrl(funcBO.getFuncUrl());
        refSysFunctionDO.setFuncIcon(funcBO.getFuncIcon());
        refSysFunctionDO.setFuncPriority(funcBO.getFuncPriority());
        int rt = functionDOMapper.insert(refSysFunctionDO);
        if (rt != 0) {
            resultMap.put("statusCode", 200);
            resultMap.put("message", "操作成功!");
        } else {
            resultMap.put("statusCode", 300);
            resultMap.put("message", "操作失败!");
        }
        return resultMap;

    }

    public FuncBO getById(String funcId) {
        TblBTSSysFunctionDOExample tblBTSSysFunctionDOExample = new TblBTSSysFunctionDOExample();
        tblBTSSysFunctionDOExample.createCriteria().andFuncIdEqualTo(funcId);
        List<TblBTSSysFunctionDO> tblBTSSysFunctionDOList = functionDOMapper
            .selectByExample(tblBTSSysFunctionDOExample);
        return convertFuncDOToFuncBO(tblBTSSysFunctionDOList.get(0));
    }

    private FuncBO convertFuncDOToFuncBO(TblBTSSysFunctionDO tblBTSSysFunctionDO) {
        FuncBO funcBO = new FuncBO();
        funcBO.setFuncId(tblBTSSysFunctionDO.getFuncId());
        funcBO.setFuncName(tblBTSSysFunctionDO.getFuncName());
        funcBO.setFuncFatherId(tblBTSSysFunctionDO.getFuncFatherId());
        funcBO.setFuncDesc(tblBTSSysFunctionDO.getFuncDesc());
        funcBO.setFuncRemark(tblBTSSysFunctionDO.getFuncRemark());
        funcBO.setFuncDisableTag(tblBTSSysFunctionDO.getFuncDisableTag());
        funcBO.setFuncTag(tblBTSSysFunctionDO.getFuncTag());
        funcBO.setFuncLevel(tblBTSSysFunctionDO.getFuncLevel());
        funcBO.setFuncUrl(tblBTSSysFunctionDO.getFuncUrl());
        funcBO.setFuncIcon(tblBTSSysFunctionDO.getFuncIcon());
        funcBO.setFuncPriority(tblBTSSysFunctionDO.getFuncPriority());
        return funcBO;
    }

    public Map updateFuncInfo(FuncBO funcBO) {
        Map resultMap = new HashMap();

        TblBTSSysFunctionDOExample functionDOExample = new TblBTSSysFunctionDOExample();
        functionDOExample.createCriteria().andFuncIdEqualTo(funcBO.getFuncId());
        TblBTSSysFunctionDO functionDO = new TblBTSSysFunctionDO();
        functionDO.setFuncFatherId(funcBO.getFuncFatherId());
        functionDO.setFuncDesc(funcBO.getFuncDesc());
        functionDO.setFuncRemark(funcBO.getFuncRemark());
        functionDO.setFuncDisableTag(funcBO.getFuncDisableTag());
        functionDO.setFuncTag(funcBO.getFuncTag());
        functionDO.setFuncLevel(funcBO.getFuncLevel());
        functionDO.setFuncUrl(funcBO.getFuncUrl());
        functionDO.setFuncIcon(funcBO.getFuncIcon());
        functionDO.setFuncPriority(funcBO.getFuncPriority());
        try {
            functionDOMapper.updateByExampleSelective(functionDO, functionDOExample);
            resultMap.put("statusCode", 200);
            resultMap.put("message", "操作成功!");
        } catch (Exception ex) {
            resultMap.put("statusCode", 300);
            resultMap.put("message", "操作失败!");

        }

        return resultMap;
    }

}
