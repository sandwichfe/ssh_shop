package com.lww.sevice;

import com.lww.vo.CategorySecond;

import java.util.List;

public interface CategorySecondService {

     List<CategorySecond> findAll();

     void save(CategorySecond categorySecond);

     void delete(CategorySecond categorySecond);

     CategorySecond getById(Integer csid);

    void update(CategorySecond categorySecond);
}
