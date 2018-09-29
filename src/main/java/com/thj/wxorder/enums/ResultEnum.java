package com.thj.wxorder.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品库存不正确"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(13,"订单详情不存在"),

    ORDER_STATUS_ERROR(14,"订单状态不正确"),
    ORDER_UPDATE_FAIL(15,"订单更新失败"),
    ORDER_DETAIL_EMPTY(16,"订单详情为空"),
    ORDER_PAY_STATUS_ERROR(17,"订单支付状态不正确"),
    ORDER_CREATE_ERROR(18,"订单创建失败"),
    PARAMS_ERR(19,"参数不正确"),
    CART_EMPTY(20,"购物车不能为空"),
    OPENID_NOT_ORDER_DETAIL_OPENID(21,"该订单不属于当前用户"),
    WECHAT_MP_ERROR(22,"微信公众帐号方面错误"),
    WECHAT_NOTIFY_MONEY_VERIFY_ERROR(23,"微信支付异步通知金额校验不通过")
    ;


    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
