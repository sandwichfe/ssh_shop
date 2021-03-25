package com.lww.action;


import com.lww.Dao.CategoryDao;
import com.lww.vo.Category;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台商品分类管理action
 */
public class AdminCategoryAction extends ActionSupport implements ModelDriven<Category> {

    //模型驱动
    private Category category = new Category();
    @Override
    public Category getModel() {
        return category;
    }


    @Autowired
    CategoryDao categoryDao;

    //查询所有一级分类
    public String findAll() {
        List<Category> list= categoryDao.findAll();
        //将集合数据放入值栈中
        ActionContext.getContext().getValueStack().set("cList",list);
        return "findAll";
    }



    //添加一级分类
    public String saveCategory(){
        categoryDao.save(category);
        return "saveSuccess";
    }


    //删除一级分类  同时对应关联的二级分类也要删除
    public String delete(){
        categoryDao.remove(category);
        return "deleteSuccess";
    }

    //编辑一级分类
    public String edit(){
        //根据传过来的cid 查询当前的category信息
        category=categoryDao.getById(category.getCid());
        return "edit";
    }

    //编辑一级分类
    public String update(){
        categoryDao.update(category);    //得到修改完成之后的新信息
        return "editSuccess";
    }
}
