package com.company.core.service;

import com.company.core.domain.UserBO;

/**
 * Created by APPLE on 16/1/13.
 */
public interface UserAuthorService {
    UserBO getAuthorByUserId(String userId);
}
