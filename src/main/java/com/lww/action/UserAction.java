package com.lww.action;

import com.lww.Dao.UserDao;
import com.lww.Enity.User;
import com.lww.sevice.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAction extends ActionSupport implements ModelDriven<User> {

    //使用模型驱动  ModelDriven 接收传过来的参数  自动封装进user对象
    private User user=new User();     //已经被struts将请求参数封装进去的user;
    @Override
    public User getModel() {
        return user;
    }

    @Autowired
    private UserService userService;

    /*用户注册的Action*/
    public String registPage() throws Exception {
        return "registPage";
    }


    /**
     * 根据用户名判断查询用户是否存在从而知道用户名时候存在
     *
     * @param
     * @return
     */
    public String findUserByName() throws IOException {
        if ("".equals(user.getName())||user.getName()==null){
            return NONE;
        }
        System.out.println(user);
        User queryUser = userService.findUserByUserName(user.getUsername());
        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhh"+queryUser);
            //声明一个response对象，用于输出页面
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=UTF-8");
            if (queryUser.getUsername() != null) {
                //该用户名已存在
                response.getWriter().println("<font color='red'> 用户名已存在</font>");
            } else {   //该用户名是空的
                response.getWriter().println("<font color='green'> 用户名可使用</font>");
            }
        return NONE;
    }

    @Autowired
    UserDao userDao;

    public String regist(){
        userDao.save(user);
        return "login";
    }

    public String login(){
        User loginUser=userService.login(user);            //模型驱动会自动封装
        if (loginUser==null){
            this.addActionError("账号或密码错误！");
            return "login";
        }
        else {
            //登录成功，将用户放入session中
            ServletActionContext.getRequest().getSession().setAttribute("loginUser",loginUser);
            return "loginSuccess";
        }
    }

    //用户退出
    public String quit(){
        //销毁session
        ServletActionContext.getRequest().getSession().invalidate();
        return "quit";
    }
}
