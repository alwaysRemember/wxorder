package com.thj.wxorder.enums;


import lombok.Getter;

@Getter
public enum ProductCategoryEnum {

    NO_PRODUCT_CATEGORY(0,"没有类目数据"),
    SAVE_PRODUCT_CATEGORY_ERROR(1,"添加类目失败"),
    PARAMS_ERROR(2,"类目数据传递出错"),
    SELECT_VALUE_NULL(3,"查找字段值为空"),
    DELETE_VALUE_NULL(4,"ID为空")
    ;

    private Integer code;
    private String msg;

    ProductCategoryEnum(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }
}
