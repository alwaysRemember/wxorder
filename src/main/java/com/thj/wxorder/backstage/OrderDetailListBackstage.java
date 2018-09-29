package com.thj.wxorder.backstage;

import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/9 14:28
 **/

@Data
public class OrderDetailListBackstage {

    @Id
    private String detailId;

    // 订单id
    private String orderId;

    // 商品名字
    private String productName;

    // 商品价格
    private BigDecimal productPrice;

    // 商品数量
    private Integer productQuantity;

    // 商品总额
    private BigDecimal productAmount;
}
