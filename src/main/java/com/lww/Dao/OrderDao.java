package com.lww.Dao;

import com.lww.Enity.User;
import com.lww.utils.JDBCUtil.JdbcTemplate;
import com.lww.utils.JDBCUtil.handler.BeanHandler;
import com.lww.utils.JDBCUtil.handler.BeanListHandler;
import com.lww.utils.pageUtils.PageBean;
import com.lww.vo.Order;
import com.lww.vo.OrderItem;
import com.lww.vo.Product;
import jdk.nashorn.internal.scripts.JD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class OrderDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserDao userDao;
    public void save(Order order) {
        User user = order.getUser();
        //得到自动生成的主键oid
        Integer generatorId = jdbcTemplate.updateAndGetKey("insert into orders (total,ordertime,state,uid,name,phone,addr) values (?,?,?,?,?,?,?)", order.getTotal(), order.getOrdertime(), order.getState(),
                user.getUid(), user.getName(), user.getPhone(), user.getAddr());

        System.out.println(generatorId);

        Set<OrderItem> orderItems = order.getOrderItems();
        //把关联的OrderItems 也存进数据库
        for (OrderItem item : orderItems) {
            item.setOid(generatorId);
            jdbcTemplate.update("insert into orderitem (count,subtotal,pid,oid) values(?,?,?,?)", item.getCount(), item.getSubtotal(), item.getPid(), item.getOid());
        }
        // System.out.println(generatorId);
    }

    public List<Order> getOrdersByUid(Integer uid) {

        List<Order> list = (List<Order>) jdbcTemplate.query("select * from orders where uid = ?", new BeanListHandler<>(Order.class), uid);

        //查询下面的子orderItem

        //先一次性查出所有的orderItems
        List<OrderItem> orderItems = jdbcTemplate.query("select * from orderitem", new BeanListHandler<>(OrderItem.class));
        System.out.println(orderItems);
        //查orderItem里面products。。。。。
        //封装：
        List<Product> products = jdbcTemplate.query("select * from product", new BeanListHandler<>(Product.class));
        for (OrderItem orderItem : orderItems) {
            for (int p = 0; p < products.size(); p++) {
                if (orderItem.getPid() == products.get(p).getPid()) {
                    orderItem.setProduct(products.get(p));
                }
            }
        }
        //进行封装
        for (int i = 0; i < list.size(); i++) {
            Set<OrderItem> set = new HashSet<>();
            Order order = list.get(i);
            for (int j = 0; j < orderItems.size(); j++) {
                OrderItem orderItem = orderItems.get(j);
               // System.out.println("o:" + order.getOid() + "oi:" + orderItem.getOid());
                String o = String.valueOf(order.getOid());
                String oi = String.valueOf(orderItem.getOid());
                if (o.equals(oi)) {
                    set.add(orderItems.get(j));
                }
            }
            order.setOrderItems(set);
        }
        //System.out.println(list);
        return list;
    }

    public List<Order> findAll() {

        return jdbcTemplate.query("select * from orders",new BeanListHandler<>(Order.class));
    }

    public List<OrderItem> getItemsByOid(Integer Oid){
        List<OrderItem> orderItems=jdbcTemplate.query("select * from orderitem where oid = ?",new BeanListHandler<>(OrderItem.class),Oid);

        //查orderItem里面products。。。。。
        //封装：
        List<Product> products = jdbcTemplate.query("select * from product", new BeanListHandler<>(Product.class));
        for (OrderItem orderItem : orderItems) {
            for (int p = 0; p < products.size(); p++) {
                if (orderItem.getPid() == products.get(p).getPid()) {
                    orderItem.setProduct(products.get(p));
                }
            }
        }
        return  orderItems;

    }

    public Order getByOid(Integer Oid) {
        return (Order) jdbcTemplate.query("select * from orders where oid = ?", new BeanHandler<>(Order.class), Oid);
    }

    public void updateState(Order currentOrder) {
        jdbcTemplate.update("update orders set state = ? where oid = ?",
               currentOrder.getState(),currentOrder.getOid());
    }
}