package com.perfect.service.impl;

import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.SystemUserEntity;
import com.perfect.service.SystemUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Resource
    private SystemUserDAO systemUserDAO;

    @Override
    public SystemUserEntity getSystemUser(String userName) {
        return systemUserDAO.findByUserName(userName);
    }
}
