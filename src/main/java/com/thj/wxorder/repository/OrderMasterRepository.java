package com.thj.wxorder.repository;

import com.thj.wxorder.dataobject.OrderMaster;
import com.thj.wxorder.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/3 16:26
 **/

public interface OrderMasterRepository extends JpaRepository<OrderMaster , String> {

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid , Pageable pageable);

    Integer deleteByBuyerOpenid(String buyerOpenid);

}
