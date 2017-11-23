package com.company.core.service.impl;

import com.company.core.biz.*;
import com.company.core.constant.*;
import com.company.core.domain.InstBO;
import com.company.core.domain.RecomCodeBO;
import com.company.core.domain.UserBO;
import com.company.core.entity.*;
import com.company.core.form.AgentForm;
import com.company.core.form.InstForm;
import com.company.core.form.Pagination;
import com.company.core.form.RecomCodeForm;
import com.company.core.service.AgentService;
import com.company.core.service.InstService;
import com.company.core.service.UserService;
import com.company.core.util.ComcomUtils;
import com.company.core.util.DateUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.Subject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service("InstService")
@Slf4j
public class InstServiceImpl implements InstService {
    
    @Autowired
    UCInstBiz ucInstBiz;
    @Autowired
    SequenceBiz sequenceBiz;
    @Autowired
    UCFeeBiz  ucFeeBiz;
    @Autowired
    AgentService agentService;
    @Autowired
    CustomCredentialsMatcherBiz customCredentialsMatcherBiz;
    @Autowired
    UserService userService;
    @Autowired
    TblBtsUserMapBiz tblBtsUserMapBiz;
    
    
    @Override
    public List<UcInstDo> getInstList() {
    
        return ucInstBiz.selectByExample();
        
    }
    
    @Override
    public Pagination getInstListPage(InstForm instForm) {
    
        int pageCurrent = Integer.parseInt(instForm.getPageCurrent());
        int pageSize = Integer.parseInt(instForm.getPageSize());
        UcInstDoExample ucInstDoExample = formatInstSearchCriteria(instForm);
    
        //获取满足的记录条数
        int tranSize = ucInstBiz.countByExample(ucInstDoExample);
        Pagination<InstBO> page = new Pagination<InstBO>(tranSize, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
        List<UcInstDo> ucInstDos = ucInstBiz.selectByExample(ucInstDoExample);
        List<InstBO> list = new ArrayList<>();
        InstBO instBO = null;
        if(ucInstDos != null && ucInstDos.size() > 0){
            for(UcInstDo ur: ucInstDos){
                instBO = new InstBO();
                instBO.setInstId(ur.getInstId());
                instBO.setInstName(ur.getInstName());
                instBO.setStatus(ur.getStatus());
                instBO.setCreateUser(ur.getCreateUser());
                instBO.setCreateTime(ur.getCreateTime());
                list.add(instBO);
            }
        }
        page.addResult(list);
        return page;
    }
    
    
    public UcInstDoExample formatInstSearchCriteria(InstForm instForm){
    
        UcInstDoExample ucInstDoExample = new UcInstDoExample();
        UcInstDoExample.Criteria criteria = ucInstDoExample.createCriteria();
        
        if(StringUtils.isNotBlank(instForm.getInstId())){
            criteria.andInstIdEqualTo(instForm.getInstId());
        }
        if(StringUtils.isNotBlank(instForm.getInstName())){
            criteria.andInstNameLike("%" + instForm.getInstName() + "%");
        }
        if(StringUtils.isNotBlank(instForm.getStatus())){
            criteria.andStatusEqualTo(instForm.getStatus());
        }
        return ucInstDoExample;
    }
    
    
    
    @Override
    public List<UcInstDo> getInstListByStatus(String status) {
        
        return ucInstBiz.selectByExample();
        
    }
    
    @Override
    @Transactional
    public String createNewInst(InstForm instForm, UserBO userBO) throws Exception {
    
        //创建机构基础信息
        this.createInstBaseInfo(instForm, userBO);
        
        //创建机构详细信息
        this.createInstDetailInfo(instForm, userBO);
        
        //创建机构费率信息
        this.createInstFeeInfo(instForm, userBO);
        
        return instForm.getInstId();
    }
    
    
    @Override
    @Transactional
    public void updateInst(InstForm instForm, UserBO userBO) throws Exception {
        
        //更新机构基础信息
        this.updateInstBaseInfo(instForm, userBO);
        
        //更新机构详细信息
        this.updateInstDetailInfo(instForm, userBO);
        
        //更新机构费率信息
        this.updateInstFeeInfo(instForm, userBO);
        
    }
    
    
    @Override
    @Transactional
    public Map<String , String> activateCheck(String instId) throws Exception {
    
        Map<String , String> result = new HashMap<>();
        
        //检查机构信息
        UcInstDo ucInstDo = getInst(instId);
        if(ucInstDo == null){
            result.put("error", "机构不存在");
        }
        //检查费率信息
        UcFeeDo ucFeeDo = new UcFeeDo();
        ucFeeDo.setUserType(UserConstant.USER_INST);
        ucFeeDo.setUserCode(instId);
        ucFeeDo.setCategory(ucInstDo.getCategory());
        ucFeeDo.setCategoryId(ucInstDo.getCategoryId());
        ucFeeDo.setFeeType("DF");
        UcFeeDo df = ucFeeBiz.getFee(ucFeeDo);
        if(df == null){
            result.put("error", "默认固定费用未配置");
        }
    
        ucFeeDo.setFeeType("DR");
        UcFeeDo dr = ucFeeBiz.getFee(ucFeeDo);
        if(dr != null){
            result.put("error", "默认费率未配置");
        }
    
        ucFeeDo.setFeeType("EF");
        UcFeeDo ef = ucFeeBiz.getFee(ucFeeDo);
        if(ef != null){
            result.put("error", "实收固定费用未配置");
        }
    
        ucFeeDo.setFeeType("ER");
        UcFeeDo er = ucFeeBiz.getFee(ucFeeDo);
        if(er != null){
            result.put("error", "实收费率未配置");
        }
        return result;
    }
    
    @Override
    @Transactional
    public void activateInst(String instId, UserBO userBO) throws Exception {
        UcInstDo ucInstDo = null;
    
        try {
            //激活
            ucInstDo = ucInstBiz.selectByPrimaryKey(instId);
            ucInstDo.setStatus(StatusConstant.STATUS_ENABLE);
        
            ucInstDo.setModifyUser(userBO.getUsrName());
            ucInstDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
            ucInstDo.setModifyTime(DateUtil.getCurrentDateTime());
        
            int a = ucInstBiz.updateInst(ucInstDo);
            if(a <= 0){
                throw new ErrorException("机构激活失败");
            }
        
            //查看机构默认登录用户
            List<String> idList = tblBtsUserMapBiz.getLoginList(UserConstant.USER_INST, instId);
            if(idList != null && idList.size() >0){
                //已经创建 - 就去激活
                if(idList != null && idList.size()> 0){
                    for (String i : idList) {
                        userService.setAcctEnable(i);
                    }
                }
            } else {
                //未创建
                this.createInstAcct(ucInstDo, userBO);
            }
            
            //查看默认的机构代理是否开出, 如果没有继续, 如果已经开了, 则跳出
            UcAgentDo ucAgentDo = agentService.getDefautlAgentOfInst(instId);
            if(ucAgentDo != null){
                return;
            }
        } catch (ErrorException e) {
            e.printStackTrace();
            throw new ErrorException("机构激活异常");
        }
    
        try {
            //机构详细信息
            UcInstInfoDo ucInstInfoDo = ucInstBiz.getInstInfo(instId);
        
            //机构激活成功, 默认开通该机构的代理账号 agent-type=0
            AgentForm agentForm = new AgentForm();
            agentForm.setInstId(ucInstDo.getInstId());
            agentForm.setAgentName(ucInstDo.getInstName());
            agentForm.setAgentType(UserConstant.USER_TYPE_DEFAULT);  //机构类型
            agentForm.setCategory(ucInstDo.getCategory());
            agentForm.setCategoryId(ucInstDo.getCategoryId());
            agentForm.setAgentOk(ucInstDo.getAgentOk());
            agentForm.setAgentCountLimit(ucInstDo.getAgentCountLimit() != null? ucInstDo.getAgentCountLimit().toString(): "");
            agentForm.setLimitArea(ucInstDo.getLimitArea());
            agentForm.setLimitAreaCode(ucInstDo.getLimitAreaCode());
            agentService.createAgentBaseInfo(agentForm, userBO);
        
            //详细信息
            agentForm.setAgentShortName(ucInstInfoDo.getInstShortName());
            agentForm.setBusinessLicense(ucInstInfoDo.getBusinessLicense());
        
            agentForm.setLegalPersonIdType(ucInstInfoDo.getLegalPersonIdType());
            agentForm.setLegalPersonId(ucInstInfoDo.getLegalPersonCertId());
            agentForm.setLegalPersonPhone(ucInstInfoDo.getLegalPersonPhone());
            agentForm.setLegalPersonMail(ucInstInfoDo.getLegalPersonMail());
            agentForm.setLegalPersonAddress(ucInstInfoDo.getLegalPersonAddress());
            agentForm.setContactName(ucInstInfoDo.getContactName());
            agentForm.setContactIdType(ucInstInfoDo.getContactIdType());
            agentForm.setContactCertId(ucInstInfoDo.getContactCertId());
            agentForm.setContactPhone(ucInstInfoDo.getContactPhone());
            agentForm.setContactMail(ucInstInfoDo.getContactMail());
            agentForm.setContactAddress(ucInstInfoDo.getContactAddress());
            agentService.createAgentDetailInfo(agentForm, userBO);
        
            //费率信息
            UcFeeDo ucFeeDo = new UcFeeDo();
            ucFeeDo.setUserType(UserConstant.USER_INST);
            ucFeeDo.setUserCode(agentForm.getInstId());
            ucFeeDo.setCategory(SystemConstant.DEFAULT_CATEGORY);
            ucFeeDo.setCategoryId(SystemConstant.DEFAULT_CATEGORY_ID);
            ucFeeDo.setFeeType(Constant.FEE_DEFAULT_FIXED);
            UcFeeDo df = ucFeeBiz.getFee(ucFeeDo);
            if(df != null){
                agentForm.setDefaultFeeFixed(df.getFeeMode());
            }
        
            ucFeeDo.setFeeType(Constant.FEE_DEFAULT_RATE);
            UcFeeDo dr = ucFeeBiz.getFee(ucFeeDo);
            if(df != null){
                agentForm.setDefaultFeeRate(dr.getFeeMode());
            }
        
            ucFeeDo.setFeeType(Constant.FEE_EFFECTIVE_FIXED);
            UcFeeDo ef = ucFeeBiz.getFee(ucFeeDo);
            if(ef != null){
                agentForm.setEffectiveFeeFixed(ef.getFeeMode());
            }
        
            ucFeeDo.setFeeType(Constant.FEE_EFFECTIVE_RATE);
            UcFeeDo er = ucFeeBiz.getFee(ucFeeDo);
            if(er != null){
                agentForm.setEffectiveFeeRate(er.getFeeMode());
            }
            agentService.createAgentFeeInfo(agentForm, userBO);
            
        } catch (Exception e) {
            e.printStackTrace();
            log.info("默认代理开通失败");
            throw new ErrorException("默认代理开通失败");
            
        }
    
    }
    
    @Override
    @Transactional
    public void cancelInst(String instId, UserBO userBO) throws Exception {
        
        //激活
        UcInstDo ucInstDo = ucInstBiz.selectByPrimaryKey(instId);
        ucInstDo.setStatus(StatusConstant.STATUS_CANNEL);
        
        ucInstDo.setModifyUser(userBO.getUsrName());
        ucInstDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucInstDo.setModifyTime(DateUtil.getCurrentDateTime());
        
        int a = ucInstBiz.updateInst(ucInstDo);
        if(a <= 0){
            throw new ErrorException("机构注销失败");
        }
    
        //禁用机构用户登录信息
        List<String> idList = tblBtsUserMapBiz.getLoginList(UserConstant.USER_INST, instId);
        if(idList != null && idList.size()> 0){
            for (String i : idList) {
                userService.setAcctDisable(i);
            }
        }
    }
    
    @Override
    @Transactional
    public void disableInst(String instId, UserBO userBO) throws Exception {
        
        //禁用
        UcInstDo ucInstDo = ucInstBiz.selectByPrimaryKey(instId);
        ucInstDo.setStatus(StatusConstant.STATUS_DISABLE);
        
        ucInstDo.setModifyUser(userBO.getUsrName());
        ucInstDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucInstDo.setModifyTime(DateUtil.getCurrentDateTime());
        
        int a = ucInstBiz.updateInst(ucInstDo);
        if(a <= 0){
            throw new ErrorException("机构禁用失败");
        }
        
        //禁用机构用户登录信息
        List<String> idList = tblBtsUserMapBiz.getLoginList(UserConstant.USER_INST, instId);
        if(idList != null && idList.size()> 0){
            for (String i : idList) {
                userService.setAcctDisable(i);
            }
        }
        
        
    }
    
    
    public void createInstBaseInfo(InstForm instForm, UserBO userBO){
        
        //创建机构
        UcInstDo ucInstDo = new UcInstDo();
        ucInstDo.setInstId(sequenceBiz.genInstId());
        ucInstDo.setInstType(instForm.getInstType());
        ucInstDo.setInstName(instForm.getInstName());
        ucInstDo.setStatus(StatusConstant.STATUS_NEW);
        ucInstDo.setCategory(instForm.getCategory());
        ucInstDo.setCategoryId(instForm.getCategoryId());
        ucInstDo.setAgentOk(instForm.getAgentOk());
        BigDecimal bigDecimal = new BigDecimal(instForm.getAgentCountLimit());
        ucInstDo.setAgentCountLimit(bigDecimal);
        ucInstDo.setLimitArea(instForm.getLimitArea());
        ucInstDo.setLimitAreaCode(instForm.getLimitAreaCode());
        ucInstDo.setCreateUser(userBO.getUsrName());
        ucInstDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucInstDo.setCreateTime(DateUtil.getCurrentDateTime());
        ucInstDo.setLockVersion(String.valueOf(0));
        ucInstDo.setModifyUser(userBO.getUsrName());
        ucInstDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucInstDo.setModifyTime(DateUtil.getCurrentDateTime());
    
        int i = ucInstBiz.insertInst(ucInstDo);
        if(i <= 0){
            throw new ErrorException("机构信息入库失败");
        }
        
        instForm.setInstId(ucInstDo.getInstId());
        
    }
    
    public void createInstDetailInfo(InstForm instForm, UserBO userBO){
        
        //存入机构详细信息
        UcInstInfoDo ucInstInfoDo = new UcInstInfoDo();
        ucInstInfoDo.setInstId(instForm.getInstId());
        ucInstInfoDo.setInstName(instForm.getInstName());
        ucInstInfoDo.setInstShortName(instForm.getInstShortName());
        ucInstInfoDo.setBusinessLicense(instForm.getBusinessLicense());
        ucInstInfoDo.setLegalPersonName(instForm.getLegalPersonName());
        ucInstInfoDo.setLegalPersonIdType(instForm.getLegalPersonIdType());
        ucInstInfoDo.setLegalPersonCertId(instForm.getLegalPersonId());
        ucInstInfoDo.setLegalPersonPhone(instForm.getLegalPersonPhone());
        ucInstInfoDo.setLegalPersonMail(instForm.getLegalPersonMail());
        ucInstInfoDo.setLegalPersonAddress(instForm.getLegalPersonAddress());
        ucInstInfoDo.setContactName(instForm.getContactName());
        ucInstInfoDo.setContactIdType(instForm.getContactIdType());
        ucInstInfoDo.setContactCertId(instForm.getContactCertId());
        ucInstInfoDo.setContactPhone(instForm.getContactPhone());
        ucInstInfoDo.setContactMail(instForm.getContactMail());
        ucInstInfoDo.setContactAddress(instForm.getContactAddress());
    
        ucInstInfoDo.setCreateUser(userBO.getUsrName());
        ucInstInfoDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucInstInfoDo.setCreateTime(DateUtil.getCurrentDateTime());
        ucInstInfoDo.setModifyUser(userBO.getUsrName());
        ucInstInfoDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucInstInfoDo.setModifyTime(DateUtil.getCurrentDateTime());
        ucInstInfoDo.setLockVersion(String.valueOf(0));
    
        int c = ucInstBiz.insertInstInfo(ucInstInfoDo);
        if(c <= 0){
            throw new ErrorException("机构详细信息入库失败");
        }
    }
    
    public void createInstFeeInfo(InstForm instForm, UserBO userBO){
        
        //存入机构费率
        UcFeeDo ucFeeDo = new UcFeeDo();
        ucFeeDo.setUserType(UserConstant.USER_INST);
        ucFeeDo.setUserCode(instForm.getInstId());
        
        if(StringUtils.isNotBlank(instForm.getCategory())){
            ucFeeDo.setCategory(instForm.getCategory());
        } else {
            ucFeeDo.setCategory(SystemConstant.DEFAULT_CATEGORY);  //默认是1
        }
        if(StringUtils.isNotBlank(instForm.getCategoryId())){
            ucFeeDo.setCategoryId(instForm.getCategoryId());
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
        if(StringUtils.isNotBlank(instForm.getDefaultFeeFixed()) && !ucFeeBiz.zeroFee(instForm.getDefaultFeeFixed())){
            ucFeeDo.setFeeType(Constant.FEE_DEFAULT_FIXED);
            ucFeeDo.setFeeDesc(Constant.FEE_DEFAULT_FIXED_DESC);
            ucFeeDo.setFeeMode(instForm.getDefaultFeeFixed());
            int i = ucFeeBiz.insertFee(ucFeeDo);
            if(i <=0 ){
                throw new ErrorException("数据新增失败");
            }
        }
        //默认固定比例
        if(StringUtils.isNotBlank(instForm.getDefaultFeeRate()) && !ucFeeBiz.zeroFee(instForm.getDefaultFeeRate())){
            ucFeeDo.setFeeType(Constant.FEE_DEFAULT_RATE);
            ucFeeDo.setFeeDesc(Constant.FEE_DEFAULT_RATE_DESC);
            ucFeeDo.setFeeMode(instForm.getDefaultFeeRate());
            int c = ucFeeBiz.insertFee(ucFeeDo);
            if(c <=0 ){
                throw new ErrorException("数据新增失败");
            }
        }
        //实收固定单笔
        if(StringUtils.isNotBlank(instForm.getEffectiveFeeFixed()) && !ucFeeBiz.zeroFee(instForm.getEffectiveFeeFixed())){
            ucFeeDo.setFeeType(Constant.FEE_EFFECTIVE_FIXED);
            ucFeeDo.setFeeDesc(Constant.FEE_EFFECTIVE_FIXED_DESC);
            ucFeeDo.setFeeMode(instForm.getEffectiveFeeFixed());
            int d = ucFeeBiz.insertFee(ucFeeDo);
            if(d <=0 ){
                throw new ErrorException("数据新增失败");
            }
        }
        //实收固定比例
        if(StringUtils.isNotBlank(instForm.getEffectiveFeeRate()) && !ucFeeBiz.zeroFee(instForm.getEffectiveFeeRate())){
            ucFeeDo.setFeeType(Constant.FEE_EFFECTIVE_RATE);
            ucFeeDo.setFeeDesc(Constant.FEE_EFFECTIVE_RATE_DESC);
            ucFeeDo.setFeeMode(instForm.getEffectiveFeeRate());
            int f = ucFeeBiz.insertFee(ucFeeDo);
            if(f <=0 ){
                throw new ErrorException("数据新增失败");
            }
        }
    }
    
    
    public void updateInstBaseInfo(InstForm instForm, UserBO userBO){
        
        //创建机构
        UcInstDo ucInstDo = new UcInstDo();
        ucInstDo.setInstId(instForm.getInstId());
        //ucInstDo.setInstType(instForm.getInstType());  机构类型不允许修改
        ucInstDo.setInstName(instForm.getInstName());
        //ucInstDo.setStatus(StatusConstant.STATUS_NEW); 机构状态不允许修改
        ucInstDo.setCategory(instForm.getCategory());
        ucInstDo.setCategoryId(instForm.getCategoryId());
        ucInstDo.setAgentOk(instForm.getAgentOk());
        BigDecimal bigDecimal = new BigDecimal(instForm.getAgentCountLimit());
        ucInstDo.setAgentCountLimit(bigDecimal);
        ucInstDo.setLimitArea(instForm.getLimitArea());
        ucInstDo.setLimitAreaCode(instForm.getLimitAreaCode());
        //ucInstDo.setCreateUser(userBO.getUsrName());   机构创建者不允许修改
        //ucInstDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE); 机构创建source不允许修改
        //ucInstDo.setCreateTime(DateUtil.getCurrentDateTime()); 机构创建时间不允许修改
        //ucInstDo.setLockVersion(String.valueOf(0));  version不允许修改
        ucInstDo.setModifyUser(userBO.getUsrName());
        ucInstDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucInstDo.setModifyTime(DateUtil.getCurrentDateTime());
        
        int i = ucInstBiz.updateInst(ucInstDo);
        if(i <= 0){
            throw new ErrorException("机构基础信息更新失败");
        }
    }
    
    public void updateInstDetailInfo(InstForm instForm, UserBO userBO){
        
        //存入机构详细信息
        UcInstInfoDo ucInstInfoDo = new UcInstInfoDo();
        ucInstInfoDo.setInstId(instForm.getInstId());
        ucInstInfoDo.setInstName(instForm.getInstName());
        ucInstInfoDo.setInstShortName(instForm.getInstShortName());
        ucInstInfoDo.setBusinessLicense(instForm.getBusinessLicense());
        ucInstInfoDo.setLegalPersonName(instForm.getLegalPersonName());
        ucInstInfoDo.setLegalPersonIdType(instForm.getLegalPersonIdType());
        ucInstInfoDo.setLegalPersonCertId(instForm.getLegalPersonId());
        ucInstInfoDo.setLegalPersonPhone(instForm.getLegalPersonPhone());
        ucInstInfoDo.setLegalPersonMail(instForm.getLegalPersonMail());
        ucInstInfoDo.setLegalPersonAddress(instForm.getLegalPersonAddress());
        ucInstInfoDo.setContactName(instForm.getContactName());
        ucInstInfoDo.setContactIdType(instForm.getContactIdType());
        ucInstInfoDo.setContactCertId(instForm.getContactCertId());
        ucInstInfoDo.setContactPhone(instForm.getContactPhone());
        ucInstInfoDo.setContactMail(instForm.getContactMail());
        ucInstInfoDo.setContactAddress(instForm.getContactAddress());
        
//        ucInstInfoDo.setCreateUser(userBO.getUsrName());
//        ucInstInfoDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE);
//        ucInstInfoDo.setCreateTime(DateUtil.getCurrentDateTime());
        
        ucInstInfoDo.setModifyUser(userBO.getUsrName());
        ucInstInfoDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucInstInfoDo.setModifyTime(DateUtil.getCurrentDateTime());
//        ucInstInfoDo.setLockVersion(String.valueOf(0));
        
        int c = ucInstBiz.updateInstInfo(ucInstInfoDo);
        if(c <= 0){
            throw new ErrorException("机构详细信息更新失败");
        }
    }
    
    public void updateInstFeeInfo(InstForm instForm, UserBO userBO){
        
        //存入机构费率
        UcFeeDo ucFeeDo = new UcFeeDo();
        ucFeeDo.setUserType(UserConstant.USER_INST);
        ucFeeDo.setUserCode(instForm.getInstId());
        
        if(StringUtils.isNotBlank(instForm.getCategory())){
            ucFeeDo.setCategory(instForm.getCategory());
        } else {
            ucFeeDo.setCategory(SystemConstant.DEFAULT_CATEGORY);  //默认是1
        }
        if(StringUtils.isNotBlank(instForm.getCategoryId())){
            ucFeeDo.setCategoryId(instForm.getCategoryId());
        } else {
            ucFeeDo.setCategoryId(SystemConstant.DEFAULT_CATEGORY_ID);  //默认是P001
        }
        ucFeeDo.setStatus(StatusConstant.STATUS_ENABLE);
        ucFeeDo.setPercentFlag("N");
        
//        ucFeeDo.setCreateUser(userBO.getUsrName());
//        ucFeeDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE);
//        ucFeeDo.setCreateTime(DateUtil.getCurrentDateTime());
        
        ucFeeDo.setModifyUser(userBO.getUsrName());
        ucFeeDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucFeeDo.setModifyTime(DateUtil.getCurrentDateTime());
        
//        ucFeeDo.setLockedVersion(String.valueOf(0));
        
        //默认固定单笔
        if(StringUtils.isNotBlank(instForm.getDefaultFeeFixed()) && !ucFeeBiz.zeroFee(instForm.getDefaultFeeFixed())){
            ucFeeDo.setFeeType(Constant.FEE_DEFAULT_FIXED);
            ucFeeDo.setFeeDesc(Constant.FEE_DEFAULT_FIXED_DESC);
            ucFeeDo.setFeeMode(instForm.getDefaultFeeFixed());
            int i = ucFeeBiz.updateFee(ucFeeDo);
            if(i <=0 ){
                throw new ErrorException("费率更新失败");
            }
        }
        //默认固定比例
        if(StringUtils.isNotBlank(instForm.getDefaultFeeRate()) && !ucFeeBiz.zeroFee(instForm.getDefaultFeeRate())){
            ucFeeDo.setFeeType(Constant.FEE_DEFAULT_RATE);
            ucFeeDo.setFeeDesc(Constant.FEE_DEFAULT_RATE_DESC);
            ucFeeDo.setFeeMode(instForm.getDefaultFeeRate());
            int c = ucFeeBiz.updateFee(ucFeeDo);
            if(c <=0 ){
                throw new ErrorException("费率更新失败");
            }
        }
        //实收固定单笔
        if(StringUtils.isNotBlank(instForm.getEffectiveFeeFixed()) && !ucFeeBiz.zeroFee(instForm.getEffectiveFeeFixed())){
            ucFeeDo.setFeeType(Constant.FEE_EFFECTIVE_FIXED);
            ucFeeDo.setFeeDesc(Constant.FEE_EFFECTIVE_FIXED_DESC);
            ucFeeDo.setFeeMode(instForm.getEffectiveFeeFixed());
            int d = ucFeeBiz.updateFee(ucFeeDo);
            if(d <=0 ){
                throw new ErrorException("费率更新失败");
            }
        }
        //实收固定比例
        if(StringUtils.isNotBlank(instForm.getEffectiveFeeRate()) && !ucFeeBiz.zeroFee(instForm.getEffectiveFeeRate())){
            ucFeeDo.setFeeType(Constant.FEE_EFFECTIVE_RATE);
            ucFeeDo.setFeeDesc(Constant.FEE_EFFECTIVE_RATE_DESC);
            ucFeeDo.setFeeMode(instForm.getEffectiveFeeRate());
            int f = ucFeeBiz.updateFee(ucFeeDo);
            if(f <=0 ){
                throw new ErrorException("费率更新失败");
            }
        }
    }
    
    
    @Override
    public UcInstDo getInst(String instId) {
        
        return ucInstBiz.selectByPrimaryKey(instId);

    }
    
    @Override
    public UcInstInfoDo getInstInfo(String instId) {
        
        return ucInstBiz.getInstInfo(instId);
        
    }
    
    @Override
    public UcInstDo getTheDefaultInst() {
        
        List<UcInstDo> instDos = getInstListByStatus(StatusConstant.STATUS_ENABLE);
        if(instDos != null && instDos.size() >0){
            for(UcInstDo u: instDos){
                if(UserConstant.USER_TYPE_DEFAULT.equals(u.getInstType())){
                    return u;
                }
            }
        }
        return null;
    }
    
    @Override
    public Boolean checkIfDupInstByName(String instName, String instShortName ) {
    
        if(StringUtils.isNotBlank(instShortName)){
            List<UcInstInfoDo> ucInstInfoDos = ucInstBiz.selectByShortName(instName);
            if(ucInstInfoDos !=null && ucInstInfoDos.size() >0){
                return true;
            }
        }
        
        if(StringUtils.isNotBlank(instName)){
            List<UcInstInfoDo> ucInstInfoDos = ucInstBiz.selectByName(instName);
            if(ucInstInfoDos !=null && ucInstInfoDos.size() >0){
                return true;
            }
        }
    

        return false;
    }
    
    @Override
    public String checkFees(InstForm instForm) {
        
        String error = "";
        
        //默认费率 必须配置一个非0费率
        if(StringUtils.isBlank(instForm.getDefaultFeeFixed())){
            instForm.setDefaultFeeFixed("0");
        }
        if(StringUtils.isBlank(instForm.getDefaultFeeRate())){
            instForm.setDefaultFeeRate("0");
        }
        BigDecimal zero = new BigDecimal("0");
        BigDecimal df = new BigDecimal(instForm.getDefaultFeeFixed());
        BigDecimal dr = new BigDecimal(instForm.getDefaultFeeRate());
        if(zero.compareTo(df) == 0 && zero.compareTo(dr) == 0){
            error = "默认费率不能同时为0和空";
            return error;
        }
    
        //默认费率 必须配置一个非0费率
        if(StringUtils.isBlank(instForm.getEffectiveFeeFixed())){
            instForm.setEffectiveFeeFixed("0");
        }
        if(StringUtils.isBlank(instForm.getEffectiveFeeRate())){
            instForm.setEffectiveFeeRate("0");
        }
        BigDecimal ef = new BigDecimal(instForm.getEffectiveFeeFixed());
        BigDecimal er = new BigDecimal(instForm.getEffectiveFeeRate());
        if(zero.compareTo(ef) == 0 && zero.compareTo(er) == 0){
            error = "实行费率不能同时为0和空";
            return error;
        }
        
        return null;
    }
    
    
    @Override
    public void formatInstFormFromInst(InstForm instForm) {
    
        UcInstDo ucInstDo = getInst(instForm.getInstId());
        if(ucInstDo == null){
            return;
        }
        instForm.setInstName(ucInstDo.getInstName());
        instForm.setInstType(ucInstDo.getInstType());
        instForm.setStatus(ucInstDo.getStatus());
        instForm.setCategory(ucInstDo.getCategory());
        instForm.setCategoryId(ucInstDo.getCategoryId());
        instForm.setAgentOk(ucInstDo.getAgentOk());
        instForm.setAgentCountLimit(String.valueOf(ucInstDo.getAgentCountLimit()));
        instForm.setLimitArea(ucInstDo.getLimitArea());
        instForm.setLimitAreaCode(ucInstDo.getLimitAreaCode());
    }
    
    @Override
    public void formatInstFormFromInstInfo(InstForm instForm) {
        
        UcInstInfoDo ucInstInfoDo = getInstInfo(instForm.getInstId());
        if(ucInstInfoDo == null){
            return;
        }
        instForm.setInstShortName(ucInstInfoDo.getInstShortName());
        instForm.setBusinessLicense(ucInstInfoDo.getBusinessLicense());
        instForm.setLegalPersonName(ucInstInfoDo.getLegalPersonName());
        instForm.setLegalPersonIdType(ucInstInfoDo.getLegalPersonIdType());
        instForm.setLegalPersonId(ucInstInfoDo.getLegalPersonCertId());
        instForm.setLegalPersonAddress(ucInstInfoDo.getLegalPersonAddress());
        instForm.setLegalPersonPhone(ucInstInfoDo.getLegalPersonPhone());
        instForm.setLegalPersonAddress(ucInstInfoDo.getLegalPersonAddress());
        instForm.setLegalPersonMail(ucInstInfoDo.getLegalPersonMail());
        
        instForm.setContactName(ucInstInfoDo.getContactName());
        instForm.setContactCertId(ucInstInfoDo.getContactCertId());
        instForm.setContactIdType(ucInstInfoDo.getContactIdType());
        instForm.setContactPhone(ucInstInfoDo.getContactPhone());
        instForm.setContactMail(ucInstInfoDo.getContactMail());
        instForm.setContactAddress(ucInstInfoDo.getContactAddress());

    }
    
    @Override
    public void formatInstFormFromFee(InstForm instForm) {
        
        UcFeeDo ucFeeDo = new UcFeeDo();
        ucFeeDo.setUserType(UserConstant.USER_INST);
        ucFeeDo.setUserCode(instForm.getInstId());
        
        //之后扩充时, 需要替换掉
        ucFeeDo.setCategory("1");
        ucFeeDo.setCategoryId("P001");
        ucFeeDo.setFeeType("DF");
        UcFeeDo df = ucFeeBiz.getFee(ucFeeDo);
        if(df != null){
            instForm.setDefaultFeeFixed(df.getFeeMode());
        }
    
        ucFeeDo.setFeeType("DR");
        UcFeeDo dr = ucFeeBiz.getFee(ucFeeDo);
        if(dr != null){
            instForm.setDefaultFeeRate(dr.getFeeMode());
        }
    
        ucFeeDo.setFeeType("EF");
        UcFeeDo ef = ucFeeBiz.getFee(ucFeeDo);
        if(ef != null){
            instForm.setEffectiveFeeFixed(ef.getFeeMode());
        }
    
        ucFeeDo.setFeeType("ER");
        UcFeeDo er = ucFeeBiz.getFee(ucFeeDo);
        if(er != null){
            instForm.setEffectiveFeeRate(er.getFeeMode());
        }
        
    }
    
    
    /**
     * 创建机构用户
     * @param ucInstDo
     * @param shiroUserBo
     */
    @Transactional
    @Override
    public void createInstAcct(UcInstDo ucInstDo, UserBO shiroUserBo){
    
        UserBO userBo = new UserBO();
        userBo.setUsrId(String.valueOf(sequenceBiz.genUserIdSeq()));
        userBo.setUsrName("I" + ucInstDo.getInstId());  //默认登录号  I0000001
        
        //获取机构详细信息 - 邮箱, 简称等
        String mail = "";
        UcInstInfoDo ucInstInfoDo = ucInstBiz.getInstInfo(ucInstDo.getInstId());
        if(ucInstInfoDo == null || StringUtils.isBlank(ucInstInfoDo.getContactPhone())){
            mail = ucInstInfoDo.getContactPhone();
        } else {
            mail = ucInstDo.getInstId() + DateUtil.getCurrentDate() + "@163.com";
        }
        userBo.setUsrEmail(mail);
  
        //默认密码用手机号的后6位, 如果手机号为空, 或者不足11位, 用机构号替代
        userBo.setUsrPwd(customCredentialsMatcherBiz.encrypt(getPassword(ucInstDo.getInstId(), ucInstInfoDo.getContactPhone())));
        userBo.setUsrDisableTag("1");
        userBo.setUsrCreateBy(shiroUserBo.getUsrName());
        userBo.setUsrUpdateBy(shiroUserBo.getUsrName());
    
        userBo.setUsrType(UserConstant.USER_INST);
        userBo.setUsrCode(ucInstDo.getInstId());
        userBo.setUsrCodeName(ucInstInfoDo.getInstShortName());
    
        userService.addNewUsr(userBo);
    
    }
    
    private String getPassword(String instId, String phone){
        
        if(StringUtils.isBlank(phone) || phone.length() < 11){
            if(instId.length() >=6){
                return instId.substring(instId.length() - 6);
            } else {
                return instId;
            }
        } else {
            return instId.substring(phone.length() - 6);
        }
    }

    
    
}
