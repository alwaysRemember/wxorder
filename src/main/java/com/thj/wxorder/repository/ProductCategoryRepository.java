package com.thj.wxorder.repository;

import com.thj.wxorder.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author yaer
 * 2018-07-01 11:28
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    List<ProductCategory> findByCategoryNameContains(String categoryName);

    ProductCategory findByCategoryId(Integer categoryId);

    void delete(Integer categoryId);

//    Integer deleteByCategoryId(Integer categoryId);
}
