package com.thj.wxorder.enums;

import lombok.Getter;

@Getter
public enum PayTypeEnum {

    WX_Pay_TYPE(0,"微信"),
    ZFB_PAY_TYPE(1,"支付宝");

    private Integer code;

    private String msg;

    PayTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
