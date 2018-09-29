package com.thj.wxorder.service.impl;

import com.thj.wxorder.backstage.OrderBackstage;
import com.thj.wxorder.backstage.OrderDetailBackstage;
import com.thj.wxorder.backstage.OrderDetailListBackstage;
import com.thj.wxorder.dataobject.OrderDetail;
import com.thj.wxorder.dto.OrderDTO;
import com.thj.wxorder.exception.WxorderException;
import com.thj.wxorder.service.OrderBackstageService;
import com.thj.wxorder.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/7 11:05
 **/

@Service
public class OrderBackstageServiceImpl implements OrderBackstageService {

    @Autowired
    private OrderService orderService;

    @Override
    public Page<OrderBackstage> findList(Integer page, Integer size) {

        // 由于page是从0开始所以要-1
        PageRequest pageRequest = new PageRequest(page-1,size);

        // 获取orderDTO的page对象
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);

        List<OrderBackstage> orderBackstageList = new ArrayList<>();

        // orderDTO 转换成 orderDTOBackstage
        for (OrderDTO orderDTO : orderDTOPage.getContent()){
            OrderBackstage orderBackstage = new OrderBackstage();

            BeanUtils.copyProperties(orderDTO,orderBackstage);
            orderBackstageList.add(orderBackstage);
        }

        // 转换成page对象
        Page<OrderBackstage> orderBackstagePage = new PageImpl<>(orderBackstageList,pageRequest,orderDTOPage.getTotalElements());

        return orderBackstagePage;
    }

    @Override
    public OrderDetailBackstage orderDetail (String orderId) throws WxorderException {

        // 查订单
        OrderDTO orderDTO = orderService.findOne(orderId);

        // 生成实体类
        OrderDetailBackstage orderDetailBackstage = new OrderDetailBackstage();

        // 实体类拷贝
        BeanUtils.copyProperties(orderDTO,orderDetailBackstage);
        List<OrderDetailListBackstage> orderDetailListBackstageList = new ArrayList<>();

        // 遍历订单中的产品列表
        for (OrderDetail orderDetail :orderDTO.getOrderDetailList()){
            OrderDetailListBackstage orderDetailListBackstage = new OrderDetailListBackstage();
            // 拷贝数据
            BeanUtils.copyProperties(orderDetail,orderDetailListBackstage);
            // 设置产品总价
            orderDetailListBackstage.setProductAmount(orderDetailListBackstage.getProductPrice()
                    .multiply(new BigDecimal(orderDetailListBackstage.getProductQuantity())));
            // 添加数组
            orderDetailListBackstageList.add(orderDetailListBackstage);
        }
        // 修改实习类的产品列表
        orderDetailBackstage.setOrderDetailList(orderDetailListBackstageList);

        return orderDetailBackstage;
    }
}
