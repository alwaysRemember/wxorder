package com.thj.wxorder.backstage;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/16 13:53
 **/
@Data
public class ProductInfoBackstage {

    // 名字
    private String productName;

    // 单价
    private BigDecimal productPrice;

    // 库存
    private Integer productStock;

    // 描述
    private String productDescription;

    // 小图
    private String productIcon;

    // 商品状态 0 正常 1 下架
    private Integer productStatus;

    // 类目编号
    private Integer categoryType;
}
