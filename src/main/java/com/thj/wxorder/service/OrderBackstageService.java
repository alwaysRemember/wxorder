package com.thj.wxorder.service;

import com.thj.wxorder.backstage.OrderBackstage;
import com.thj.wxorder.backstage.OrderDetailBackstage;
import org.springframework.data.domain.Page;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/7 11:04
 **/

public interface OrderBackstageService {

    /**
     * 获取订单列表
     * @param page  页数
     * @param size  条数
     * @return
     */
    Page<OrderBackstage> findList(Integer page,Integer size);

    /**
     * 获取订单详情
     * @param orderId   订单ID
     * @return
     */
    OrderDetailBackstage orderDetail(String orderId);


}
