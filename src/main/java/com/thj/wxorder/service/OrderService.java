package com.thj.wxorder.service;

import com.thj.wxorder.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/4 11:22
 **/

public interface OrderService {

    // 创建订单
    OrderDTO create(OrderDTO orderDTO) throws Exception;

    // 查询单个订单
    OrderDTO findOne(String orderId);

    // 查询某个用户的订单列表
    Page<OrderDTO> findList (String buyerOpenid , Pageable pageable);

    // 查询所有的订单列表
    Page<OrderDTO> findList (Pageable pageable);

    // 取消订单
    OrderDTO cancel( OrderDTO orderDTO);

    // 完结订单
    OrderDTO finish (OrderDTO orderDTO);

    // 支付订单
    OrderDTO paid (OrderDTO orderDTO);


}
