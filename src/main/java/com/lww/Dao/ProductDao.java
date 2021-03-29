package com.lww.Dao;

import com.lww.utils.JDBCUtil.JdbcTemplate;
import com.lww.utils.JDBCUtil.handler.BeanHandler;
import com.lww.utils.JDBCUtil.handler.BeanListHandler;
import com.lww.utils.JDBCUtil.handler.IResultSetHandler;
import com.lww.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class ProductDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Product> findHot(){
        return jdbcTemplate.query("select * from product where is_hot = ? order by pdate desc limit 0,10", new BeanListHandler<>(Product.class),1);
    }

    public List<Product> findNew() {
        return jdbcTemplate.query("select * from product order by pdate desc limit 0,10", new BeanListHandler<>(Product.class));
    }

    public Product get(Integer pid) {
        return (Product) jdbcTemplate.query("select * from product where pid = ?",new BeanHandler<>(Product.class),pid);
    }
    public int findCountByCid(Integer cid) {
        return jdbcTemplate.query("select count(*) from categorysecond cs\n" +
                "\t\t\t\t\t\t\t\t,category c\n" +
                "\t\t\t\t\t\t\t\t,product p\n" +
                "\t\t\t\t\t\t\t\twhere p.csid= cs.csid and cs. cid = c.cid and c.cid= ?", new IResultSetHandler<Integer>() {
            @Override
            public Integer handle(ResultSet rs) throws Exception {
                if (rs.next()){
                    //System.out.println("rows:-----------------------"+rs.getInt(1));   查询条数结果集肯定只有一条结果啊  取第一条结果即可。。。。。。
                    return rs.getInt(1);     //得到最后一行的行数并返回
                }
                //结果集为空就返回o条
                return 0;
            }
        },cid);
    }

    public List<Product> getProductsByCid(Integer cid, int begin, int limit) {
        return jdbcTemplate.query("select * from categorysecond cs\n" +
                "\t\t\t\t\t\t\t\t,category c\n" +
                "\t\t\t\t\t\t\t\t,product p\n" +
                "\t\t\t\t\t\t\t\twhere p.csid= cs.csid and cs. cid = c.cid and c.cid = ? limit ? , ?",new BeanListHandler<>(Product.class),cid,begin,limit);
    }

    public List<Product> findAll() {
        return jdbcTemplate.query("select * from product", new BeanListHandler<>(Product.class));
    }

    public void save(Product product) {
        jdbcTemplate.update("insert into product (pname,market_price,shop_price,image,pdesc,is_hot,pdate,csid) values (?,?,?,?,?,?,?,?)",
                product.getPname(),product.getMarket_price(),product.getShop_price(),product.getImage(),product.getPdesc(),product.getIs_hot(),
                product.getPdate(),product.getCsid());
    }

    public void delete(Product product) {
        jdbcTemplate.update("delete from product where pid =?",product.getPid());
    }

    public Product getById(Integer pid) {
        return (Product) jdbcTemplate.query("select * from product where pid = ?", new BeanHandler<>(Product.class),pid);
    }

    public void update(Product product) {
        jdbcTemplate.update("update product set pname  = ? ,market_price  = ?,shop_price  = ?,image  = ?,pdesc  = ?,is_hot  = ?,pdate  = ?,csid  = ? where pid = ?",
                product.getPname(),product.getMarket_price(),product.getShop_price(),product.getImage(),product.getPdesc(),product.getIs_hot(),
                product.getPdate(),product.getCsid(),product.getPid());
    }

    public int findCountByCsid(Integer csid) {
        return jdbcTemplate.query("select count(*) from categorysecond cs\n" +
                "\t\t\t\t\t\t\t\t,category c\n" +
                "\t\t\t\t\t\t\t\t,product p\n" +
                "\t\t\t\t\t\t\t\twhere p.csid= cs.csid and cs. cid = c.cid and cs.csid= ?", new IResultSetHandler<Integer>() {
            @Override
            public Integer handle(ResultSet rs) throws Exception {
                if (rs.next()){
                    //System.out.println("rows:-----------------------"+rs.getInt(1));   查询条数结果集肯定只有一条结果啊  取第一条结果即可。。。。。。
                    return rs.getInt(1);     //得到最后一行的行数并返回
                }
                //结果集为空就返回o条
                return 0;
            }
        },csid);
    }

    public List<Product> getProductsByCsid(Integer cid, int begin, int limit) {
        return jdbcTemplate.query("select * from categorysecond cs\n" +
                "\t\t\t\t\t\t\t\t,category c\n" +
                "\t\t\t\t\t\t\t\t,product p\n" +
                "\t\t\t\t\t\t\t\twhere p.csid= cs.csid and cs. cid = c.cid and cs.csid = ? limit ? , ?",new BeanListHandler<>(Product.class),cid,begin,limit);
    }
}
