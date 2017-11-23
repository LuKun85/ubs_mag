package com.company.core.service;

import com.company.core.domain.UserBO;
import com.company.core.entity.UcReccomCodeCntlDo;
import com.company.core.form.Pagination;
import com.company.core.form.RecomCodeForm;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
public interface RecomCodeService {
    
    public List<UcReccomCodeCntlDo> getRecomCodeList(UcReccomCodeCntlDo ucReccomCodeCntlDo);
    
    public void createRecomCodes(RecomCodeForm recomCodeForm, UserBO userBO);
    
    public Boolean checkIfRecomCodeExisted(String recom);
    
    UcReccomCodeCntlDo getRecomCode(String recom);
    
    Pagination getAllRecomcodes(RecomCodeForm recomCodeForm);
    
    int getRecomCodeCount(String userCode, String status);
    
    @Transactional
    void dispatchRecomCode(String agentId, String toAgentId, int dispatchCount, String user);
    
    @Transactional
    String dispatchRecomCodeSelected(List<String> recomCodeList, String toAgentId, String user);
}
