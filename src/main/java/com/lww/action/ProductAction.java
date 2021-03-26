package com.lww.action;

import com.lww.Dao.CategoryDao;
import com.lww.Dao.ProductDao;
import com.lww.utils.pageUtils.PageBean;
import com.lww.vo.Category;
import com.lww.vo.Product;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductAction extends ActionSupport implements ModelDriven<Product> {

    //使用模型驱动接收数据的自动封装
    private Product ParamProduct = new Product();     //他默认就是在值栈的栈顶

    @Override
    public Product getModel() {              //有一个getModel 就相当于这个action中有一个属性名为model
        return ParamProduct;
    }


    //用于接受参数cid
    private Integer cid;

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    //用于接受参数page
    private int page;

    public void setPage(int page) {
        this.page = page;
    }

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;


    //根据商品的Id查询商品信息
    public String findByPid() {

        //根据id查询得到商品

        //普通方式
        //Product product=productDao.get(ParamProduct.getPid() );
        //将product放入值栈。。。。。     这样重复了     因为模型驱动ParamProduct就是在值栈中的，还不如将值就设置到模型驱动中，反正都是product对象

        //方式二
        //因为模型驱动就是在栈顶的，在赋值之前ParamProduct里面只有pid有值，  此时赋值之后里面的属性全部有值，后面跳转jsp直接从它里面去取即可。
        ParamProduct = productDao.get(ParamProduct.getPid());
        return "findByPid";
    }


    //查询所有的一级分类信息（dao层已经将二级分类封装了进去）
    public String findByCid() {
        //其实在首页 此数据已经查了一次并且放到session里了，所以需要直接从session里面取即可
        //List<Category> list=categoryDao.findAll();
        return "findByCid";
    }


    //根据一级分类id查询商品
    public String getProductsByCid() {
        PageBean<Product> productPageBean = new PageBean<>();
        productPageBean.setPage(page);
        int limit = 8;
        productPageBean.setLimit(limit);
        int totalCount = 0;
        totalCount = productDao.findCountByCid(cid);
        productPageBean.setTotalCount(totalCount);
        int totalPage = 0;
        if (totalCount % limit == 0) {
            totalPage = totalCount / limit;
        } else {
            totalPage = totalCount / limit + 1;
        }
        //从哪条记录数开始
      int  begin=(page-1)*limit;
        productPageBean.setTotalPage(totalPage);
        List<Product> list=productDao.getProductsByCid(cid,begin,limit);
        productPageBean.setList(list);
        //将pageBean放入值栈中
        ActionContext.getContext().getValueStack().set("pageBean",productPageBean);
        System.out.println(productPageBean);

        ActionContext.getContext().getValueStack().setValue("cid",cid);
        return "findByCid";
    }

}
