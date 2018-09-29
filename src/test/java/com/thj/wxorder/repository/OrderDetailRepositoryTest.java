package com.thj.wxorder.repository;

import com.thj.wxorder.dataobject.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderDetailRepositoryTest {


    @Autowired
    private OrderDetailRepository repository;


    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("111111");
        orderDetail.setOrderId("123123");
        orderDetail.setProductId("01");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductIcon("http:xxx.jpg");
        orderDetail.setProductPrice(new BigDecimal(3.2));
        orderDetail.setProductQuantity(1);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotEquals(null,result);
    }

    @Test
    public void findAll(){
        List<OrderDetail> resultList = repository.findAll();
        Assert.assertNotEquals(0,resultList.size());
        log.info("订单表数据：｛｝"+resultList);
    }

    @Test
    public void findByOrderId() {
        String orderId = "123123";
        List<OrderDetail> resultList = repository.findByOrderId(orderId);
        Assert.assertNotEquals(0,resultList.size());
        log.info("订单详情：｛｝"+resultList);

    }
}