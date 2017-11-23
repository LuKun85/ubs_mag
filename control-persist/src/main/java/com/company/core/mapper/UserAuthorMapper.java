package com.company.core.mapper;

import com.company.core.entity.TblBTSSysUsrVO;

/**
 * Created by fireWorks on 2016/2/26.
 */
public interface UserAuthorMapper {
    TblBTSSysUsrVO getAuthorByUserId(String userId);
}
