package com.thj.wxorder.repository;

import com.thj.wxorder.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith (SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest () {
        ProductCategory productCategory = repository.findByCategoryId (1);
        System.out.println (productCategory.toString ());
    }

    @Test
    @Transactional  //在service里面表示方法有异常后回滚数据库插入的内容，测试里面表示执行完方法后回滚
    public void saveTest () {
        // 只能存在相同的一条数据
        ProductCategory productCategory = new ProductCategory ();
        productCategory.setCategoryName ("肉");
        productCategory.setCategoryType (4);
        ProductCategory result = repository.save (productCategory);
        // 用断言判断返回的数据不为null
        Assert.assertNotNull (result);
//        Assert.assertNotEquals (null,result);
    }

    @Test
    public void deleteTest () {
        Integer id = 1;
        ProductCategory productCategory = repository.findByCategoryId (id);
        if (productCategory != null) {
            repository.delete (id);
            System.out.println ("已删除：｛｝" + id);
        }
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList (4,5);

        List<ProductCategory> result= repository.findByCategoryTypeIn (list);
        Assert.assertNotEquals (0,result.size ());
        System.out.println ("List:{}"+result);
    }
}