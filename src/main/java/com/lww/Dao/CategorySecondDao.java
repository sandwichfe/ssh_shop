package com.lww.Dao;

import com.lww.utils.JDBCUtil.JdbcTemplate;
import com.lww.utils.JDBCUtil.handler.BeanHandler;
import com.lww.utils.JDBCUtil.handler.BeanListHandler;
import com.lww.vo.CategorySecond;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategorySecondDao {
    public List<CategorySecond> findAll() {
        return JdbcTemplate.query("select * from categorysecond",new BeanListHandler<>(CategorySecond.class));
    }

    public void save(CategorySecond categorySecond) {
        JdbcTemplate.update("insert into categorysecond (csname,cid) values (?,?)",categorySecond.getCsname(),categorySecond.getCid());
    }

    public void delete(CategorySecond categorySecond) {

        //删除二级分类 一级对应关联的商品
        JdbcTemplate.update("delete from categorysecond where csid =?",categorySecond.getCsid());

        //删除二级分类 对应关联的商品
        JdbcTemplate.update("delete from product where csid =?",categorySecond.getCsid());

    }

    public CategorySecond getById(Integer csid) {
        return (CategorySecond) JdbcTemplate.query("select * from categorysecond where csid = ?",new BeanHandler<>(CategorySecond.class),csid);
    }

    public void update(CategorySecond categorySecond) {
        JdbcTemplate.update("update  categorysecond set csname = ? , cid = ? where csid =?",categorySecond.getCsname(),categorySecond.getCid(),categorySecond.getCsid());
    }
}
