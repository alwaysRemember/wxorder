package com.thj.wxorder.service.impl;

import com.thj.wxorder.dataobject.ProductCategory;
import com.thj.wxorder.enums.ProductCategoryEnum;
import com.thj.wxorder.exception.WxorderException;
import com.thj.wxorder.repository.ProductCategoryRepository;
import com.thj.wxorder.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/20 10:35
 **/

@Service
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    // 添加类目
    @Override
    public ProductCategory save(ProductCategory productCategory) throws WxorderException {

        if ( productCategory == null ) {
            log.error(new Date() + "【添加类目】" + ProductCategoryEnum.NO_PRODUCT_CATEGORY.getMsg());
            throw new WxorderException(ProductCategoryEnum.NO_PRODUCT_CATEGORY);
        }
        ProductCategory result;
        try {
            result = productCategoryRepository.save(productCategory);
        } catch (Exception e) {
            throw new WxorderException(-1, "无法执行的操作");
        }
        if ( result == null ) {
            log.error(new Date() + "【添加类目】" + ProductCategoryEnum.SAVE_PRODUCT_CATEGORY_ERROR.getMsg());
            throw new WxorderException(ProductCategoryEnum.SAVE_PRODUCT_CATEGORY_ERROR);
        }
        return result;
    }


    // 更新类目
    @Override
    public ProductCategory update(ProductCategory productCategory) throws WxorderException {

        if ( productCategory == null ) {
            log.error(new Date() + "【更新类目】" + ProductCategoryEnum.NO_PRODUCT_CATEGORY.getMsg());
            throw new WxorderException(ProductCategoryEnum.NO_PRODUCT_CATEGORY);
        }

        ProductCategory result = productCategoryRepository.findOne(productCategory.getCategoryId());

        if ( result == null ) {
            log.error(new Date() + "【更新类目】" + ProductCategoryEnum.NO_PRODUCT_CATEGORY.getMsg());
            throw new WxorderException(ProductCategoryEnum.NO_PRODUCT_CATEGORY);
        }
        result = this.save(productCategory);

        return result;
    }

    // 类目列表
    @Override
    public Page<ProductCategory> categoryPage(Pageable pageable) throws WxorderException {
        return productCategoryRepository.findAll(pageable);
    }

    // 查询
    @Override
    public List<ProductCategory> findList(String categoryName) throws WxorderException {

        if ( categoryName.isEmpty() ) {
            log.error(new Date() + "【查找数据】" + ProductCategoryEnum.SELECT_VALUE_NULL.getMsg());
            throw new WxorderException(ProductCategoryEnum.SELECT_VALUE_NULL);
        }
        return productCategoryRepository.findByCategoryNameContains(categoryName);
    }

    // 删除类目
    @Override
    public ProductCategory deleteCategory(Integer categoryId) throws WxorderException {

        if ( categoryId == null ) {
            log.error(new Date() + "【删除类目】" + ProductCategoryEnum.DELETE_VALUE_NULL.getMsg());
            throw new WxorderException(ProductCategoryEnum.DELETE_VALUE_NULL);
        }
        ProductCategory productCategory = productCategoryRepository.findOne(categoryId);

        try {
            productCategoryRepository.delete(categoryId);
        }catch (Exception e){
            throw new WxorderException(-1,e.getMessage());
        }


        return productCategory;

    }
}
