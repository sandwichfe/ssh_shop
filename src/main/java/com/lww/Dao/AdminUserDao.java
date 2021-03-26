package com.lww.Dao;

import com.lww.utils.JDBCUtil.JdbcTemplate;
import com.lww.utils.JDBCUtil.handler.BeanListHandler;
import com.lww.vo.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AdminUserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public AdminUser get(AdminUser adminUser) {
        List<AdminUser> list=jdbcTemplate.query("select * from adminuser where username= ? and password =?",
                new BeanListHandler<>(AdminUser.class),adminUser.getUsername(),adminUser.getPassword());
            if (list!=null&&list.size()>0){
                return list.get(0);
            }
            return null;
    }
}
