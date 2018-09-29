package com.thj.wxorder.dataobject.mapper;

import com.thj.wxorder.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() throws Exception {

        Map<String,Object> map = new HashMap<>();

        map.put("category_name","男生最爱");
        map.put("category_type","5");
        int result = mapper.insertByMap(map);
    }

    @Test
    public void insertByObject() throws Exception{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(112);
        int result = mapper.insertByObject(productCategory);
    }

    @Test
    public void findByCategoryType() throws Exception{
        ProductCategory productCategory = mapper.findByCategoryType(112);

        log.info("分类，findByCategoryType={}",productCategory);
    }
}