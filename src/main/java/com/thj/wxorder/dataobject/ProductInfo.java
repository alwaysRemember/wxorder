package com.thj.wxorder.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/2 13:43
 * 商品
 **/
@Entity
@Data
public class ProductInfo {

    @Id
    private String productId;

    // 名字
    private String productName;

    // 单价
    private BigDecimal productPrice;

    // 库存
    private Integer productStock;

    // 描述
    private String productDescription;

    // 小图
    protected String productIcon;


    // 商品状态 0 正常 1 下架
    private Integer productStatus;

    // 类目编号
    private Integer categoryType;

}
