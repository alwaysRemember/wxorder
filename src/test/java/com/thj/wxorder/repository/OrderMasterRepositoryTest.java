package com.thj.wxorder.repository;

import com.thj.wxorder.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;


    @Test
    public void saveTest() throws Exception {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123222");
        orderMaster.setBuyerName("汤好杰");
        orderMaster.setBuyerPhone("15901749277");
        orderMaster.setBuyerAddress("上海");
        orderMaster.setBuyerOpenid("3333333");
        orderMaster.setOrderAmount(new BigDecimal(30.5));

        OrderMaster orderMaster1 = repository.save(orderMaster);
        Assert.assertNotEquals(null, orderMaster);
    }

    @Test
    public void findAllTest() throws Exception {
        List<OrderMaster> resultList = repository.findAll();
        Assert.assertNotEquals(0,resultList.size());
    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        String openid = "3333333";
        PageRequest pageRequest = new PageRequest(0, 2);

        Page<OrderMaster> resultList = repository.findByBuyerOpenid(openid, pageRequest);

        Assert.assertNotEquals(0, resultList.getTotalElements());
    }
}