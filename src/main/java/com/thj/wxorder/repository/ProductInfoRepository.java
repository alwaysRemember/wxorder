package com.thj.wxorder.repository;

import com.thj.wxorder.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {


    List<ProductInfo> findByProductStatus(Integer productStatus);


    ProductInfo findOne(String productId);

    Integer deleteByProductId (String productId);
}
