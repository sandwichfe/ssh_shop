package com.lww.Dao;

import com.lww.utils.JDBCUtil.JdbcTemplate;
import com.lww.utils.JDBCUtil.handler.BeanHandler;
import com.lww.utils.JDBCUtil.handler.BeanListHandler;
import com.lww.vo.Category;
import com.lww.vo.CategorySecond;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryDao {


    public List<Category> findAll() {

        //此时查出的是基本的一级分类list  每个一级分类下的二级分类还没有查出来封装进去
        List<Category> list = JdbcTemplate.query("select * from category", new BeanListHandler<>(Category.class));
        //开始将他们进行封装
        //先查出所有的二级分类数据
        List<CategorySecond> secondList=JdbcTemplate.query("select * from categorysecond",new BeanListHandler<>(CategorySecond.class));

        //根据cid关联将一级分类对应数据封装进去

        //遍历一级分类
        for (int i=0;i<list.size();i++){
            //用于存放单前遍历的一级分类对应的所以二级分类的集合
            List<CategorySecond> categorySecondsCacheList=new ArrayList<>();
            //当前遍历的一级分类对象
            Category category=list.get(i);
            Integer cid=category.getCid();   //当前一级分类对象的cid
            //开始遍历所有二级分类数据，寻找属于此cid的二级分类对象，  也就是此一级分类下的二级分类对象了。
            for (int j=0;j<secondList.size();j++){
                CategorySecond categorySecond=secondList.get(j);
                if(categorySecond.getCid()==cid){
                categorySecondsCacheList.add(categorySecond);
                }
            }
            //将此一级分类对应下的所有二级分类集合设置进去
            //System.out.println("cid:"+cid+"  list："+categorySecondsCacheList);
            category.setList(categorySecondsCacheList);
        }
        //System.out.println("all:"+list);
        return list;
    }

    public void save(Category category) {
        JdbcTemplate.update("insert into category (cname) values (?)",category.getCname());
    }


    //删除一级分类  同时也要删除对应的二级分类
    public void remove(Category category) {
        JdbcTemplate.update("delete from category where cid =?",category.getCid());

        //删除一级分类对应的二级分类
        JdbcTemplate.update("delete from categorysecond where cid =?",category.getCid());
    }


    public void update(Category category) {
        JdbcTemplate.update("update  category set cname = ? where cid =?",category.getCname(),category.getCid());
        //return (Category) JdbcTemplate.query("select * from category where cid=?",new BeanHandler<>(Category.class),category.getCid());
    }

    public Category getById(Integer cid) {
        return (Category) JdbcTemplate.query("select * from category where cid=?",new BeanHandler<>(Category.class),cid);
    }
}
