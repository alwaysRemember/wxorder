package com.thj.wxorder.service.impl;

import com.thj.wxorder.dataobject.ProductCategory;
import com.thj.wxorder.repository.ProductCategoryRepository;
import com.thj.wxorder.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author yaer
 * 2018-07-01 15:40
 *
 * product_category 接口方法
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne (Integer categoryId) {
        return repository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll () {
        return repository.findAll ();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn (List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn (categoryTypeList);
    }

    @Override
    public ProductCategory save (ProductCategory productCategory) {
        return repository.save (productCategory);
    }
}
