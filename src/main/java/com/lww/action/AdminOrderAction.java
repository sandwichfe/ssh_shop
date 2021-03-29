package com.lww.action;

import com.lww.Dao.OrderDao;
import com.lww.sevice.OrderService;
import com.lww.utils.pageUtils.PageBean;
import com.lww.vo.Order;
import com.lww.vo.OrderItem;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminOrderAction extends ActionSupport implements ModelDriven<Order> {

    private Order order=new Order();
    @Override
    public Order getModel() {
        return order;
    }

    private int page;
    public void setPage(int page) {
        this.page = page;
    }
    private String time;

    public void setTime(String time) {
        this.time = time;
    }

    @Autowired
    OrderService orderService;

    public String findAll(){
        PageBean<Order> pageBean=new PageBean<>();
        List<Order> orders=orderService.findAll();
        pageBean.setList(orders);
        ActionContext.getContext().getValueStack().set("pageBean",pageBean);
        return "findAll";
    }

    public String findOrderItem(){
        List<OrderItem > list=orderService.getItemsByOid(order.getOid());
        ActionContext.getContext().getValueStack().set("list",list);
        return "findOrderItem";
    }

    //修改订单状态
    public String updateState(){
        Order currentOrder=orderService.getByOid(order.getOid());
        //修改订单状态
        currentOrder.setState(3);
        orderService.updateState(currentOrder);
        return "updateStateSuccess";
    }

}
