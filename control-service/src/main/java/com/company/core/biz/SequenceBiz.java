package com.company.core.biz;

import com.company.core.entity.UcInstDo;
import com.company.core.entity.UcInstDoExample;
import com.company.core.entity.UcInstInfoDo;
import com.company.core.entity.UcInstInfoDoExample;
import com.company.core.mapper.SeqMapper;
import com.company.core.mapper.UcInstDoMapper;
import com.company.core.mapper.UcInstInfoDoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service
@Slf4j
public class SequenceBiz {
    
    @Autowired
    SeqMapper seqMapper;
    
    public String genInstId() {
        String seq = String.valueOf(seqMapper.getSequenceNextVal("UBS_INST_SEQ"));
        return StringUtils.leftPad(seq, 6, "0");
    }
    
    public String genAgentId() {
        String seq = String.valueOf(seqMapper.getSequenceNextVal("UBS_AGENT_SEQ"));
        return StringUtils.leftPad(seq, 8, "0");
    }
    
    public String genRecomCodeBatchId() {
        String seq = String.valueOf(seqMapper.getSequenceNextVal("UBS_RECOM_CODE_BATCH"));
        return StringUtils.leftPad(seq, 8, "0");
    }
    
    public String genRecomCodeSeq() {
        String seq = String.valueOf(seqMapper.getSequenceNextVal("UBS_RECOM_CODE_SEQ"));
        return StringUtils.leftPad(seq, 8, "0");
    }
    
    public String genUserIdSeq() {
        return String.valueOf(seqMapper.getSequenceNextVal("TBL_BTS_SYS_USR_ID_SEQ"));
    }
    
}
