package com.thj.wxorder.service.impl;

import com.thj.wxorder.dataobject.OrderDetail;
import com.thj.wxorder.dto.OrderDTO;
import com.thj.wxorder.enums.OrderStatusEnum;
import com.thj.wxorder.enums.PayStatusEnum;
import com.thj.wxorder.util.JsonUtil;
import com.thj.wxorder.util.ResultVOUtil;
import com.thj.wxorder.viewObject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "1101110";

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("tang");
        orderDTO.setBuyerAddress("上海市");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        orderDTO.setBuyerPhone("15923125232");


        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setProductId("01");
        orderDetail.setProductQuantity(1);


        orderDetailList.add(orderDetail);
        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = null;

        try {
            result = orderService.create(orderDTO);
        } catch (Exception e) {
            log.error(new Date() +"【创建订单失败】"+e.getMessage()+",orderDTO={}",orderDTO);
        }

        ResultVOUtil  resultVOUtil = new ResultVOUtil();
        ResultVO resultVO =  resultVOUtil.success(result);
        log.info("result：｛｝"+resultVO);

    }

    @Test
    public void findOne() {
        String orderid = "9835041532330794600";
        ResultVOUtil resultVOUtil = new ResultVOUtil();
        OrderDTO orderDTO = orderService.findOne(orderid);

        if ( orderDTO == null ){
            ResultVO resultVO = resultVOUtil.error(-1,"没有查询到订单数据");
        }
        ResultVO resultVO = resultVOUtil.success(orderDTO);

        log.info("result:{}"+resultVO);
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0,4);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,request);

        ResultVOUtil resultVOUtil = new ResultVOUtil();
        ResultVO resultVO = resultVOUtil.success(orderDTOPage);
        log.info("result:{}" + resultVO.toString());
    }

    @Test
    public void list(){
        PageRequest pageRequest = new PageRequest(0,10);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);

        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());

        ResultVOUtil resultVOUtil = new ResultVOUtil();
        ResultVO resultVO = resultVOUtil.success(orderDTOPage);

        log.info(new Date()+"【测试list】,list={}",resultVO);


    }

    @Test
    public void cancel() {
        String orderid = "9835041532330794600";
        OrderDTO orderDTO = orderService.findOne(orderid);


        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());

    }

    @Test
    public void finish() {
        String orderid = "1664001532330298975";
        OrderDTO orderDTO = orderService.findOne(orderid);

        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() {
        String orderid = "1496271532329531787";
        OrderDTO orderDTO = orderService.findOne(orderid);

        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }
}