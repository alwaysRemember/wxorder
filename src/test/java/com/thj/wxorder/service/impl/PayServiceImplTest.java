package com.thj.wxorder.service.impl;

import com.thj.wxorder.dto.OrderDTO;
import com.thj.wxorder.service.OrderService;
import com.thj.wxorder.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;


    @Test
    public void create() {
        OrderDTO orderDTO  = orderService.findOne("1887131532585458790");


        payService.create(orderDTO);

    }
}