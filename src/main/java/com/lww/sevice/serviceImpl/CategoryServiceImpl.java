package com.lww.sevice.serviceImpl;

import com.lww.Dao.CategoryDao;
import com.lww.sevice.CategoryService;
import com.lww.vo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public void save(Category category) {
            categoryDao.save(category);
    }

    @Override
    public void remove(Category category) {
            categoryDao.remove(category);
    }

    @Override
    public void update(Category category) {
            categoryDao.update(category);
    }

    @Override
    public Category getById(Integer cid) {
        return categoryDao.getById(cid);
    }
}
