package com.thj.wxorder.service.impl;

import com.thj.wxorder.converter.OrderMaster2OrderDTOConverter;
import com.thj.wxorder.dataobject.OrderDetail;
import com.thj.wxorder.dataobject.OrderMaster;
import com.thj.wxorder.dataobject.ProductInfo;
import com.thj.wxorder.dto.CartDTO;
import com.thj.wxorder.dto.OrderDTO;
import com.thj.wxorder.enums.OrderStatusEnum;
import com.thj.wxorder.enums.PayStatusEnum;
import com.thj.wxorder.enums.ResultEnum;
import com.thj.wxorder.exception.WxorderException;
import com.thj.wxorder.repository.OrderDetailRepository;
import com.thj.wxorder.repository.OrderMasterRepository;
import com.thj.wxorder.service.*;
import com.thj.wxorder.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/4 13:38
 **/
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) throws WxorderException {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);

        // 1. 查商品
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            // 判断商品是否存在
            if ( productInfo == null ) {
                throw new WxorderException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 2. 计算订单总价
                orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            // 订单详情入库
            // 订单ID
            orderDetail.setOrderId(orderId);
            // 订单详情ID
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        // 3. 写入订单数据库
        OrderMaster orderMaster = new OrderMaster();

        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);

        orderMasterRepository.save(orderMaster);

        // 4. 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())

        ).collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);

        BeanUtils.copyProperties(orderMaster, orderDTO);

        // 发送websocket消息
        webSocket.sendMessage("有新的订单");

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) throws WxorderException {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if ( orderMaster == null ) {
            log.error(new Date()+"【获取订单失败】订单不存在,orderId={}",orderId);
            throw new WxorderException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = (List<OrderDetail>) orderDetailRepository.findOne(orderId);
        if ( CollectionUtils.isEmpty(orderDetailList) ) {
            log.error(new Date()+"【获取订单详情失败】订单详情不存在,orderId={}",orderId);
            throw new WxorderException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        // 用page的方法获取所有的数据转换成数组
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());


        // 新建一个page，传参 数据数组、传进来的page对象、总条数
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());

        return orderDTOPage;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {

        // 获取page对象形式的数据
        Page<OrderMaster> orderMasters = orderMasterRepository.findAll(pageable);

        // 转换成OrderDTO
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasters.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderMasters.getTotalElements());

        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) throws WxorderException {

        // 转换数据
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);

        // 判断订单状态
        if ( !orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()) ) {
            log.error(new Date() + "【取消订单】订单状态不正确，orderId={},orderStatus={},", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new WxorderException(ResultEnum.ORDER_STATUS_ERROR);
        }


        // 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if ( updateResult == null ) {
            log.error(new Date() + "【取消订单】更新失败，orderMaster={}", orderMaster);
            throw new WxorderException(ResultEnum.ORDER_UPDATE_FAIL);

        }
        // 返还库存
        if ( CollectionUtils.isEmpty(orderDTO.getOrderDetailList()) ) {
            log.error(new Date() + "【取消订单】订单中无商品详情，orderDTO={}", orderDTO);
            throw new WxorderException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        // 判断订单状态
        if ( !orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode()) ) {
            log.error(new Date()+"【取消订单】订单状态不是支付成功,orderDTO={}",orderDTO);
            throw new WxorderException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        // 如果已经支付了，要退款
       // payService.refund(orderDTO);

        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) throws WxorderException {
        String orderId = orderDTO.getOrderId();
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);

        // 是否查到了数据，没有查到就报错

        if ( orderMaster == null ) {
            log.error(new Date() + "【完结订单】没有查到订单数据,orderDTO={}", orderDTO);
            throw new WxorderException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 判断状态是否为已完结状态或者取消状态 如果当前是已完结状态或者取消状态 就需要报错
        if ( orderMaster.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode()) || orderMaster.getOrderStatus().equals(OrderStatusEnum.CANCEL.getCode()) ) {
            log.error(new Date() + "【完结订单】订单状态不正确,orderId={}", orderMaster.getOrderId());
            throw new WxorderException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 如果是等待支付的状态，就不能完结
        if ( orderMaster.getPayStatus().equals(PayStatusEnum.WAIT) ) {
            log.error(new Date() + "【完结订单】订单支付状态不正确,orderId={}", orderMaster.getOrderId());
            throw new WxorderException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        // 状态改为已完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());

        // 修改订单
        OrderMaster result = orderMasterRepository.save(orderMaster);

        if ( result == null ) {
            log.error(new Date() + "【完结订单】更新订单状态失败,orderMaster={}", orderMaster);
            throw new WxorderException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        // 推送微信模版消息
        pushMessageService.orderStatus(orderDTO);



        return OrderMaster2OrderDTOConverter.convert(orderMaster);
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) throws WxorderException {

        // 查询订单
        String orderid = orderDTO.getOrderId();
        OrderMaster orderMaster = orderMasterRepository.findOne(orderid);

        if ( orderMaster == null ) {
            log.error(new Date() + "new Date()+【支付订单】没有查到订单数据,orderId={}", orderDTO.getOrderId());
            throw new WxorderException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 如果不是新订单 报错，不能支付
        if ( !orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()) ) {
            log.error(new Date() + "【支付订单】订单状态不正确,orderId={}", orderMaster.getOrderId());
            throw new WxorderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        if ( !orderMaster.getPayStatus().equals(PayStatusEnum.WAIT.getCode()) ) {
            log.error(new Date() + "【支付订单】订单支付状态不正确,orderId={}", orderMaster.getOrderId());
            throw new WxorderException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        // 修改支付状态
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());

        // 更新数据
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if ( result == null ) {
            log.error(new Date() + " 【支付订单】更新订单状态失败,orderId={}", orderMaster.getOrderId());
            throw new WxorderException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return OrderMaster2OrderDTOConverter.convert(orderMaster);
    }
}
