package com.thj.wxorder.service.impl;

import com.thj.wxorder.backstage.ProductInfoBackstage;
import com.thj.wxorder.dataobject.ProductInfo;
import com.thj.wxorder.enums.ProductEnum;
import com.thj.wxorder.exception.WxorderException;
import com.thj.wxorder.service.ProductBackstageService;
import com.thj.wxorder.service.ProductService;
import com.thj.wxorder.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/16 13:52
 **/
@Slf4j
@Service
public class ProductBackstageImpl implements ProductBackstageService {

    @Autowired
    private ProductService productService;

    // 添加商品
    @Override
    public ProductInfo addProduct(ProductInfoBackstage productInfoBackstage) throws WxorderException {
        String productId = KeyUtil.genUniqueKey();
        if ( productInfoBackstage == null ) {
            log.error(new Date() + "【添加商品】" + ProductEnum.NO_PRODUCT.getMsg());
            throw new WxorderException(ProductEnum.NO_PRODUCT);
        }
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productInfoBackstage, productInfo);

        productInfo.setProductId(productId);

        return productService.save(productInfo);
    }

    // 更新商品
    @Override
    public ProductInfo updateProduct(ProductInfo productInfo) {

        if ( productInfo == null ) {
            log.error(new Date() + "【更新商品】" + ProductEnum.NO_PRODUCT.getMsg());
            throw new WxorderException(ProductEnum.NO_PRODUCT);
        }
        return productService.save(productInfo);
    }


}
