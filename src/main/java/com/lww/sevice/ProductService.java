package com.lww.sevice;

import com.lww.vo.Product;

import java.util.List;

public interface ProductService {

    List<Product> findHot();

    List<Product> findNew();

    Product get(Integer pid);

    int findCountByCid(Integer cid);

    List<Product> getProductsByCid(Integer cid, int begin, int limit);

    List<Product> findAll();

    void save(Product product);

    void delete(Product product);

    Product getById(Integer pid);

    void update(Product product);

    int findCountByCsid(Integer csid);

    List<Product> getProductsByCsid(Integer cid, int begin, int limit);
}
