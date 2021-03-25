package com.lww;


import com.lww.Dao.OrderDao;
import com.lww.Enity.User;
import com.lww.vo.Order;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class test {

    @Test
    public void test1() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application.xml");
    }


    @Test
    public void test2() {
        System.out.println("测试log4j");
        Log log = LogFactory.getLog(getClass());
        log.debug("debug级别日志");
        log.warn("warn级别日志");
        log.error("error级别日志");
    }



    @Test
    public void test3() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application.xml");
        OrderDao orderDao=ctx.getBean(OrderDao.class);
        Order order = new Order();
        User user = new User();
        user.setUid(7);
        user.setName("fd");
        order.setUser(user);
        orderDao.save(order);
    }

}
