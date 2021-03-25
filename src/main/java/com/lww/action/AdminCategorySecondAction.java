package com.lww.action;


import com.lww.Dao.CategoryDao;
import com.lww.Dao.CategorySecondDao;
import com.lww.utils.pageUtils.PageBean;
import com.lww.vo.Category;
import com.lww.vo.CategorySecond;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 后台商品二级分类管理action
 */
public class AdminCategorySecondAction extends ActionSupport implements ModelDriven<CategorySecond> {

    //模型驱动
    private CategorySecond categorySecond = new CategorySecond();
    @Override
    public CategorySecond getModel() {
        return categorySecond;
    }

    private int page;

    public void setPage(int page) {
        this.page = page;
    }

    private int cidUseForAdd;

    public void setCidUseForAdd(int cidUseForAdd) {
        this.cidUseForAdd = cidUseForAdd;
    }

    @Autowired
    CategorySecondDao categorySecondDao;

    @Autowired
    CategoryDao categoryDao;

    //查询所有二级分类
    public String findAll() {
        List<CategorySecond> list= categorySecondDao.findAll();
        PageBean<CategorySecond> categorySecondPageBean=new PageBean<>();
        categorySecondPageBean.setList(list);
        //将集合数据放入值栈中
        ActionContext.getContext().getValueStack().set("pageBean",categorySecondPageBean);
        return "findAll";
    }


    //添加二级分类
    public String addPage(){

        //查询出所有一级分类
        List<Category> categoryList=categoryDao.findAll();

        //放入值栈中 用于添加一级分类时 显示一个下拉框用于选择
        ActionContext.getContext().getValueStack().set("cList",categoryList);

        return "addPage";
    }

    //保存二级分类
    public String save(){
        categorySecond.setCid(cidUseForAdd);
        categorySecondDao.save(categorySecond);
        return "saveSuccess";
    }


    //删除二级分类   同时删除关联的商品
    public String delete(){
        categorySecondDao.delete(categorySecond);
        return "deleteSuccess";
    }

    //跳转视图 并且回显数据
    public String edit(){
        //根据传过来的cid 查询当前的category信息
        categorySecond=categorySecondDao.getById(categorySecond.getCsid());
        System.out.println(categorySecond);
        //查询所有一级分类数据 并放入到值栈中
        //查询出所有一级分类
        List<Category> categoryList=categoryDao.findAll();
        ActionContext.getContext().getValueStack().set("cList",categoryList);
        return "edit";
    }

    //编辑二级分类
    public String update(){
        categorySecond.setCid(cidUseForAdd);
       categorySecondDao.update(categorySecond);    //得到修改完成之后的新信息
        return "editSuccess";
    }
}
