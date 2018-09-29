package com.thj.wxorder.converter;

import com.thj.wxorder.dataobject.OrderDetail;
import com.thj.wxorder.dto.OrderDTO;
import com.thj.wxorder.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/25 14:24
 **/
@Slf4j
public class OrderForm2OrderDTOConverter {

    // orderForm 转为OrderDTO实体类
    public static OrderDTO convert(OrderForm orderForm){

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();

        orderForm.getItems().stream().map(
                e ->orderDetailList.add(e)
        ).collect(Collectors.toList());

        orderDTO.setOrderDetailList(orderDetailList);


        return orderDTO;
    }

}
