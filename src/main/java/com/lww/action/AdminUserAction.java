package com.lww.action;


import com.lww.Dao.AdminUserDao;
import com.lww.vo.AdminUser;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminUserAction extends ActionSupport implements ModelDriven<AdminUser> {

    //模型驱动     一定要实例化！！！！！！！
    private AdminUser adminUser =new AdminUser();
    @Override
    public AdminUser getModel() {
        return adminUser;
    }

    @Autowired
    AdminUserDao adminUserDao;

    public String login(){
        AdminUser loginAdminUser=adminUserDao.get(adminUser);
        if (loginAdminUser==null){
            this.addActionError("账号或密码错误！");
            return "login";
        }
        else {
            //登录成功，将用户放入session中
            ServletActionContext.getRequest().getSession().setAttribute("loginAdminUser",loginAdminUser);
            return "loginSuccess";
        }
    }
}
