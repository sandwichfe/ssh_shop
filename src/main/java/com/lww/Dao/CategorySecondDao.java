package com.lww.Dao;

import com.lww.utils.JDBCUtil.JdbcTemplate;
import com.lww.utils.JDBCUtil.handler.BeanHandler;
import com.lww.utils.JDBCUtil.handler.BeanListHandler;
import com.lww.vo.CategorySecond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategorySecondDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    
    public List<CategorySecond> findAll() {
        return jdbcTemplate.query("select * from categorysecond",new BeanListHandler<>(CategorySecond.class));
    }

    public void save(CategorySecond categorySecond) {
        jdbcTemplate.update("insert into categorysecond (csname,cid) values (?,?)",categorySecond.getCsname(),categorySecond.getCid());
    }

    public void delete(CategorySecond categorySecond) {

        //删除二级分类 一级对应关联的商品
        jdbcTemplate.update("delete from categorysecond where csid =?",categorySecond.getCsid());

        //删除二级分类 对应关联的商品
        jdbcTemplate.update("delete from product where csid =?",categorySecond.getCsid());

    }

    public CategorySecond getById(Integer csid) {
        return (CategorySecond) jdbcTemplate.query("select * from categorysecond where csid = ?",new BeanHandler<>(CategorySecond.class),csid);
    }

    public void update(CategorySecond categorySecond) {
        jdbcTemplate.update("update  categorysecond set csname = ? , cid = ? where csid =?",categorySecond.getCsname(),categorySecond.getCid(),categorySecond.getCsid());
    }
}
