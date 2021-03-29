package com.lww.sevice;

import com.lww.vo.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    void save(Category category);

    void remove(Category category);

    void update(Category category);


    Category getById(Integer cid);
}
