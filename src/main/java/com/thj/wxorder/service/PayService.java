package com.thj.wxorder.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.thj.wxorder.dto.OrderDTO;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/1 16:36
 **/

public interface PayService {

    // 创建支付订单
    PayResponse create(OrderDTO orderDTO);

    // 支付完成回调
    PayResponse notify (String notifyData);

    // 退款
    RefundResponse refund(OrderDTO orderDTO);
}

