package com.lww.action;

import com.lww.Enity.User;
import com.lww.TestUser;
import com.lww.sevice.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class TestAction extends ActionSupport {

    //设置返回Json格式的product
    private Map<String,Object> dataMap;
    public Map<String, Object> getDataMap() {
        return dataMap;
    }
    //设置key属性不作为json的内容返回
    @JSON(serialize=false)
    public String getKey() {
        return key;
    }

    private String key = "Just see see";
    @Autowired
    UserService userService;

    @Override
    public String execute() throws Exception {
        // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
        dataMap = new HashMap<String, Object>();
        TestUser user = new TestUser();
        user.setUserName("张三");
        user.setPwd("123");
        dataMap.put("user", user);
        // 放入一个是否操作成功的标识
        dataMap.put("success", true);
        // 返回结果
        return Action.SUCCESS;
    }



}
