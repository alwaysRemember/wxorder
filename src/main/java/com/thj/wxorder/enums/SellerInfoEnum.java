package com.thj.wxorder.enums;


import lombok.Getter;

@Getter
public enum SellerInfoEnum {
    OPENID_IS_NULL(0,"openid为空"),
    NO_USER_INFO(1,"登录失败，登录信息不正确"),
    NO_LOGIN(2,"未登录"),
    ;

    private Integer code;
    private String msg;

    SellerInfoEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
