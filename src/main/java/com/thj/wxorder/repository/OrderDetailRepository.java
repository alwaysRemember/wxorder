package com.thj.wxorder.repository;

import com.thj.wxorder.dataobject.OrderDetail;
import com.thj.wxorder.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/3 16:45
 **/

public interface OrderDetailRepository extends JpaRepository<OrderDetail , String> {

    List<OrderDetail> findByOrderId(String orderId);
}
