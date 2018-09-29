package com.thj.wxorder.service;

import com.thj.wxorder.backstage.ProductInfoBackstage;
import com.thj.wxorder.dataobject.ProductInfo;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/16 13:52
 **/
public interface ProductBackstageService {
    ProductInfo addProduct(ProductInfoBackstage productInfoBackstage);

    ProductInfo updateProduct(ProductInfo productInfo);
}
