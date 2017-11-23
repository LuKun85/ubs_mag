package com.company.core.service;

import com.company.core.domain.UserBO;
import com.company.core.entity.UcInstDo;
import com.company.core.entity.UcInstInfoDo;
import com.company.core.form.InstForm;
import com.company.core.form.Pagination;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
public interface InstService {
    
    public List<UcInstDo> getInstList();
    
    Pagination getInstListPage(InstForm instForm);
    
    public List<UcInstDo> getInstListByStatus(String status);
    
    public String createNewInst(InstForm instForm, UserBO userBO) throws Exception;
    
    @Transactional
    void updateInst(InstForm instForm, UserBO userBO) throws Exception;
    
    @Transactional
    Map<String , String> activateCheck(String instId) throws Exception;
    
    @Transactional
    void activateInst(String instId, UserBO userBO) throws Exception;
    
    @Transactional
    void cancelInst(String instId, UserBO userBO) throws Exception;
    
    @Transactional
    void disableInst(String instId, UserBO userBO) throws Exception;
    
    public UcInstDo getInst(String instId);
    
    UcInstInfoDo getInstInfo(String instId);
    
    UcInstDo getTheDefaultInst();
    
    public Boolean checkIfDupInstByName(String instName, String instShortName );
    
    String checkFees(InstForm instForm);
    
    void formatInstFormFromInst(InstForm instForm);
    
    void formatInstFormFromInstInfo(InstForm instForm);
    
    void formatInstFormFromFee(InstForm instForm);
    
    @Transactional
    void createInstAcct(UcInstDo ucInstDo, UserBO shiroUserBo);
}
