package com.thj.wxorder.repository;

import com.thj.wxorder.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/20 15:33
 **/

public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {

    SellerInfo findByOpenid(String openid);
}
