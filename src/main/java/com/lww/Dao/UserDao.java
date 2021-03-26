package com.lww.Dao;

import com.lww.Enity.User;
import com.lww.utils.JDBCUtil.JdbcTemplate;
import com.lww.utils.JDBCUtil.handler.BeanHandler;
import com.lww.utils.JDBCUtil.handler.BeanListHandler;
import com.lww.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.alibaba.druid.sql.ast.SQLPartitionValue.Operator.List;

@Component
public class UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public User findUserByUserName(String userName) {
        return (User) jdbcTemplate.query("select * from user where username=?", new BeanHandler<>(User.class), userName);
    }


    public void save(User user) {
        user.setState(1);   //  0: 用户未激活 1：用户已激活
        String code = UUIDUtils.getUUID() + UUIDUtils.getUUID();   //64位
        user.setCode(code);
        jdbcTemplate.update("insert into user (username,password,name,email,phone,addr,state,code) values(?,?,?,?,?,?,?,?)",
                user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), user.getPhone(), user.getAddr(), user.getState(), user.getCode());
    }
    //查询用户
    public User get(User user) {
        java.util.List<User> list = jdbcTemplate.query("select * from user where username=? and password =? and state =1", new BeanListHandler<>(User.class),
                user.getUsername(), user.getPassword());
        if (list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
