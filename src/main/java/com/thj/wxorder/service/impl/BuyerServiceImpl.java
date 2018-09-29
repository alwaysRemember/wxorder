package com.thj.wxorder.service.impl;

import com.thj.wxorder.dto.OrderDTO;
import com.thj.wxorder.enums.ResultEnum;
import com.thj.wxorder.exception.WxorderException;
import com.thj.wxorder.service.BuyerService;
import com.thj.wxorder.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/26 17:00
 **/
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;


    @Override
    public OrderDTO findOrderOne(String openid, String orderId) throws WxorderException {

        OrderDTO orderDTO = orderService.findOne(orderId);
        if ( !orderDTO.getBuyerOpenid().equalsIgnoreCase(openid) ) {
            log.error(new Date() + "【查询订单详情】查询订单详情失败，openid与查到的订单详情openid不符合,openid={},orderDTO={}", openid, orderDTO);
            throw new WxorderException(ResultEnum.OPENID_NOT_ORDER_DETAIL_OPENID);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) throws WxorderException {

        OrderDTO orderDTO = this.findOrderOne(openid, orderId);
        if ( orderDTO == null ){
            log.error(new Date()+"【取消订单】查无此订单,orderId={},openid={}",orderId,openid);
            throw new WxorderException(ResultEnum.ORDER_NOT_EXIST);
        }

        orderDTO = orderService.cancel(orderDTO);

        return orderDTO;
    }
}
