package com.thj.wxorder.converter;

import com.thj.wxorder.dataobject.OrderMaster;
import com.thj.wxorder.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/23 16:58
 **/

public class OrderMaster2OrderDTOConverter {
    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();

        BeanUtils.copyProperties(orderMaster,orderDTO);

        return orderDTO;
    }
    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(
                e -> convert(e)
        ).collect(Collectors.toList());
    }
}
