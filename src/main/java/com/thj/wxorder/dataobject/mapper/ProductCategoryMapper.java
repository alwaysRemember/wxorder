package com.thj.wxorder.dataobject.mapper;

import com.thj.wxorder.dataobject.ProductCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/27 16:06
 **/

public interface ProductCategoryMapper {

    @Insert("insert into product_category(category_name,category_type) values (#{category_name, jdbcType=VARCHAR},#{category_type , jdbcType=INTEGER})")
    int insertByMap(Map<String,Object> map);
    @Insert("insert into product_category(category_name,category_type) values (#{categoryName, jdbcType=VARCHAR},#{categoryType , jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);

    @Select("select * from product_category where category_type = #{categoryType}")
    @Results({
            @Result(column = "category_type",property = "categoryType"),
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName")
    })
    ProductCategory findByCategoryType(Integer categoryType);
}
