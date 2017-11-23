package com.company.core.mapper;

import org.apache.ibatis.annotations.Param;

public interface SeqMapper {

    int getTblBTSSysUsrIdSeq();

    int getTblBTSSysRoleIdSeq();

    int getTblBTSSysFuncIdSeq();

    int getTblBTSInstIdSeq();
    
    Long getSequenceNextVal(@Param("seqName") String seqName);
    
}
