package com.lww.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 后台用户登录实体
 */
@Getter
@Setter
public class AdminUser {
    private Integer uid;
    private String username;
    private String password;
}
