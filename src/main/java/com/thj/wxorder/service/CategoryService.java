package com.thj.wxorder.service;

import com.thj.wxorder.dataobject.ProductCategory;

import java.util.List;

/**
 * Author yaer
 * 2018-07-01 15:36
 *
 * product_category 接口
 */
public interface CategoryService {


    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);

}
