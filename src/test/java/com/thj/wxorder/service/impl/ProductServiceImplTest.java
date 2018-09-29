package com.thj.wxorder.service.impl;

import com.thj.wxorder.dataobject.ProductInfo;
import com.thj.wxorder.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {


    @Autowired
    protected ProductServiceImpl productService;


    @Test
    public void findOne() {

        ProductInfo result = productService.findOne("123123123");

        Assert.assertEquals("123123123", result.getProductId());

    }

    @Test
    public void findUpAll() {

        List<ProductInfo> resultList = productService.findUpAll();
        Assert.assertNotEquals(0, resultList.size());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0, 2);

        Page<ProductInfo> resultList = productService.findAll(pageRequest);
        Assert.assertNotEquals(0,resultList.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();

        productInfo.setProductId("12223323123");
        productInfo.setCategoryType(2);
        productInfo.setProductDescription("注释");
        productInfo.setProductIcon("图标");
        productInfo.setProductName("名字4");
        productInfo.setProductPrice(new BigDecimal(20));
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setProductStock(20);

        ProductInfo result = productService.save(productInfo);

        Assert.assertNotEquals(null,result);
    }
}