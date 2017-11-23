package com.company.core.service.impl;

import com.company.core.biz.SequenceBiz;
import com.company.core.biz.UCRecomCodeControlBiz;
import com.company.core.biz.UCRecomCodeUserRelBiz;
import com.company.core.constant.*;
import com.company.core.domain.RecomCodeBO;
import com.company.core.domain.UserBO;
import com.company.core.entity.*;
import com.company.core.form.Pagination;
import com.company.core.form.RecomCodeForm;
import com.company.core.service.AgentService;
import com.company.core.service.RecomCodeService;
import com.company.core.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service("RecomCodeService")
@Slf4j
public class RecomCodeServiceImpl implements RecomCodeService {
    
    @Autowired
    SequenceBiz sequenceBiz;
    @Autowired
    UCRecomCodeControlBiz ucRecomCodeControlBiz;
    @Autowired
    AgentService agentService;
    @Autowired
    UCRecomCodeUserRelBiz ucRecomCodeUserRelBiz;
    
    @Override
    public List<UcReccomCodeCntlDo> getRecomCodeList(UcReccomCodeCntlDo ucReccomCodeCntlDo) {
        return null;
    }
    
    @Override
    public void createRecomCodes(RecomCodeForm recomCodeForm, UserBO userBO) {
        
        UcReccomCodeCntlDo ucReccomCodeCntlDo = null;

        int totalCount = Integer.parseInt(recomCodeForm.getRecomCodeCount());
        log.info("生成注册码的个数:" + totalCount + "个");
        String batchId = sequenceBiz.genRecomCodeBatchId();
        String expireDate = "20991231";
        if(StringUtils.isNotBlank(recomCodeForm.getExpireDays())){
            expireDate = DateUtil.getAnotherDate(180, 5); //半年之后
        }
    
        //获取机构本身的代理
        String agentId = "";
        if(StringUtils.isBlank(recomCodeForm.getAgentId())){
            UcAgentDo ucAgentDo = agentService.getAgentOfInstOwn(recomCodeForm.getInstId());
            if(ucAgentDo == null){
                throw new ErrorException("机构代理没有开户");
            }
            agentId = ucAgentDo.getAgentId();
        }else {
            agentId = recomCodeForm.getAgentId();
        }
        String userType = UserConstant.USER_AGENT;
    
        for(int i = 1; i <= totalCount; i++){
            ucReccomCodeCntlDo = new UcReccomCodeCntlDo();
            String newRecom = RandomCodesUtils.genRecomCode(Constant.RECOM_CODE_LENGTH_6);
            while(checkIfRecomCodeExisted(newRecom)){
                newRecom = RandomCodesUtils.genRecomCode(Constant.RECOM_CODE_LENGTH_6);
            }
            ucReccomCodeCntlDo.setReccomCode(newRecom);
            ucReccomCodeCntlDo.setType(recomCodeForm.getRecomCodeType());
            ucReccomCodeCntlDo.setCategory(recomCodeForm.getCategory());
            ucReccomCodeCntlDo.setCategoryId(recomCodeForm.getCategoryId());
            ucReccomCodeCntlDo.setBatchId(batchId);
            ucReccomCodeCntlDo.setReccomCodeSeq(sequenceBiz.genRecomCodeSeq());
            ucReccomCodeCntlDo.setReccomCodePath("");
//            ucReccomCodeCntlDo.setStatus(StatusConstant.RECOMCODE_STATUS_NEW);
            ucReccomCodeCntlDo.setStatus(StatusConstant.RECOMCODE_STATUS_ENABLE);
//            ucReccomCodeCntlDo.setMaxUstingTimes(recomCodeForm.);
            ucReccomCodeCntlDo.setPwdRequired(recomCodeForm.getPwdRequired());
            ucReccomCodeCntlDo.setReccomCodePwd(RandomCodesUtils.genRandomNumber(6));
            ucReccomCodeCntlDo.setUserType(userType);
            ucReccomCodeCntlDo.setUserCode(agentId);
            ucReccomCodeCntlDo.setExpireDate(expireDate);
            ucReccomCodeCntlDo.setPrice(recomCodeForm.getPrice());
    
            ucReccomCodeCntlDo.setCreateUser(userBO.getUsrName());
            ucReccomCodeCntlDo.setCreateSource(ComcomUtils.getSourceName(Thread.currentThread()));
            ucReccomCodeCntlDo.setCreateTime(DateUtil.getCurrentDateTime());
            ucReccomCodeCntlDo.setLockVersion(String.valueOf(0));
            ucReccomCodeCntlDo.setModifyUser(userBO.getUsrName());
            ucReccomCodeCntlDo.setModifySource(ComcomUtils.getSourceName(Thread.currentThread()));
            ucReccomCodeCntlDo.setModifyTime(DateUtil.getCurrentDateTime());
    
            int c = ucRecomCodeControlBiz.insertSelective(ucReccomCodeCntlDo);
            if (c <= 0){
                throw new ErrorException("注册码信息入库失败");
            }
        }
    }
    
    @Override
    public Boolean checkIfRecomCodeExisted(String recom) {
        
        if(StringUtils.isBlank(recom)){
            return false;
        }
        
        UcReccomCodeCntlDo ucReccomCodeCntlDo = ucRecomCodeControlBiz.selectByPrimaryKey(recom);
        if(ucReccomCodeCntlDo == null){
            return false;
        }
        return true;
    }
    
    @Override
    public UcReccomCodeCntlDo getRecomCode(String recom) {
        
        if(StringUtils.isBlank(recom)){
            return null;
        }
        
        UcReccomCodeCntlDo ucReccomCodeCntlDo = ucRecomCodeControlBiz.selectByPrimaryKey(recom);
        if(ucReccomCodeCntlDo == null){
            return null;
        }
        return ucReccomCodeCntlDo;
    }
    
    @Override
    public Pagination getAllRecomcodes(RecomCodeForm recomCodeForm){
    
        int pageCurrent = Integer.parseInt(recomCodeForm.getPageCurrent());
        int pageSize = Integer.parseInt(recomCodeForm.getPageSize());
        UcReccomCodeCntlDoExample ucReccomCodeCntlDoExample = formatSearchCriteria(recomCodeForm);

        //获取满足的记录条数
        int tranSize = ucRecomCodeControlBiz.countByExample(ucReccomCodeCntlDoExample);
        Pagination<RecomCodeBO> page = new Pagination<RecomCodeBO>(tranSize, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
        List<UcReccomCodeCntlDo> ucReccomCodeCntlDoList = ucRecomCodeControlBiz.selectByExample(ucReccomCodeCntlDoExample);
        List<RecomCodeBO> list = new ArrayList<>();
        RecomCodeBO recomCodeBO = null;
        if(ucReccomCodeCntlDoList != null && ucReccomCodeCntlDoList.size() > 0){
            for(UcReccomCodeCntlDo ur: ucReccomCodeCntlDoList){
                recomCodeBO = new RecomCodeBO();
                recomCodeBO.setBatchId(ur.getBatchId());
                recomCodeBO.setUserType(ur.getUserType());
                recomCodeBO.setUserCode(ur.getUserCode());
                recomCodeBO.setRecomCodeSeq(ur.getReccomCodeSeq());
                recomCodeBO.setRecomCode(ur.getReccomCode());
                recomCodeBO.setRecomCodePwd(ur.getReccomCodePwd());
                recomCodeBO.setPwdRequired(ur.getPwdRequired());
                recomCodeBO.setStatus(ur.getStatus());
                recomCodeBO.setExpireDate(ur.getExpireDate());
                recomCodeBO.setPrice(ur.getPrice());
                recomCodeBO.setCreateTime(ur.getCreateTime());
                recomCodeBO.setCreateUser(ur.getCreateUser());
                recomCodeBO.setCreateSource(ur.getCreateSource());
                //获取绑定的商户号
                UcRecomCodeUserRelDo ucRecomCodeUserRelDo =ucRecomCodeUserRelBiz.selectByCode(ur.getReccomCode());
                if(ucRecomCodeUserRelDo != null){
                    recomCodeBO.setUserId(ucRecomCodeUserRelDo.getUserCode());
                }
                list.add(recomCodeBO);
            }
        }
        page.addResult(list);
        return page;
    }
    
    public UcReccomCodeCntlDoExample formatSearchCriteria(RecomCodeForm recomCodeForm){
    
        UcReccomCodeCntlDoExample ucReccomCodeCntlDoExample = new UcReccomCodeCntlDoExample();
        UcReccomCodeCntlDoExample.Criteria criteria = ucReccomCodeCntlDoExample.createCriteria();
        
//        if(StringUtils.isNotBlank(recomCodeForm.getInstId())){
//            List<String> agentIdList = agentService.getAgentIdList(recomCodeForm.getInstId(), "");
//            criteria.andUserTypeEqualTo(UserConstant.USER_AGENT);
//            criteria.andUserCodeIn(agentIdList);
//        }
//        if(StringUtils.isNotBlank(recomCodeForm.getAgentId())){
//            criteria.andUserTypeEqualTo(UserConstant.USER_AGENT);
//            criteria.andUserCodeEqualTo(recomCodeForm.getAgentId());
//        }
        
        if(StringUtils.isNotBlank(recomCodeForm.getUserId())){
            criteria.andUserTypeEqualTo(UserConstant.USER_USER);
            criteria.andUserCodeEqualTo(recomCodeForm.getAgentId());
        } else if(StringUtils.isNotBlank(recomCodeForm.getAgentId())){
            criteria.andUserTypeEqualTo(UserConstant.USER_AGENT);
            criteria.andUserCodeEqualTo(recomCodeForm.getAgentId());
        } else if(StringUtils.isNotBlank(recomCodeForm.getInstId())){
            List<String> agentIdList = agentService.getAgentIdList(recomCodeForm.getInstId(), "");
            criteria.andUserTypeEqualTo(UserConstant.USER_AGENT);
            criteria.andUserCodeIn(agentIdList);
        }
        
        if(StringUtils.isNotBlank(recomCodeForm.getStatus())){
            criteria.andStatusEqualTo(recomCodeForm.getStatus());
        }
        if(StringUtils.isNotBlank(recomCodeForm.getBatchId())){
            criteria.andBatchIdEqualTo(recomCodeForm.getBatchId());
        }
        if(StringUtils.isNotBlank(recomCodeForm.getRecomCode())){
            criteria.andReccomCodeEqualTo(recomCodeForm.getRecomCode());
        }
        
        return ucReccomCodeCntlDoExample;
  
    }
    
    /**
     * 获取推荐码的个数
     */
    @Override
    public int getRecomCodeCount(String userCode, String status){
        
        UcReccomCodeCntlDoExample ucReccomCodeCntlDoExample = new UcReccomCodeCntlDoExample();
        UcReccomCodeCntlDoExample.Criteria criteria = ucReccomCodeCntlDoExample.createCriteria();
        
        if(StringUtils.isNotBlank(userCode)){
            criteria.andUserCodeEqualTo(userCode);
        }
        if(StringUtils.isNotBlank(status)){
            criteria.andStatusEqualTo(status);
        }
        return ucRecomCodeControlBiz.countByExample(ucReccomCodeCntlDoExample);
    }
    
    
    /**
     * 下发指定个数的推荐码到指定下级代理
     */
    @Override
    @Transactional
    public void dispatchRecomCode(String agentId, String toAgentId, int dispatchCount, String user) {
    
        UcReccomCodeCntlDoExample ucReccomCodeCntlDoExample = new UcReccomCodeCntlDoExample();
        ucReccomCodeCntlDoExample.createCriteria().andUserCodeEqualTo(agentId);
        List<UcReccomCodeCntlDo> ucReccomCodeCntlDoList = ucRecomCodeControlBiz.selectByExample(ucReccomCodeCntlDoExample);
        int i = 0;
        for (UcReccomCodeCntlDo uc : ucReccomCodeCntlDoList) {
            if (i >= dispatchCount) {
                break;
            }
            uc.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
            uc.setModifyTime(DateUtil.getCurrentDateTime());
            uc.setModifyUser(user);
            uc.setUserCode(toAgentId);
            int d = ucRecomCodeControlBiz.updateSelective(uc);
            if (d <= 0) {
                throw new ErrorException("下发注册时数据库更新异常");
            }
            i++;
            if (i == dispatchCount) {
                break;
            }
        }
    }
    
    
    /**
     * 下发指定推荐码到指定代理
     */
    @Override
    @Transactional
    public String dispatchRecomCodeSelected(List<String> recomCodeList, String toAgentId, String user) {
        
        List<String> sList = new ArrayList<>();
        List<String> eList = new ArrayList<>();
    
        String recomP = "";
        for(String recomCode: recomCodeList){
            
            UcReccomCodeCntlDo ucReccomCodeCntlDo = ucRecomCodeControlBiz.selectByPrimaryKey(recomP);
            if(ucReccomCodeCntlDo == null || !StatusConstant.STATUS_ENABLE.equals(ucReccomCodeCntlDo.getStatus())){
                eList.add(recomP);
                continue;
            }
    
            ucReccomCodeCntlDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
            ucReccomCodeCntlDo.setModifyTime(DateUtil.getCurrentDateTime());
            ucReccomCodeCntlDo.setModifyUser(user);
            ucReccomCodeCntlDo.setUserCode(toAgentId);
            int d = ucRecomCodeControlBiz.updateSelective(ucReccomCodeCntlDo);
            if (d <= 0) {
                throw new ErrorException("下发注册时数据库更新异常");
            }
            sList.add(recomCode);
        }
        
        return "下发成功个数:" + sList.size() + "个." + "下发失败个数:" + eList.size() + "个";
        
    }
    
}
