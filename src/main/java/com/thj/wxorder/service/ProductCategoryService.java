package com.thj.wxorder.service;

import com.thj.wxorder.dataobject.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/20 10:35
 **/

public interface ProductCategoryService {
    ProductCategory save(ProductCategory productCategory);

    ProductCategory update(ProductCategory productCategory);

    Page<ProductCategory> categoryPage(Pageable pageable);

    List<ProductCategory> findList(String categoryName);

    ProductCategory deleteCategory(Integer categoryId);
}
