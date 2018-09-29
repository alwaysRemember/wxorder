package com.thj.wxorder.backstage;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thj.wxorder.enums.OrderStatusEnum;
import com.thj.wxorder.enums.PayStatusEnum;
import com.thj.wxorder.enums.PayTypeEnum;
import com.thj.wxorder.util.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单列表（后台）
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/7 10:11
 **/
@Data
public class OrderBackstage {

    // 订单ID
    private String orderId;

    // 买家名字
    private String buyerName;

    // 买家电话
    private String buyerPhone;

    // 买家地址
    private String buyerAddress;

    // 订单总价
    private BigDecimal orderAmount;

    // 订单状态 默认 0 新下单
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    // 支付方式 默认 0 微信
    private Integer payType = PayTypeEnum.WX_Pay_TYPE.getCode();

    // 支付状态 默认 0 未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    // 创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;



}
