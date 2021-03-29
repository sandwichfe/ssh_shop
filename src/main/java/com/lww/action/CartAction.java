package com.lww.action;

import com.lww.Dao.ProductDao;
import com.lww.sevice.ProductService;
import com.lww.vo.Cart;
import com.lww.vo.CartItem;
import com.lww.vo.Product;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Validateable;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 购物车Action
 */
public class CartAction extends ActionSupport {

    @Autowired
    private ProductService productService;

    //用于接受商品pid  以及购买数量
    private Integer pid;
    private Integer count;

    //提供set方法
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    //将商品添加到购物车执行的方法
    public String addCart() {

        //一个购物项对象
        CartItem cartItem = new CartItem();

        Product product = productService.get(pid);
        cartItem.setProduct(product);
        cartItem.setCount(count);

        //一个购物车对象
        Cart cart = getCart();
        //将购物项添加到购物车中
        cart.addCart(cartItem);

        return "addCart";
    }

    //获取购物车信息的方法  从session中获取
    private Cart getCart() {
        Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
        //如果第一次不存在就创建一个放到session中去
        if (cart == null) {
            cart = new Cart();
            ServletActionContext.getRequest().getSession().setAttribute("cart", cart);   //这个样子存放的好像是cart对象的地址，cart变了session里面也会跟着变
        }
        return cart;
    }

    //清空购物车
    public String clearCart(){
        //得到购物车对象
        Cart cart=getCart();
        cart.clearCart();
        return "clearCart";
    }

    //从购物车移除某一项
    public String removeItemInCart(){

        Cart cart=getCart();
        cart.removeCart(pid);
        return "removeItemInCart";
    }

    public String myCart(){
        return "myCart";
    }

}
