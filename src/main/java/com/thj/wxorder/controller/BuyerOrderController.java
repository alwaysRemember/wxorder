package com.thj.wxorder.controller;

import com.thj.wxorder.converter.OrderForm2OrderDTOConverter;
import com.thj.wxorder.dto.OrderDTO;
import com.thj.wxorder.enums.OrderStatusEnum;
import com.thj.wxorder.enums.ResultEnum;
import com.thj.wxorder.form.OrderForm;
import com.thj.wxorder.service.BuyerService;
import com.thj.wxorder.service.OrderService;
import com.thj.wxorder.util.ResultVOUtil;
import com.thj.wxorder.viewObject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.transform.Result;
import java.util.*;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/24 16:09
 **/
@RestController
@RequestMapping(value = "/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;


    // 创建订单
    @PostMapping(value = "/save")
    public ResultVO<Map<String, String>> create(@Valid @RequestBody OrderForm orderForm, BindingResult bindingResult) {

        // 定义返回函数
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        // 后台判断传参是否正确
        if ( bindingResult.hasErrors() ) {
            log.error("【创建订单失败】参数不正确,orderForm={}", orderForm);
            return resultVOUtil.error(-1, bindingResult.getFieldError().getDefaultMessage());
        }

        // 数据转成orderDTO实体类
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);

        OrderDTO result = null;

        // 如果创建订单逻辑出错了会抛出到这一层，进行处理。
        try {
            result = orderService.create(orderDTO);
        } catch (Exception e) {
            log.error(new Date() + "【创建订单失败】" + e.getMessage() + ",orderDTO={}", orderDTO);
            return resultVOUtil.error(-1, e.getMessage());
        }

        // 在判断一次订单是否创建成功
        if ( result == null ) {
            log.error(new Date() + "【创建订单失败】,order={}", orderForm);
            return resultVOUtil.error(-1, ResultEnum.ORDER_CREATE_ERROR.getMsg());
        }

        // 判断购物车是否为空
        if ( CollectionUtils.isEmpty(result.getOrderDetailList()) ) {
            log.error("【创建订单失败】购物车为空, orderDetailList={}", result.getOrderDetailList());
            return resultVOUtil.error(-1, ResultEnum.CART_EMPTY.getMsg());
        }
        //返回出来的是个map
        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());

        return resultVOUtil.success(map);
    }

    // 订单列表
    @GetMapping(value = "/findList")
    public ResultVO<List<OrderDTO>> findList(@RequestParam("openid") String openid, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        if ( StringUtils.isEmpty(openid) ) {
            log.error("【订单列表获取失败】openid为空");
            resultVOUtil.error(-1, ResultEnum.PARAMS_ERR.getMsg());
        }

        PageRequest pageRequest = new PageRequest(page, size);

        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);

        return resultVOUtil.success(orderDTOPage.getContent());
    }

    // 取消订单
    @PostMapping(value = "/cancel")
    public ResultVO<Map<String ,String>> cancel (@RequestParam("orderId")String orderId,@RequestParam("openid") String openid){
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        // 判断orderid是否为空
        if ( StringUtils.isEmpty(orderId) ){
            log.error(new Date()+"【取消订单】orderId参数错误,orderId={}",orderId);
            return resultVOUtil.error(-1,ResultEnum.PARAMS_ERR.getMsg());
        }

        OrderDTO orderDTO = null;

        // 处理异常错误
        try {
            orderDTO = buyerService.cancelOrder(openid,orderId);
        } catch (Exception e) {
            return resultVOUtil.error(-1, e.getMessage());
        }

        Map map = new HashMap();
        map.put("orderId",orderDTO.getOrderId());
        map.put("orderStatus",orderDTO.getOrderStatus());
        // 设置返回orderStatusText字段
        if ( orderDTO.getOrderStatus() == OrderStatusEnum.CANCEL.getCode() ){
            map.put("orderStatusText",OrderStatusEnum.CANCEL.getMsg());
        }


        return resultVOUtil.success(map);
    }

    // 订单详情
    @PostMapping(value = "/detail")
    public ResultVO detail(@RequestParam("orderId") String orderId,@RequestParam("openid") String openid) {

        ResultVOUtil resultVOUtil = new ResultVOUtil();

        OrderDTO result = null;

        // 如果查询订单详情出错会把错误抛到这里进行处理
        try {
            result = buyerService.findOrderOne(openid,orderId);
        } catch (Exception e) {
            return resultVOUtil.error(-1, e.getMessage());
        }


        return resultVOUtil.success(result);
    }

}
