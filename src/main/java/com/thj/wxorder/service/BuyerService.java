package com.thj.wxorder.service;

import com.thj.wxorder.dto.OrderDTO;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/26 16:59
 **/

public interface BuyerService {

    // 查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    // 取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
