package com.company.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.core.domain.MenuBO;
import com.company.core.entity.TblBTSSysFunctionDO;
import com.company.core.entity.TblBTSSysFunctionDOExample;
import com.company.core.entity.TblBTSSysFunctionVO;
import com.company.core.entity.TblBTSSysRoleVO;
import com.company.core.entity.TblBTSSysUsrVO;
import com.company.core.mapper.TblBTSSysFunctionDOMapper;
import com.company.core.mapper.UserAuthorMapper;
import com.company.core.service.MenuService;

/**
 * Created by APPLE on 16/1/12.
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

    @Autowired
    UserAuthorMapper          userAuthorMapper;

    @Autowired
    TblBTSSysFunctionDOMapper tblBTSSysFunctionDOMapper;

    public List<MenuBO> getAllEnabledMenu() {
        TblBTSSysFunctionDOExample tblBTSSysFunctionDOExample = new TblBTSSysFunctionDOExample();
        tblBTSSysFunctionDOExample.createCriteria().andFuncDisableTagEqualTo("1")
            .andFuncTagEqualTo("0");
        List<TblBTSSysFunctionDO> tblBTSSysFunctionDOList = tblBTSSysFunctionDOMapper
            .selectByExample(tblBTSSysFunctionDOExample);
        List<MenuBO> menuBOList = new ArrayList<MenuBO>();
        for (TblBTSSysFunctionDO tblBTSSysFunctionDO : tblBTSSysFunctionDOList) {
            if (tblBTSSysFunctionDO.getFuncLevel().equals("1")) {
                MenuBO fMenuBO = new MenuBO();
                fMenuBO.setFuncId(tblBTSSysFunctionDO.getFuncId());
                fMenuBO.setFuncDesc(tblBTSSysFunctionDO.getFuncDesc());
                fMenuBO.setFuncFatherId(tblBTSSysFunctionDO.getFuncFatherId());
                fMenuBO.setFuncIcon(tblBTSSysFunctionDO.getFuncIcon());
                fMenuBO.setFuncLevel(tblBTSSysFunctionDO.getFuncLevel());
                fMenuBO.setFuncName(tblBTSSysFunctionDO.getFuncName());
                fMenuBO.setFuncUrl(tblBTSSysFunctionDO.getFuncUrl());
                fMenuBO.setFuncPriority(tblBTSSysFunctionDO.getFuncPriority());
                List<MenuBO> sMenuBOList = new ArrayList<MenuBO>();
                for (TblBTSSysFunctionDO tblBTSSysFunctionDO2 : tblBTSSysFunctionDOList) {
                    if (tblBTSSysFunctionDO2.getFuncLevel().equals("2") && tblBTSSysFunctionDO2
                        .getFuncFatherId().equals(tblBTSSysFunctionDO.getFuncId())) {
                        MenuBO sMenuBO = new MenuBO();
                        sMenuBO.setFuncId(tblBTSSysFunctionDO2.getFuncId());
                        sMenuBO.setFuncDesc(tblBTSSysFunctionDO2.getFuncDesc());
                        sMenuBO.setFuncFatherId(tblBTSSysFunctionDO2.getFuncFatherId());
                        sMenuBO.setFuncIcon(tblBTSSysFunctionDO2.getFuncIcon());
                        sMenuBO.setFuncLevel(tblBTSSysFunctionDO2.getFuncLevel());
                        sMenuBO.setFuncName(tblBTSSysFunctionDO2.getFuncName());
                        sMenuBO.setFuncUrl(tblBTSSysFunctionDO2.getFuncUrl());
                        sMenuBO.setFuncPriority(tblBTSSysFunctionDO2.getFuncPriority());
                        List<MenuBO> tMenuBOList = new ArrayList<MenuBO>();
                        for (TblBTSSysFunctionDO tblBTSSysFunctionDO3 : tblBTSSysFunctionDOList) {
                            if (tblBTSSysFunctionDO3.getFuncLevel().equals("3")
                                && tblBTSSysFunctionDO3.getFuncFatherId()
                                    .equals(tblBTSSysFunctionDO2.getFuncId())) {
                                MenuBO tMenuBO = new MenuBO();
                                tMenuBO.setFuncId(tblBTSSysFunctionDO3.getFuncId());
                                tMenuBO.setFuncDesc(tblBTSSysFunctionDO3.getFuncDesc());
                                tMenuBO.setFuncFatherId(tblBTSSysFunctionDO3.getFuncFatherId());
                                tMenuBO.setFuncIcon(tblBTSSysFunctionDO3.getFuncIcon());
                                tMenuBO.setFuncLevel(tblBTSSysFunctionDO3.getFuncLevel());
                                tMenuBO.setFuncName(tblBTSSysFunctionDO3.getFuncName());
                                tMenuBO.setFuncUrl(tblBTSSysFunctionDO3.getFuncUrl());
                                tMenuBO.setFuncPriority(tblBTSSysFunctionDO3.getFuncPriority());
                                tMenuBOList.add(tMenuBO);
                                Collections.sort(tMenuBOList);
                            }
                        }
                        sMenuBO.setChildMenuBOList(tMenuBOList);
                        sMenuBOList.add(sMenuBO);
                        Collections.sort(sMenuBOList);
                    }
                }
                fMenuBO.setChildMenuBOList(sMenuBOList);
                menuBOList.add(fMenuBO);
            }
        }
        return menuBOList;
    }

    public List<MenuBO> getAllEnabledMenuByUserId(String userid) {
        List<MenuBO> menuBOList = new ArrayList<MenuBO>();
        TblBTSSysUsrVO tblBTSSysUsrVO = userAuthorMapper.getAuthorByUserId(userid);
        if (tblBTSSysUsrVO == null) {
            return menuBOList;
        }
        List<TblBTSSysRoleVO> tblBTSSysRoleVOList = tblBTSSysUsrVO.getTblBTSSysRoleVOList();
        Set<TblBTSSysFunctionVO> tblBTSSysFunctionVOSet = new HashSet<TblBTSSysFunctionVO>();
        for (TblBTSSysRoleVO tblBTSSysRoleVO : tblBTSSysRoleVOList) {
            List<TblBTSSysFunctionVO> tblBTSSysFunctionVOList = tblBTSSysRoleVO
                .getTblBTSSysFunctionVOList();
            for (TblBTSSysFunctionVO tblBTSSysFunctionVO : tblBTSSysFunctionVOList) {
                tblBTSSysFunctionVOSet.add(tblBTSSysFunctionVO);
            }
        }

        TblBTSSysFunctionDOExample tblBTSSysFunctionDOExample = new TblBTSSysFunctionDOExample();
        tblBTSSysFunctionDOExample.createCriteria().andFuncDisableTagEqualTo("1")
            .andFuncTagEqualTo("0");
        List<TblBTSSysFunctionDO> tblBTSSysFunctionDOList = tblBTSSysFunctionDOMapper
            .selectByExample(tblBTSSysFunctionDOExample);
        for (TblBTSSysFunctionVO tblBTSSysFunctionVO : tblBTSSysFunctionVOSet) {
            if (!tblBTSSysFunctionVO.getFuncDisableTag().equals("1")
                || !tblBTSSysFunctionVO.getFuncTag().equals("0")) {
                continue;
            }
            if (tblBTSSysFunctionVO.getFuncLevel().equals("1")) {
                MenuBO fMenuBO = new MenuBO();
                fMenuBO.setFuncId(tblBTSSysFunctionVO.getFuncId());
                fMenuBO.setFuncDesc(tblBTSSysFunctionVO.getFuncDesc());
                fMenuBO.setFuncFatherId(tblBTSSysFunctionVO.getFuncFatherId());
                fMenuBO.setFuncIcon(tblBTSSysFunctionVO.getFuncIcon());
                fMenuBO.setFuncLevel(tblBTSSysFunctionVO.getFuncLevel());
                fMenuBO.setFuncName(tblBTSSysFunctionVO.getFuncName());
                fMenuBO.setFuncUrl(tblBTSSysFunctionVO.getFuncUrl());
                fMenuBO.setFuncPriority(tblBTSSysFunctionVO.getFuncPriority());
                List<MenuBO> sMenuBOList = new ArrayList<MenuBO>();
                for (TblBTSSysFunctionVO tblBTSSysFunctionVO2 : tblBTSSysFunctionVOSet) {
                    if (!tblBTSSysFunctionVO2.getFuncDisableTag().equals("1")
                        || !tblBTSSysFunctionVO2.getFuncTag().equals("0")) {
                        continue;
                    }
                    if (tblBTSSysFunctionVO2.getFuncLevel().equals("2") && tblBTSSysFunctionVO2
                        .getFuncFatherId().equals(tblBTSSysFunctionVO.getFuncId())) {
                        MenuBO sMenuBO = new MenuBO();
                        sMenuBO.setFuncId(tblBTSSysFunctionVO2.getFuncId());
                        sMenuBO.setFuncDesc(tblBTSSysFunctionVO2.getFuncDesc());
                        sMenuBO.setFuncFatherId(tblBTSSysFunctionVO2.getFuncFatherId());
                        sMenuBO.setFuncIcon(tblBTSSysFunctionVO2.getFuncIcon());
                        sMenuBO.setFuncLevel(tblBTSSysFunctionVO2.getFuncLevel());
                        sMenuBO.setFuncName(tblBTSSysFunctionVO2.getFuncName());
                        sMenuBO.setFuncUrl(tblBTSSysFunctionVO2.getFuncUrl());
                        sMenuBO.setFuncPriority(tblBTSSysFunctionVO2.getFuncPriority());
                        List<MenuBO> tMenuBOList = new ArrayList<MenuBO>();
                        for (TblBTSSysFunctionVO tblBTSSysFunctionVO3 : tblBTSSysFunctionVOSet) {
                            if (!tblBTSSysFunctionVO3.getFuncDisableTag().equals("1")
                                || !tblBTSSysFunctionVO3.getFuncTag().equals("0")) {
                                continue;
                            }
                            if (tblBTSSysFunctionVO3.getFuncLevel().equals("3")
                                && tblBTSSysFunctionVO3.getFuncFatherId()
                                    .equals(tblBTSSysFunctionVO2.getFuncId())) {
                                MenuBO tMenuBO = new MenuBO();
                                tMenuBO.setFuncId(tblBTSSysFunctionVO3.getFuncId());
                                tMenuBO.setFuncDesc(tblBTSSysFunctionVO3.getFuncDesc());
                                tMenuBO.setFuncFatherId(tblBTSSysFunctionVO3.getFuncFatherId());
                                tMenuBO.setFuncIcon(tblBTSSysFunctionVO3.getFuncIcon());
                                tMenuBO.setFuncLevel(tblBTSSysFunctionVO3.getFuncLevel());
                                tMenuBO.setFuncName(tblBTSSysFunctionVO3.getFuncName());
                                tMenuBO.setFuncUrl(tblBTSSysFunctionVO3.getFuncUrl());
                                tMenuBO.setFuncPriority(tblBTSSysFunctionVO3.getFuncPriority());
                                tMenuBOList.add(tMenuBO);
                                Collections.sort(tMenuBOList);
                            }
                        }
                        sMenuBO.setChildMenuBOList(tMenuBOList);
                        sMenuBOList.add(sMenuBO);
                        Collections.sort(sMenuBOList);
                    }
                }
                fMenuBO.setChildMenuBOList(sMenuBOList);
                menuBOList.add(fMenuBO);
            }
        }
        return menuBOList;
    }
}
