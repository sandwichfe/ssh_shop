package com.lww.sevice.serviceImpl;

import com.lww.Dao.UserDao;
import com.lww.Enity.User;
import com.lww.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public User findUserByUserName(String userName) {
        User user=userDao.findUserByUserName(userName);
        return user;
    }

    @Override
    public User login(User user) {
        return userDao.get(user);
    }
}
