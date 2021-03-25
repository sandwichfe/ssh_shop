package com.lww.action;

import com.lww.Dao.OrderDao;
import com.lww.Enity.User;
import com.lww.utils.pageUtils.PageBean;
import com.lww.vo.Cart;
import com.lww.vo.CartItem;
import com.lww.vo.Order;
import com.lww.vo.OrderItem;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class OrderAction extends ActionSupport implements ModelDriven<Order> {

    //实现模型驱动  数据封装
    private  Order order=new Order();               //因为模型驱动就保存在值栈中  所以当jsp需要时可以直接去取
    @Override
    public Order getModel() {
        return order;
    }

    @Autowired
    private OrderDao orderDao;

    //接受page参数
    private  Integer page;

    public void setPage(Integer page) {
        this.page = page;
    }

    //生成订单
    public String saveOrder(){

        //1.保存数据到数据库

        //1.订单数据补全
        order.setOrdertime(new Date());
        order.setState(1);   // 状态： 1:已付款 未发货 2.已发货 3.发货成功未确认收货 4.交易成功

        //得到此时购物车中的总计                        从session里面取得购物车对象
        Cart cart= (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
        if (cart==null){
            return NONE;
        }

        order.setTotal(cart.getTotal());

        //设置订单中的每项购物项
        for (CartItem cartItem: cart.getCartItems()) {
            OrderItem item=new OrderItem();
            item.setProduct(cartItem.getProduct());
            item.setCount(cartItem.getCount());
            item.setPid(cartItem.getProduct().getPid());
            item.setSubtotal(cartItem.getSubtotal());
            //将item依次添加进order里面orderItems
            order.getOrderItems().add(item);
        }
        User loginUser= (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
        if (loginUser==null){
            this.addActionError("购买前请先登录哦！");
            return "login";
        }
        order.setUser(loginUser);
        orderDao.save(order);


        //提交订单后 清空购物车
        Cart c=(Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
        c.clearCart();
        return "saveSuccess";
    }



    //通过用户id 查询订单
    public String findByUid(){
        User user= (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
        List<Order> list=orderDao.getOrdersByUid(user.getUid());
        PageBean<Order> pageBean=new PageBean<>();
        pageBean.setList(list);
        //存入到值栈中
        ActionContext.getContext().getValueStack().set("pageBean",pageBean);
        return "findSuccess";
    }

    //修改订单状态  确认收货
    public String updateState(){
        Order currentOrder=orderDao.getByOid(order.getOid());
        //修改订单状态
        currentOrder.setState(4);
        orderDao.updateState(currentOrder);
        return "updateStateSuccess";
    }
}
