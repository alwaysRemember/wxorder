package com.thj.wxorder.backstage;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单详情 （后台）
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/9 14:25
 **/

@Data
public class OrderDetailBackstage {

    // 订单id
    private String orderId;

    // 订单总价
    private BigDecimal orderAmount;

    // 订单状态
    private Integer orderStatus;

    // 订单列表
    List<OrderDetailListBackstage> orderDetailList;


}
