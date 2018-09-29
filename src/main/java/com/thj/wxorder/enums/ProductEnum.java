package com.thj.wxorder.enums;

import lombok.Getter;

@Getter
public enum ProductEnum {
    NO_PRODUCTID(0,"商品ID未传递"),
    NO_PRODUCT(1,"没有此商品"),
    IS_ON_SHELF(2,"此商品已经上架"),
    is_DOWN_SHELF(4,"此商品已经下架"),
    MODIFY_STATUS_ERROR(3,"修改商品状态失败"),
    DELETE_PRODUCT_ERROR(5,"删除商品失败"),
    ;

    private Integer code;
    private String msg;


    ProductEnum(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }
}
