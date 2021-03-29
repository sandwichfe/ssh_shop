package com.lww.sevice.serviceImpl;

import com.lww.Dao.AdminUserDao;
import com.lww.sevice.AdminUserService;
import com.lww.vo.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {


    @Autowired
    AdminUserDao adminUserDao;

    @Override
    public AdminUser get(AdminUser adminUser) {
        return adminUserDao.get(adminUser);
    }
}
