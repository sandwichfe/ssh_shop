package com.lww.action;

import com.lww.Dao.CategoryDao;
import com.lww.Dao.ProductDao;
import com.lww.sevice.CategoryService;
import com.lww.sevice.ProductService;
import com.lww.vo.Category;
import com.lww.vo.Product;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class IndexAction extends ActionSupport {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Override
    public String execute() throws Exception {

        //查询所有首页商品一级分类的集合
        List<Category> list=categoryService.findAll();

        //将一级分类数据存入Session中
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        session.put("list",list);      //


        //查询首页热门商品集合
        List<Product> hList=productService.findHot();
        //将热门商品信息保存在值栈中
        ValueStack valueStack = context.getValueStack();
        valueStack.set("hList",hList);     //添加进值栈中    注意Set   与Set Value 的区别！！！！

        //查询最新商品
        List<Product> NList=productService.findNew();
        valueStack.set("NList",NList);     //添加进值栈中    注意Set   与Set Value 的区别！！！！
        return "index";
    }





}
