package com.thj.wxorder.service;

import com.thj.wxorder.dto.OrderDTO;

/**
 * 推送消息
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/22 16:31
 **/

public interface PushMessageService {

    void orderStatus(OrderDTO orderDTO);

}
