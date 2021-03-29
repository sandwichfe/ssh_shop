package com.lww.sevice.serviceImpl;

import com.lww.Dao.CategorySecondDao;
import com.lww.sevice.CategorySecondService;
import com.lww.sevice.CategoryService;
import com.lww.vo.CategorySecond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorySecondServiceImpl implements CategorySecondService {

    @Autowired
    CategorySecondDao categorySecondDao;

    @Override
    public List<CategorySecond> findAll() {
        return categorySecondDao.findAll();
    }

    @Override
    public void save(CategorySecond categorySecond) {
            categorySecondDao.save(categorySecond);
    }

    @Override
    public void delete(CategorySecond categorySecond) {
        categorySecondDao.delete(categorySecond);
    }

    @Override
    public CategorySecond getById(Integer csid) {
        return categorySecondDao.getById(csid);
    }

    @Override
    public void update(CategorySecond categorySecond) {
        categorySecondDao.update(categorySecond);
    }
}
