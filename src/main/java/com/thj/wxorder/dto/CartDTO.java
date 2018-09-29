package com.thj.wxorder.dto;

import lombok.Data;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/9 9:54
 *
 * 购物车
 **/

@Data
public class CartDTO {

    // 商品ID
    private String productId;

    // 商品数量
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;

    }
}
