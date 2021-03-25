package com.lww.sevice;

import com.lww.Enity.User;

public interface UserService {

    public User findUserByUserName(String userName);

    User login(User user);
}
