package com.company.core.service.impl;

import com.company.core.biz.*;
import com.company.core.constant.*;
import com.company.core.domain.AgentBO;
import com.company.core.domain.UserBO;
import com.company.core.entity.*;
import com.company.core.form.AgentForm;
import com.company.core.form.Pagination;
import com.company.core.service.AgentService;
import com.company.core.service.UserService;
import com.company.core.util.DateUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service("AgentService")
public class AgentServiceImpl implements AgentService {
    
    @Autowired
    UCAgentBiz ucAgentBiz;
    @Autowired
    SequenceBiz sequenceBiz;
    @Autowired
    UCAgentLevelBiz ucAgentLevelBiz;
    @Autowired
    UCFeeBiz ucFeeBiz;
    @Autowired
    UCInstBiz ucInstBiz;
    @Autowired
    CustomCredentialsMatcherBiz customCredentialsMatcherBiz;
    @Autowired
    UserService userService;
    @Autowired
    TblBtsUserMapBiz tblBtsUserMapBiz;
    
    @Override
    public List<UcAgentDo> getAgentList() {
        
        return ucAgentBiz.selectAllAgents();
        
    }
    
    @Override
    public List<UcAgentDo> getAgentList(String instId) {
        
        return ucAgentBiz.selectAgentsByInstId(instId);
        
    }
    
    @Override
    public List<UcAgentDo> getAgentList(String instId, String agentType, String status) {
        
        return ucAgentBiz.selectAllAgents(instId, agentType, status, "");
        
    }
    
    @Override
    public List<String> getAgentIdList(String instId, String status) {
        
        List<String> agentIdList = new ArrayList<>();
        List<UcAgentDo> ucAgentDos = ucAgentBiz.selectAgentsByInstId(instId);
        if (ucAgentDos != null && ucAgentDos.size() > 0) {
            for (UcAgentDo uc : ucAgentDos) {
                if (StringUtils.isNotBlank(status)) {
                    if (status.equals(uc.getStatus())) {
                        agentIdList.add(uc.getAgentId());
                    } else {
                        continue;
                    }
                } else {
                    agentIdList.add(uc.getAgentId());
                }
            }
        }
        return agentIdList;
    }
    
    @Override
    public List<UcAgentLevelDo> getAgentListOfAgentOwn(String agent) {
        
        return ucAgentLevelBiz.getAllDownAgentLevelList(agent);
        
    }
    
    ;
    
    
    @Override
    public List<String> getAgentIdListOfAgentOwn(String agentId) {
        
        List<String> agentIdList = new ArrayList<>();
        List<UcAgentLevelDo> list = ucAgentLevelBiz.getAllDownAgentLevelList(agentId);
        if (list != null && list.size() > 0) {
            for (UcAgentLevelDo s : list) {
                agentIdList.add(s.getAgentId());
            }
        }
        return agentIdList;
    }
    
    @Override
    public List<UcAgentDo> getAgentIdListOfAgentOwnEnabled(String agentId) {
        
        List<UcAgentDo> agentIdList = new ArrayList<>();
        UcAgentDo ucAgentDo = null;
        List<UcAgentLevelDo> list = ucAgentLevelBiz.getAllDownAgentLevelList(agentId);
        if (list != null && list.size() > 0) {
            for (UcAgentLevelDo s : list) {
                ucAgentDo = ucAgentBiz.selectAgent(agentId);
                if (StatusConstant.STATUS_ENABLE.equals(ucAgentDo.getStatus())) {
                    agentIdList.add(ucAgentDo);
                }
            }
        }
        return agentIdList;
    }
    
    
    @Override
    public Map<String, String> checkAgentBefore(AgentForm agentForm, UserBO userBO) {
        
        Map<String, String> result = new HashMap<>();
        
        //检查机构信息
        UcInstDo ucInstDo = ucInstBiz.selectByPrimaryKey(agentForm.getInstId());
        if (ucInstDo == null) {
            result.put("error", "机构不存在");
        }
        if (!StatusConstant.STATUS_ENABLE.equals(ucInstDo.getStatus())) {
            result.put("error", "机构非激活状态不允许新增代理");
        }
        if (!"Y".equals(ucInstDo.getAgentOk())) {
            result.put("error", "机构不允许新增代理");
        }
        
        //检查费率信息 - 代理的默认费率不能低于机构
        UcFeeDo ucFeeDo = new UcFeeDo();
        ucFeeDo.setUserType(UserConstant.USER_INST);
        ucFeeDo.setUserCode(agentForm.getInstId());
        ucFeeDo.setCategory(agentForm.getCategory());
        ucFeeDo.setCategoryId(agentForm.getCategoryId());
        ucFeeDo.setFeeType("DF");
        UcFeeDo df = ucFeeBiz.getFee(ucFeeDo);
        if (df != null && !ucFeeBiz.zeroFee(df.getFeeMode())) {
            //非空, 非0机构默认固定费用
            int i = ucFeeBiz.compareFees(agentForm.getDefaultFeeFixed(), df.getFeeMode());
            if (i < 0) {
                result.put("error", "代理默认固定费用低于机构");
            }
        }
        ucFeeDo.setFeeType("DR");
        UcFeeDo dr = ucFeeBiz.getFee(ucFeeDo);
        if (dr != null && !ucFeeBiz.zeroFee(dr.getFeeMode())) {
            //非空, 非0机构默认费率
            int i = ucFeeBiz.compareFees(agentForm.getDefaultFeeRate(), dr.getFeeMode());
            if (i < 0) {
                result.put("error", "代理默认费率低于机构");
            }
        }
        
        //代理的实收费率不能低于默认费率
        int a = ucFeeBiz.compareFees(agentForm.getEffectiveFeeFixed(), agentForm.getDefaultFeeFixed());
        if (a < 0) {
            result.put("error", "代理实收固定费用低于默认固定费用");
        }
        int b = ucFeeBiz.compareFees(agentForm.getEffectiveFeeRate(), agentForm.getDefaultFeeRate());
        if (b < 0) {
            result.put("error", "代理实收费率低于默认费率");
        }
        
        return result;
    }
    
    
    @Override
    public String createNewAgent(AgentForm agentForm, UserBO userBO) {
        
        //创建代理基础信息
        this.createAgentBaseInfo(agentForm, userBO);
        
        //创建代理详细信息
        this.createAgentDetailInfo(agentForm, userBO);
        
        //创建代理费率
        this.createAgentFeeInfo(agentForm, userBO);
        
        return agentForm.getAgentId();
        
    }
    
    @Override
    public void createAgentBaseInfo(AgentForm agentForm, UserBO userBO) {
        
        //创建代理
        UcAgentDo ucAgentDo = new UcAgentDo();
        ucAgentDo.setInstId(agentForm.getInstId());
        ucAgentDo.setAgentId(sequenceBiz.genAgentId());
        
        //机构发展的代理商类型为1,  机构自身的代理商号是0,
        if (UserConstant.USER_TYPE_DEFAULT.equals(agentForm.getAgentType()) || UserConstant.USER_TYPE_AGENT.equals(agentForm.getAgentType())) {
            //机构默认代理, 直接激活
            ucAgentDo.setAgentType(UserConstant.USER_TYPE_DEFAULT);
            ucAgentDo.setStatus(StatusConstant.STATUS_ENABLE);
        } else if (UserConstant.USER_TYPE_AGENT.equals(agentForm.getAgentType())) {
            ucAgentDo.setAgentType(UserConstant.USER_TYPE_AGENT);
            ucAgentDo.setStatus(StatusConstant.STATUS_NEW);
        }
        ucAgentDo.setAgentName(agentForm.getAgentName());
        ucAgentDo.setCategory(agentForm.getCategory());
        ucAgentDo.setCategoryId(agentForm.getCategoryId());
        ucAgentDo.setAgentOk(agentForm.getAgentOk());
        BigDecimal bigDecimal = new BigDecimal(agentForm.getAgentCountLimit());
        ucAgentDo.setAgentCountLimit(bigDecimal);
        ucAgentDo.setLimitArea(agentForm.getLimitArea());
        ucAgentDo.setLimitAreaCode(agentForm.getLimitAreaCode());
        
        ucAgentDo.setCreateUser(userBO.getUsrName());
        ucAgentDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucAgentDo.setCreateTime(DateUtil.getCurrentDateTime());
        
        ucAgentDo.setLockVersion(String.valueOf(0));
        ucAgentDo.setModifyUser(userBO.getUsrName());
        ucAgentDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucAgentDo.setModifyTime(DateUtil.getCurrentDateTime());
        
        int i = ucAgentBiz.insertAgent(ucAgentDo);
        if (i <= 0) {
            throw new ErrorException("代理信息入库失败");
        }
        
        agentForm.setAgentId(ucAgentDo.getAgentId());
        
    }
    
    @Override
    public void createAgentDetailInfo(AgentForm agentForm, UserBO userBO) {
        
        //存入代理详细信息
        UcAgentInfoDo ucAgentInfoDo = new UcAgentInfoDo();
        ucAgentInfoDo.setAgentId(agentForm.getAgentId());
        ucAgentInfoDo.setAgentName(agentForm.getAgentName());
        ucAgentInfoDo.setAgentShortName(agentForm.getAgentShortName());
        ucAgentInfoDo.setBusinessLicense(agentForm.getBusinessLicense());
        ucAgentInfoDo.setLegalPersonName(agentForm.getLegalPersonName());
        ucAgentInfoDo.setLegalPersonIdType(agentForm.getLegalPersonIdType());
        ucAgentInfoDo.setLegalPersonCertId(agentForm.getLegalPersonId());
        ucAgentInfoDo.setLegalPersonPhone(agentForm.getLegalPersonPhone());
        ucAgentInfoDo.setLegalPersonMail(agentForm.getLegalPersonMail());
        ucAgentInfoDo.setLegalPersonAddress(agentForm.getLegalPersonAddress());
        ucAgentInfoDo.setContactName(agentForm.getContactName());
        ucAgentInfoDo.setContactIdType(agentForm.getContactIdType());
        ucAgentInfoDo.setContactCertId(agentForm.getContactCertId());
        ucAgentInfoDo.setContactPhone(agentForm.getContactPhone());
        ucAgentInfoDo.setContactMail(agentForm.getContactMail());
        ucAgentInfoDo.setContactAddress(agentForm.getContactAddress());
        
        ucAgentInfoDo.setCreateUser(userBO.getUsrName());
        ucAgentInfoDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucAgentInfoDo.setCreateTime(DateUtil.getCurrentDateTime());
        ucAgentInfoDo.setModifyUser(userBO.getUsrName());
        ucAgentInfoDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucAgentInfoDo.setModifyTime(DateUtil.getCurrentDateTime());
        ucAgentInfoDo.setLockVersion(String.valueOf(0));
        
        int c = ucAgentBiz.insertAgentInfo(ucAgentInfoDo);
        if (c <= 0) {
            throw new ErrorException("代理详细信息入库失败");
        }
    }
    
    @Override
    public void createAgentFeeInfo(AgentForm agentForm, UserBO userBO) {
        
        //存入机构费率
        UcFeeDo ucFeeDo = new UcFeeDo();
        ucFeeDo.setUserType(UserConstant.USER_AGENT);
        ucFeeDo.setUserCode(agentForm.getInstId());
        
        if (StringUtils.isNotBlank(agentForm.getCategory())) {
            ucFeeDo.setCategory(agentForm.getCategory());
        } else {
            ucFeeDo.setCategory(SystemConstant.DEFAULT_CATEGORY);  //默认是1
        }
        if (StringUtils.isNotBlank(agentForm.getCategoryId())) {
            ucFeeDo.setCategoryId(agentForm.getCategoryId());
        } else {
            ucFeeDo.setCategoryId(SystemConstant.DEFAULT_CATEGORY_ID);  //默认是P001
        }
        ucFeeDo.setStatus(StatusConstant.STATUS_ENABLE);
        ucFeeDo.setPercentFlag("N");
        
        ucFeeDo.setCreateUser(userBO.getUsrName());
        ucFeeDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucFeeDo.setCreateTime(DateUtil.getCurrentDateTime());
        ucFeeDo.setModifyUser(userBO.getUsrName());
        ucFeeDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucFeeDo.setModifyTime(DateUtil.getCurrentDateTime());
        ucFeeDo.setLockedVersion(String.valueOf(0));
        
        //默认固定单笔
        if (StringUtils.isNotBlank(agentForm.getDefaultFeeFixed()) && !ucFeeBiz.zeroFee(agentForm.getDefaultFeeFixed())) {
            ucFeeDo.setFeeType(Constant.FEE_DEFAULT_FIXED);
            ucFeeDo.setFeeDesc(Constant.FEE_DEFAULT_FIXED_DESC);
            ucFeeDo.setFeeMode(agentForm.getDefaultFeeFixed());
            int i = ucFeeBiz.insertFee(ucFeeDo);
            if (i <= 0) {
                throw new ErrorException("数据新增失败");
            }
        }
        //默认固定比例
        if (StringUtils.isNotBlank(agentForm.getDefaultFeeRate()) && !ucFeeBiz.zeroFee(agentForm.getDefaultFeeRate())) {
            ucFeeDo.setFeeType(Constant.FEE_DEFAULT_RATE);
            ucFeeDo.setFeeDesc(Constant.FEE_DEFAULT_RATE_DESC);
            ucFeeDo.setFeeMode(agentForm.getDefaultFeeRate());
            int c = ucFeeBiz.insertFee(ucFeeDo);
            if (c <= 0) {
                throw new ErrorException("数据新增失败");
            }
        }
        //实收固定单笔
        if (StringUtils.isNotBlank(agentForm.getEffectiveFeeFixed()) && !ucFeeBiz.zeroFee(agentForm.getEffectiveFeeFixed())) {
            ucFeeDo.setFeeType(Constant.FEE_EFFECTIVE_FIXED);
            ucFeeDo.setFeeDesc(Constant.FEE_EFFECTIVE_FIXED_DESC);
            ucFeeDo.setFeeMode(agentForm.getEffectiveFeeFixed());
            int d = ucFeeBiz.insertFee(ucFeeDo);
            if (d <= 0) {
                throw new ErrorException("数据新增失败");
            }
        }
        //实收固定比例
        if (StringUtils.isNotBlank(agentForm.getEffectiveFeeRate()) && !ucFeeBiz.zeroFee(agentForm.getEffectiveFeeRate())) {
            ucFeeDo.setFeeType(Constant.FEE_EFFECTIVE_RATE);
            ucFeeDo.setFeeDesc(Constant.FEE_EFFECTIVE_RATE_DESC);
            ucFeeDo.setFeeMode(agentForm.getEffectiveFeeRate());
            int f = ucFeeBiz.insertFee(ucFeeDo);
            if (f <= 0) {
                throw new ErrorException("数据新增失败");
            }
        }
    }
    
    
    @Override
    public void formatAgentFormFromAgent(AgentForm agentForm) {
        
        UcAgentDo ucAgentDo = getAgent(agentForm.getAgentId());
        if (ucAgentDo == null) {
            return;
        }
        agentForm.setInstId(ucAgentDo.getInstId());
        agentForm.setAgentName(ucAgentDo.getAgentName());
        agentForm.setAgentType(ucAgentDo.getAgentType());
        agentForm.setStatus(ucAgentDo.getStatus());
        agentForm.setCategory(ucAgentDo.getCategory());
        agentForm.setCategoryId(ucAgentDo.getCategoryId());
        agentForm.setAgentOk(ucAgentDo.getAgentOk());
        agentForm.setAgentCountLimit(String.valueOf(ucAgentDo.getAgentCountLimit()));
        agentForm.setLimitArea(ucAgentDo.getLimitArea());
        agentForm.setLimitAreaCode(ucAgentDo.getLimitAreaCode());
    }
    
    @Override
    public void formatAgentFormFromAgentInfo(AgentForm agentForm) {
        
        UcAgentInfoDo ucAgentInfoDo = getAgentInfo(agentForm.getInstId());
        if (ucAgentInfoDo == null) {
            return;
        }
        agentForm.setAgentShortName(ucAgentInfoDo.getAgentShortName());
        agentForm.setBusinessLicense(ucAgentInfoDo.getBusinessLicense());
        agentForm.setLegalPersonName(ucAgentInfoDo.getLegalPersonName());
        agentForm.setLegalPersonIdType(ucAgentInfoDo.getLegalPersonIdType());
        agentForm.setLegalPersonId(ucAgentInfoDo.getLegalPersonCertId());
        agentForm.setLegalPersonAddress(ucAgentInfoDo.getLegalPersonAddress());
        agentForm.setLegalPersonPhone(ucAgentInfoDo.getLegalPersonPhone());
        agentForm.setLegalPersonAddress(ucAgentInfoDo.getLegalPersonAddress());
        agentForm.setLegalPersonMail(ucAgentInfoDo.getLegalPersonMail());
        
        agentForm.setContactName(ucAgentInfoDo.getContactName());
        agentForm.setContactCertId(ucAgentInfoDo.getContactCertId());
        agentForm.setContactIdType(ucAgentInfoDo.getContactIdType());
        agentForm.setContactPhone(ucAgentInfoDo.getContactPhone());
        agentForm.setContactMail(ucAgentInfoDo.getContactMail());
        agentForm.setContactAddress(ucAgentInfoDo.getContactAddress());
        
    }
    
    @Override
    public void formatAgentFormFromFee(AgentForm agentForm) {
        
        UcFeeDo ucFeeDo = new UcFeeDo();
        ucFeeDo.setUserType(UserConstant.USER_AGENT);
        ucFeeDo.setUserCode(agentForm.getAgentId());
        
        //之后扩充时, 需要替换掉
        ucFeeDo.setCategory("1");
        ucFeeDo.setCategoryId("P001");
        ucFeeDo.setFeeType("DF");
        UcFeeDo df = ucFeeBiz.getFee(ucFeeDo);
        if (df != null) {
            agentForm.setDefaultFeeFixed(df.getFeeMode());
        }
        
        ucFeeDo.setFeeType("DR");
        UcFeeDo dr = ucFeeBiz.getFee(ucFeeDo);
        if (dr != null) {
            agentForm.setDefaultFeeRate(dr.getFeeMode());
        }
        
        ucFeeDo.setFeeType("EF");
        UcFeeDo ef = ucFeeBiz.getFee(ucFeeDo);
        if (ef != null) {
            agentForm.setEffectiveFeeFixed(ef.getFeeMode());
        }
        
        ucFeeDo.setFeeType("ER");
        UcFeeDo er = ucFeeBiz.getFee(ucFeeDo);
        if (er != null) {
            agentForm.setEffectiveFeeRate(er.getFeeMode());
        }
        
    }
    
    @Override
    public UcAgentDo getAgent(String agentId) {
        return ucAgentBiz.selectAgent(agentId);
    }
    
    
    @Override
    public UcAgentInfoDo getAgentInfo(String agentId) {
        return ucAgentBiz.selectAgentInfo(agentId);
    }
    
    @Override
    public UcAgentLevelDo getAgentLevel(String agentId) {
        
        return ucAgentLevelBiz.getAgentLevel(agentId);
        
    }
    
    @Override
    public String getAgentLevelName(String agentId) {
        
        String levelName = "";
        UcAgentLevelDo ucAgentLevelDo = ucAgentLevelBiz.getAgentLevel(agentId);
        if (ucAgentLevelDo == null) {
            return "1级: ";
        } else {
            return ucAgentLevelDo.getAgentLevel() + "级: ";
        }
    }
    
    @Override
    public Boolean checkIfDupAgentByName(String agentName, String agentShortName) {
        
        if (StringUtils.isNotBlank(agentName)) {
            List<UcAgentInfoDo> ucAgentInfoDos = ucAgentBiz.selectByName(agentName);
            if (ucAgentInfoDos != null && ucAgentInfoDos.size() > 0) {
                return true;
            }
        }
        
        if (StringUtils.isNotBlank(agentShortName)) {
            List<UcAgentInfoDo> ucAgentInfoDos = ucAgentBiz.selectByShortName(agentName);
            if (ucAgentInfoDos != null && ucAgentInfoDos.size() > 0) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public UcAgentDo getAgentOfInstOwn(String instId) {
        
        List<UcAgentDo> ucAgentDos = ucAgentBiz.selectAgentsByInstId(instId);
        if (ucAgentDos == null || ucAgentDos.size() == 0) {
            return null;
        }
        return ucAgentDos.get(0);
    }
    
    @Override
    public UcAgentDo getDefautlAgentOfInst(String instId) {
        
        UcAgentDo ucAgentDos = ucAgentBiz.selectDefaultAgent(instId);
        if (ucAgentDos != null) {
            return ucAgentDos;
        }
        return null;
    }
    
    @Override
    public Pagination getAgentListPage(AgentForm agentForm) {
        
        int pageCurrent = Integer.parseInt(agentForm.getPageCurrent());
        int pageSize = Integer.parseInt(agentForm.getPageSize());
        UcAgentDoExample ucagentDoExample = formatAgentSearchCriteria(agentForm);
        
        //获取满足的记录条数
        int tranSize = ucAgentBiz.countByExample(ucagentDoExample);
        Pagination<AgentBO> page = new Pagination<AgentBO>(tranSize, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
        List<UcAgentDo> ucagentDos = ucAgentBiz.selectByExample(ucagentDoExample);
        List<AgentBO> list = new ArrayList<>();
        AgentBO agentBO = null;
        if (ucagentDos != null && ucagentDos.size() > 0) {
            for (UcAgentDo ur : ucagentDos) {
                agentBO = new AgentBO();
                agentBO.setInstId(ur.getInstId());
                agentBO.setAgentId(ur.getAgentId());
                agentBO.setAgentName(ur.getAgentName());
                agentBO.setStatus(ur.getStatus());
                agentBO.setCreateUser(ur.getCreateUser());
                agentBO.setCreateTime(ur.getCreateTime());
                list.add(agentBO);
            }
        }
        page.addResult(list);
        return page;
    }
    
    public UcAgentDoExample formatAgentSearchCriteria(AgentForm agentForm) {
        
        UcAgentDoExample ucAgentDoExample = new UcAgentDoExample();
        UcAgentDoExample.Criteria criteria = ucAgentDoExample.createCriteria();
        
        if (StringUtils.isNotBlank(agentForm.getInstId())) {
            criteria.andInstIdEqualTo(agentForm.getInstId());
        }
        if (StringUtils.isNotBlank(agentForm.getAgentId())) {
            criteria.andAgentIdEqualTo(agentForm.getAgentId());
        }
        if (StringUtils.isNotBlank(agentForm.getAgentName())) {
            criteria.andAgentNameLike("%" + agentForm.getAgentName() + "%");
        }
        if (StringUtils.isNotBlank(agentForm.getStatus())) {
            criteria.andStatusEqualTo(agentForm.getStatus());
        }
        return ucAgentDoExample;
    }
    
    
    @Override
    public void updateAgent(AgentForm agentForm, UserBO userBO) {
        
        //更新代理基础信息
        this.updateAgentBaseInfo(agentForm, userBO);
        
        //更新代理详细信息
        this.updateAgentDetailInfo(agentForm, userBO);
        
        //更新代理费率信息
        this.updateAgentFeeInfo(agentForm, userBO);
        
    }
    
    @Override
    public void updateAgentBaseInfo(AgentForm agentForm, UserBO userBO) {
        
        //代理信息
        UcAgentDo ucAgentDo = new UcAgentDo();
        ucAgentDo.setInstId(agentForm.getInstId());
        ucAgentDo.setAgentId(agentForm.getAgentId());
        //代理类型不允许修改
        //代理状态不允许修改

//        //机构发展的代理商类型为1,  机构自身的代理商号是0,
//        if(UserConstant.USER_TYPE_DEFAULT.equals(agentForm.getAgentType()) || UserConstant.USER_TYPE_AGENT.equals(agentForm.getAgentType())){
//            //机构默认代理, 直接激活
//            ucAgentDo.setAgentType(UserConstant.USER_TYPE_DEFAULT);
//            ucAgentDo.setStatus(StatusConstant.STATUS_ENABLE);
//        }else if(UserConstant.USER_TYPE_AGENT.equals(agentForm.getAgentType())) {
//            ucAgentDo.setAgentType(UserConstant.USER_TYPE_AGENT);
//            ucAgentDo.setStatus(StatusConstant.STATUS_NEW);
//        }
        
        ucAgentDo.setAgentName(agentForm.getAgentName());
        ucAgentDo.setCategory(agentForm.getCategory());
        ucAgentDo.setCategoryId(agentForm.getCategoryId());
        ucAgentDo.setAgentOk(agentForm.getAgentOk());
        BigDecimal bigDecimal = new BigDecimal(agentForm.getAgentCountLimit());
        ucAgentDo.setAgentCountLimit(bigDecimal);
        ucAgentDo.setLimitArea(agentForm.getLimitArea());
        ucAgentDo.setLimitAreaCode(agentForm.getLimitAreaCode());

//        ucAgentDo.setCreateUser(userBO.getUsrName());
//        ucAgentDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE);
//        ucAgentDo.setCreateTime(DateUtil.getCurrentDateTime());
//        ucAgentDo.setLockVersion(String.valueOf(0));
        ucAgentDo.setModifyUser(userBO.getUsrName());
        ucAgentDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucAgentDo.setModifyTime(DateUtil.getCurrentDateTime());
        
        int i = ucAgentBiz.updateAgent(ucAgentDo);
        if (i <= 0) {
            throw new ErrorException("代理信息更新失败");
        }
    }
    
    @Override
    public void updateAgentDetailInfo(AgentForm agentForm, UserBO userBO) {
        
        //更新代理详细信息
        UcAgentInfoDo ucAgentInfoDo = new UcAgentInfoDo();
        ucAgentInfoDo.setAgentId(agentForm.getAgentId());
        ucAgentInfoDo.setAgentName(agentForm.getAgentName());
        ucAgentInfoDo.setAgentShortName(agentForm.getAgentShortName());
        ucAgentInfoDo.setBusinessLicense(agentForm.getBusinessLicense());
        ucAgentInfoDo.setLegalPersonName(agentForm.getLegalPersonName());
        ucAgentInfoDo.setLegalPersonIdType(agentForm.getLegalPersonIdType());
        ucAgentInfoDo.setLegalPersonCertId(agentForm.getLegalPersonId());
        ucAgentInfoDo.setLegalPersonPhone(agentForm.getLegalPersonPhone());
        ucAgentInfoDo.setLegalPersonMail(agentForm.getLegalPersonMail());
        ucAgentInfoDo.setLegalPersonAddress(agentForm.getLegalPersonAddress());
        ucAgentInfoDo.setContactName(agentForm.getContactName());
        ucAgentInfoDo.setContactIdType(agentForm.getContactIdType());
        ucAgentInfoDo.setContactCertId(agentForm.getContactCertId());
        ucAgentInfoDo.setContactPhone(agentForm.getContactPhone());
        ucAgentInfoDo.setContactMail(agentForm.getContactMail());
        ucAgentInfoDo.setContactAddress(agentForm.getContactAddress());

//        ucAgentInfoDo.setCreateUser(userBO.getUsrName());
//        ucAgentInfoDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE);
//        ucAgentInfoDo.setCreateTime(DateUtil.getCurrentDateTime());
        ucAgentInfoDo.setModifyUser(userBO.getUsrName());
        ucAgentInfoDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucAgentInfoDo.setModifyTime(DateUtil.getCurrentDateTime());
//        ucAgentInfoDo.setLockVersion(String.valueOf(0));
        
        int c = ucAgentBiz.updateAgentInfo(ucAgentInfoDo);
        if (c <= 0) {
            throw new ErrorException("代理详细信息更新失败");
        }
    }
    
    @Override
    public void updateAgentFeeInfo(AgentForm agentForm, UserBO userBO) {
        
        //存入机构费率
        UcFeeDo ucFeeDo = new UcFeeDo();
        ucFeeDo.setUserType(UserConstant.USER_AGENT);
        ucFeeDo.setUserCode(agentForm.getInstId());

//        if(StringUtils.isNotBlank(agentForm.getCategory())){
//            ucFeeDo.setCategory(agentForm.getCategory());
//        } else {
//            ucFeeDo.setCategory(SystemConstant.DEFAULT_CATEGORY);  //默认是1
//        }
//        if(StringUtils.isNotBlank(agentForm.getCategoryId())){
//            ucFeeDo.setCategoryId(agentForm.getCategoryId());
//        } else {
//            ucFeeDo.setCategoryId(SystemConstant.DEFAULT_CATEGORY_ID);  //默认是P001
//        }
//        ucFeeDo.setStatus(StatusConstant.STATUS_ENABLE);
//        ucFeeDo.setPercentFlag("N");

//        ucFeeDo.setCreateUser(userBO.getUsrName());
//        ucFeeDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE);
//        ucFeeDo.setCreateTime(DateUtil.getCurrentDateTime());
        ucFeeDo.setModifyUser(userBO.getUsrName());
        ucFeeDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucFeeDo.setModifyTime(DateUtil.getCurrentDateTime());
//        ucFeeDo.setLockedVersion(String.valueOf(0));
        
        //默认固定单笔
        if (StringUtils.isNotBlank(agentForm.getDefaultFeeFixed()) && !ucFeeBiz.zeroFee(agentForm.getDefaultFeeFixed())) {
            ucFeeDo.setFeeType(Constant.FEE_DEFAULT_FIXED);
            ucFeeDo.setFeeDesc(Constant.FEE_DEFAULT_FIXED_DESC);
            ucFeeDo.setFeeMode(agentForm.getDefaultFeeFixed());
            int i = ucFeeBiz.updateFee(ucFeeDo);
            if (i <= 0) {
                throw new ErrorException("数据更新失败");
            }
        }
        //默认固定比例
        if (StringUtils.isNotBlank(agentForm.getDefaultFeeRate()) && !ucFeeBiz.zeroFee(agentForm.getDefaultFeeRate())) {
            ucFeeDo.setFeeType(Constant.FEE_DEFAULT_RATE);
            ucFeeDo.setFeeDesc(Constant.FEE_DEFAULT_RATE_DESC);
            ucFeeDo.setFeeMode(agentForm.getDefaultFeeRate());
            int c = ucFeeBiz.updateFee(ucFeeDo);
            if (c <= 0) {
                throw new ErrorException("数据更新失败");
            }
        }
        //实收固定单笔
        if (StringUtils.isNotBlank(agentForm.getEffectiveFeeFixed()) && !ucFeeBiz.zeroFee(agentForm.getEffectiveFeeFixed())) {
            ucFeeDo.setFeeType(Constant.FEE_EFFECTIVE_FIXED);
            ucFeeDo.setFeeDesc(Constant.FEE_EFFECTIVE_FIXED_DESC);
            ucFeeDo.setFeeMode(agentForm.getEffectiveFeeFixed());
            int d = ucFeeBiz.updateFee(ucFeeDo);
            if (d <= 0) {
                throw new ErrorException("数据更新失败");
            }
        }
        //实收固定比例
        if (StringUtils.isNotBlank(agentForm.getEffectiveFeeRate()) && !ucFeeBiz.zeroFee(agentForm.getEffectiveFeeRate())) {
            ucFeeDo.setFeeType(Constant.FEE_EFFECTIVE_RATE);
            ucFeeDo.setFeeDesc(Constant.FEE_EFFECTIVE_RATE_DESC);
            ucFeeDo.setFeeMode(agentForm.getEffectiveFeeRate());
            int f = ucFeeBiz.updateFee(ucFeeDo);
            if (f <= 0) {
                throw new ErrorException("数据更新失败");
            }
        }
    }
    
    
    @Override
    @Transactional
    public void activateAgent(String agentId, UserBO userBO) throws Exception {
        UcAgentDo ucAgentDo = null;
        
        try {
            //激活
            ucAgentDo = ucAgentBiz.selectAgent(agentId);
            ucAgentDo.setStatus(StatusConstant.STATUS_ENABLE);
            
            ucAgentDo.setModifyUser(userBO.getUsrName());
            ucAgentDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
            ucAgentDo.setModifyTime(DateUtil.getCurrentDateTime());
            
            int a = ucAgentBiz.updateAgent(ucAgentDo);
            if (a <= 0) {
                throw new ErrorException("代理激活失败");
            }
            
            //查看代理默认登录用户
            List<String> idList = tblBtsUserMapBiz.getLoginList(UserConstant.USER_AGENT, agentId);
            if (idList != null && idList.size() > 0) {
                //已经创建 - 就去激活
                if (idList != null && idList.size() > 0) {
                    for (String i : idList) {
                        userService.setAcctEnable(i);
                    }
                }
            } else {
                //未创建
                this.createAgentAcct(ucAgentDo, userBO);
            }
            
        } catch (ErrorException e) {
            e.printStackTrace();
            throw new ErrorException("代理激活异常");
        }
        
        
    }
    
    @Override
    @Transactional
    public void cancelAgent(String agentId, UserBO userBO) throws Exception {
        
        UcAgentDo ucAgentDo = null;
        
        //激活
        ucAgentDo = ucAgentBiz.selectAgent(agentId);
        ucAgentDo.setStatus(StatusConstant.STATUS_CANNEL);
        
        ucAgentDo.setModifyUser(userBO.getUsrName());
        ucAgentDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucAgentDo.setModifyTime(DateUtil.getCurrentDateTime());
        
        int a = ucAgentBiz.updateAgent(ucAgentDo);
        if (a <= 0) {
            throw new ErrorException("代理注销失败");
        }
        
        //禁用代理用户登录信息
        List<String> idList = tblBtsUserMapBiz.getLoginList(UserConstant.USER_AGENT, agentId);
        if (idList != null && idList.size() > 0) {
            for (String i : idList) {
                userService.setAcctDisable(i);
            }
        }
    }
    
    @Override
    @Transactional
    public void disableAgent(String agentId, UserBO userBO) throws Exception {
        
        UcAgentDo ucAgentDo = null;
        
        //激活
        ucAgentDo = ucAgentBiz.selectAgent(agentId);
        ucAgentDo.setStatus(StatusConstant.STATUS_DISABLE);
        
        ucAgentDo.setModifyUser(userBO.getUsrName());
        ucAgentDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucAgentDo.setModifyTime(DateUtil.getCurrentDateTime());
        
        int a = ucAgentBiz.updateAgent(ucAgentDo);
        if (a <= 0) {
            throw new ErrorException("代理挂起失败");
        }
        
        //禁用代理用户登录信息
        List<String> idList = tblBtsUserMapBiz.getLoginList(UserConstant.USER_AGENT, agentId);
        if (idList != null && idList.size() > 0) {
            for (String i : idList) {
                userService.setAcctDisable(i);
            }
        }
    }
    
    @Override
    public String checkFees(AgentForm agentForm) {
        
        String error = "";
        
        //默认费率 必须配置一个非0费率
        if (StringUtils.isBlank(agentForm.getDefaultFeeFixed())) {
            agentForm.setDefaultFeeFixed("0");
        }
        if (StringUtils.isBlank(agentForm.getEffectiveFeeRate())) {
            agentForm.setEffectiveFeeRate("0");
        }
        BigDecimal zero = new BigDecimal("0");
        BigDecimal df = new BigDecimal(agentForm.getDefaultFeeFixed());
        BigDecimal dr = new BigDecimal(agentForm.getDefaultFeeRate());
        if (zero.compareTo(df) == 0 && zero.compareTo(dr) == 0) {
            error = "默认费率不能同时为0和空";
            return error;
        }
        
        //默认费率 必须配置一个非0费率
        if (StringUtils.isBlank(agentForm.getEffectiveFeeFixed())) {
            agentForm.setEffectiveFeeFixed("0");
        }
        if (StringUtils.isBlank(agentForm.getEffectiveFeeRate())) {
            agentForm.setEffectiveFeeRate("0");
        }
        BigDecimal ef = new BigDecimal(agentForm.getEffectiveFeeFixed());
        BigDecimal er = new BigDecimal(agentForm.getEffectiveFeeRate());
        if (zero.compareTo(ef) == 0 && zero.compareTo(er) == 0) {
            error = "实行费率不能同时为0和空";
            return error;
        }
        
        return null;
    }
    
    
    /**
     * 创建代理用户
     */
    @Transactional
    @Override
    public void createAgentAcct(UcAgentDo ucAgentDo, UserBO shiroUserBo) {
        
        UserBO userBo = new UserBO();
        userBo.setUsrId(String.valueOf(sequenceBiz.genUserIdSeq()));
        userBo.setUsrName("I" + ucAgentDo.getAgentId());  //默认登录号  A0000001
        
        //获取代理详细信息 - 邮箱, 简称等
        String mail = "";
        UcAgentInfoDo ucAgentInfoDo = ucAgentBiz.selectAgentInfo(ucAgentDo.getAgentId());
        if (ucAgentInfoDo == null || StringUtils.isBlank(ucAgentInfoDo.getContactMail())) {
            mail = ucAgentInfoDo.getContactMail();
        } else {
            mail = ucAgentDo.getAgentId() + DateUtil.getCurrentDate() + "@163.com";
        }
        userBo.setUsrEmail(mail);
        
        //默认密码用手机号的后6位, 如果手机号为空, 或者不足11位, 用机构号替代
        userBo.setUsrPwd(customCredentialsMatcherBiz.encrypt(getPassword(ucAgentDo.getAgentId(), ucAgentInfoDo.getContactPhone())));
        userBo.setUsrDisableTag("1");
        userBo.setUsrCreateBy(shiroUserBo.getUsrName());
        userBo.setUsrUpdateBy(shiroUserBo.getUsrName());
        
        userBo.setUsrType(UserConstant.USER_AGENT);
        userBo.setUsrCode(ucAgentDo.getAgentId());
        userBo.setUsrCodeName(ucAgentInfoDo.getAgentShortName());
        
        userService.addNewUsr(userBo);
        
    }
    
    private String getPassword(String instId, String phone) {
        
        if (StringUtils.isBlank(phone) || phone.length() < 11) {
            if (instId.length() >= 6) {
                return instId.substring(instId.length() - 6);
            } else {
                return instId;
            }
        } else {
            return instId.substring(phone.length() - 6);
        }
    }
    
    
}
