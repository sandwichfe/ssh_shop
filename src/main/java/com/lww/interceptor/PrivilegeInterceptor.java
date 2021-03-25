package com.lww.interceptor;

import com.lww.vo.AdminUser;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;


import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 权限拦截器:
 */
public class PrivilegeInterceptor extends MethodFilterInterceptor {

    @Override
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
        // 判断是否登录,如果登录,放行,没有登录,跳转到登录页面.   根据session是否存在
        AdminUser adminUser = (AdminUser) ServletActionContext.getRequest()
                .getSession().getAttribute("loginAdminUser");

        if (adminUser == null) {
            ActionSupport actionSupport= (ActionSupport) actionInvocation.getAction();
            actionSupport.addActionError( "请先登录！");
            return "loginFail";
        } else {
            return actionInvocation.invoke();
        }
    }

}
