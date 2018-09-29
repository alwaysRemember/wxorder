package com.thj.wxorder.service.impl;

import com.thj.wxorder.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;


@RunWith (SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne () throws Exception {
        ProductCategory productCategory = categoryService.findOne (7);
        Assert.assertEquals (new Integer (7),productCategory.getCategoryId ());
    }

    @Test
    public void findAll () throws Exception  {
        List<ProductCategory> resultList = categoryService.findAll ();
        Assert.assertNotEquals (0,resultList.size ());

    }

    @Test
    public void findByCategoryTypeIn () throws Exception  {
        List<ProductCategory> resultList = categoryService.findByCategoryTypeIn (Arrays.asList (4,5));
        Assert.assertNotEquals (0,resultList.size ());
    }

    @Test
    public void save () throws Exception  {
        ProductCategory productCategory = new ProductCategory ();
        productCategory.setCategoryType (10);
        productCategory.setCategoryName ("百香果");
        ProductCategory result = categoryService.save (productCategory);
        Assert.assertNotEquals (null,result);

    }
}