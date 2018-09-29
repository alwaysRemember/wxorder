package com.thj.wxorder.repository;

import com.thj.wxorder.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {


    @Autowired
    private ProductInfoRepository repository;


    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123323123");
        productInfo.setCategoryType(2);
        productInfo.setProductDescription("注释");
        productInfo.setProductIcon("图标");
        productInfo.setProductName("名字1");
        productInfo.setProductPrice(BigDecimal.valueOf(20.00));
        productInfo.setProductStatus(1);
        productInfo.setProductStock(20);

        ProductInfo result = repository.save(productInfo);

        Assert.assertNotEquals(null,result);
    }

    @Test
    public void findOneTest(){
        List<ProductInfo> result = repository.findByProductStatus(1);

        Assert.assertNotEquals(0,result.size());
        System.out.println("数组:{}"+result);
    }




    @Test
    public void findByProductStatus() throws Exception {
    }
}