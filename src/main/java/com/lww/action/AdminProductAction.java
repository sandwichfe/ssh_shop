package com.lww.action;

import com.lww.Dao.CategorySecondDao;
import com.lww.Dao.ProductDao;
import com.lww.utils.pageUtils.PageBean;
import com.lww.vo.Category;
import com.lww.vo.CategorySecond;
import com.lww.vo.Product;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import javafx.scene.effect.Effect;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class AdminProductAction extends ActionSupport implements ModelDriven<Product> {


    //模型。。驱动
    private Product product = new Product();

    @Override
    public Product getModel() {
        return product;
    }

    private int page;

    public void setPage(int page) {
        this.page = page;
    }

    // Struts 文件上传相关    提供set方法！         变量名要固定。。。。。。。。
    private File upload;   //要与表单中的name一致
    private String uploadFileName;  //接受文件的上传文件名  (上传的字段名+fileName)
    private String uploadContentType; //文件上传的MIME类型  (上传的字段名+contentType)

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    @Autowired
    ProductDao productDao;

    @Autowired
    CategorySecondDao categorySecondDao;

    public String findAll() {

        PageBean<Product> productPageBean = new PageBean<>();
        List<Product> products = productDao.findAll();
        productPageBean.setList(products);
        //放入值栈
        ActionContext.getContext().getValueStack().set("pageBean", productPageBean);
        return "findAll";
    }


    //添加商品
    public String addPage() {
        //查询出所有二级分类
        List<CategorySecond> categoryList = categorySecondDao.findAll();

        ActionContext.getContext().getValueStack().set("csList", categoryList);

        return "addPage";
    }

    //保存商品
    public String save() throws IOException {
        product.setPdate(new Date());
        //开始文件上传相关
        if (upload != null) {
            //获得文件的磁盘的绝对路径
            String realPath = ServletActionContext.getServletContext().getRealPath("/products");
            System.out.println(realPath);
            //创建一个文件
            File diskFile = new File(realPath + "//" + uploadFileName);
            //文件上传
            FileUtils.copyFile(upload, diskFile);
            System.out.println();
            //product属性路径设置进去
            product.setImage("products/" + uploadFileName);
        }
        productDao.save(product);
        return "saveSuccess";
    }

    public String delete() {
        //
        product = productDao.getById(product.getPid());
        //删除关联的图片
        String path = product.getImage();
        if (path != null) {
            String realPath = ServletActionContext.getServletContext().getRealPath("/" + path);
            File file = new File(realPath);
            file.delete();
        }
        productDao.delete(product);
        return "deleteSuccess";
    }


    //跳转视图 并且回显数据
    public String edit() {
        //根据传过来的cid 查询当前的category信息
        product = productDao.getById(product.getPid());
        //查询所有二级分类数据 并放入到值栈中
        List<CategorySecond> categoryList = categorySecondDao.findAll();
        ActionContext.getContext().getValueStack().set("csList", categoryList);
        return "edit";
    }

    //编辑二级分类
    public String update() throws IOException {

        product.setPdate(new Date());
        //开始文件上传相关

        //更改图片之前先删除原来的图片
        if (upload != null) {
            String path = product.getImage();
            if (path != null) {
                File file = new File(ServletActionContext.getServletContext().getRealPath("/") + path);
                file.delete();
            }
            //文件上传
            //获得文件的磁盘的绝对路径
            String realPath = ServletActionContext.getServletContext().getRealPath("/products");
            System.out.println(realPath);
            //创建一个文件
            File diskFile = new File(realPath + "//" + uploadFileName);
            //文件上传
            FileUtils.copyFile(upload, diskFile);
            System.out.println();
            //product属性路径设置进去
            product.setImage("products/" + uploadFileName);
        }
        productDao.update(product);
        return "editSuccess";
    }


}
