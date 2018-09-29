package com.thj.wxorder.service;

import com.thj.wxorder.dataobject.ProductInfo;
import com.thj.wxorder.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/2 14:27
 * productInfo接口实现
 **/

public interface ProductService {

    /**
     * 查找单个商品信息
     * @param productId 商品id
     * @return
     */
    ProductInfo findOne(String  productId);

    /**
     * 查询所有在架的商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 分页获取所有商品数据
     * @param pageable  分页对象 (PageRequest)
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);


    /**
     * 添加商品
     * @param productInfo   商品信息
     * @return
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 加商品
     * @param cartDTOList   增加的数组
     */
    void increaseStock(List<CartDTO> cartDTOList);


    /**
     * 减商品
     * @param cartDTOList  减少的数组
     */
    void decreaseStock(List<CartDTO> cartDTOList);

    /**
     * 修改商品为上架状态
     * @param productId 商品ID
     * @return
     */
    ProductInfo onShelfProduct(String productId);

    /**
     * 修改商品为下架状态
     * @param productId 商品ID
     * @return
     */
    ProductInfo downShelfProduct(String productId);

    Integer deleteProduct(String productId);
}
