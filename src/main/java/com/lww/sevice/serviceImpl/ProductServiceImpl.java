package com.lww.sevice.serviceImpl;

import com.lww.Dao.ProductDao;
import com.lww.sevice.ProductService;
import com.lww.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    ProductDao productDao;


    @Override
    public List<Product> findHot() {
        return productDao.findHot();
    }

    @Override
    public List<Product> findNew() {
        return productDao.findNew();
    }

    @Override
    public Product get(Integer pid) {
        return productDao.get(pid);
    }

    @Override
    public int findCountByCid(Integer cid) {
        return productDao.findCountByCid(cid);
    }

    @Override
    public List<Product> getProductsByCid(Integer cid, int begin, int limit) {
        return productDao.getProductsByCid(cid,begin,limit);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public void save(Product product) {
            productDao.save(product);
    }

    @Override
    public void delete(Product product) {
        productDao.delete(product);
    }

    @Override
    public Product getById(Integer pid) {
        return productDao.getById(pid);
    }

    @Override
    public void update(Product product) {
            productDao.update(product);
    }

    @Override
    public int findCountByCsid(Integer csid) {
        return productDao.findCountByCsid(csid);
    }

    @Override
    public List<Product> getProductsByCsid(Integer cid, int begin, int limit) {
        return productDao.getProductsByCsid(cid,begin,limit);
    }
}
