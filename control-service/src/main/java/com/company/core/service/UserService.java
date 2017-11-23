package com.company.core.service;

import java.util.Map;

import com.company.core.domain.UserBO;
import com.company.core.entity.TblBTSSysUsrDO;
import com.company.core.form.Pagination;

public interface UserService {
    UserBO get(String username);

    UserBO getById(String username);

    Map updatePwd(UserBO userBO);

    Map addNewUsr(UserBO userBO);

    Map setAcctEnable(String usrId) throws Exception;

    Map setAcctDisable(String usrId) throws Exception;

    Pagination<UserBO> getAllUsr(UserBO userBO);

    Pagination<UserBO> getTheUsr(UserBO userBO);

    Map cancelAcctAuthority(String id, String uid) throws Exception;

    Map addAcctAuthority(String id, String uid);

    TblBTSSysUsrDO getIdByName(String usr_name);
}
