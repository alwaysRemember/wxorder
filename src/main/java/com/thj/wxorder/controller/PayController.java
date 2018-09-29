package com.thj.wxorder.controller;

import com.lly835.bestpay.model.PayResponse;
import com.thj.wxorder.dataobject.Result;
import com.thj.wxorder.dto.OrderDTO;
import com.thj.wxorder.enums.PayStatusEnum;
import com.thj.wxorder.enums.ResultEnum;
import com.thj.wxorder.exception.WxorderException;
import com.thj.wxorder.service.OrderService;
import com.thj.wxorder.service.PayService;
import com.thj.wxorder.util.ResultVOUtil;
import com.thj.wxorder.viewObject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


/**
 * 支付接口
 *
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/1 16:05
 **/

@Controller
@Slf4j
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId, @RequestParam("returnUrl") String returnUrl, Map<String, Object> map) {
        // 1. 查询订单
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (WxorderException e) {
            throw new WxorderException(ResultEnum.ORDER_NOT_EXIST);
        }
//        payService.create(orderDTO);

        // 这一步可以返回payService的信息给前端，前端修改js进行付款不一定需要后端模板渲染
        return new ModelAndView("pay/create");

    }

    // 支付完成后回调接口
    @PostMapping("/notify")
    public ResultVO notify(@RequestBody String notifyData) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        // 接收回调信息
        PayResponse payResponse = payService.notify(notifyData);

        // 修改订单支付状态

        OrderDTO orderDTO;

        try {
            orderDTO = orderService.paid(orderService.findOne(payResponse.getOrderId()));
        }catch (WxorderException e){
            return resultVOUtil.error(-1,e.getMessage());
        }

        // 修改后判断是否为支付成功的状态
        if ( orderDTO.getPayStatus() != PayStatusEnum.SUCCESS.getCode() ){
            return resultVOUtil.error(-1,ResultEnum.ORDER_UPDATE_FAIL.getMsg());
        }
        return resultVOUtil.success(orderDTO);
    }
}
