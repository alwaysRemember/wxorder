package com.thj.wxorder.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/3 16:36
 **/

@Entity
@Data
public class OrderDetail {

    @Id
    private String detailId;

    // 订单ID
    private String orderId;

    // 商品ID
    private String productId;

    // 商品名字
    private String productName;

    // 商品价格
    private BigDecimal productPrice;

    // 商品数量
    private Integer productQuantity;

    // 商品图片
    private String productIcon;






}

