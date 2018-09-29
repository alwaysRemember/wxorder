package com.thj.wxorder.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Author yaer
 * 2018-07-01 11:22
 * sql_product_category
 */

@Entity
@DynamicUpdate
@Data   //使用此注解代替生成get、set、toString等方法
public class ProductCategory {

    //    类目id
    @Id
    @GeneratedValue
    private Integer categoryId;

    //    类目名字
    private String categoryName;

    //    类目编号
    private Integer categoryType;

}
