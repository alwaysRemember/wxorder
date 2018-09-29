package com.thj.wxorder.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thj.wxorder.dataobject.OrderDetail;
import com.thj.wxorder.enums.OrderStatusEnum;
import com.thj.wxorder.enums.PayStatusEnum;
import com.thj.wxorder.util.EnumUtil;
import com.thj.wxorder.util.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/4 11:35
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    // 订单ID
    private String orderId;

    // 买家名字
    private String buyerName;

    // 买家电话
    private String buyerPhone;

    // 买家地址
    private String buyerAddress;

    // 买家微信openid
    private String buyerOpenid;

    // 订单总价
    private BigDecimal orderAmount;

    // 订单状态 默认 0 新下单;
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    // 支付状态 默认 0 未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    // 创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    // 修改时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    // 订单列表
    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }

}
