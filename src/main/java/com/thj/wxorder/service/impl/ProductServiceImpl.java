package com.thj.wxorder.service.impl;

import com.thj.wxorder.dataobject.ProductInfo;
import com.thj.wxorder.dto.CartDTO;
import com.thj.wxorder.enums.ProductEnum;
import com.thj.wxorder.enums.ProductStatusEnum;
import com.thj.wxorder.enums.ResultEnum;
import com.thj.wxorder.exception.WxorderException;
import com.thj.wxorder.handler.ResponseException;
import com.thj.wxorder.repository.ProductInfoRepository;
import com.thj.wxorder.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/2 14:32
 * ProductInfo接口方法实现
 **/
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) throws WxorderException {
        return repository.save(productInfo);
    }

    // 加商品
    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) throws WxorderException {
        // 遍历购物车
        for (CartDTO cartDTO : cartDTOList) {
            // 查询有没有这件商品 没有的话就报错
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if ( productInfo == null ) {
                log.error(new Date() + "【添加商品】" + ResultEnum.PRODUCT_NOT_EXIST.getMsg() + "productId={}", cartDTO.getProductId());
                throw new WxorderException(ResultEnum.PRODUCT_NOT_EXIST);

                // 返回特定的异常
              //  throw new ResponseException();
            }           // 原本库存加上要添加的库存并且重新设置进入数据库
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    // 减商品
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) throws WxorderException {
        // 遍历传入的购物车
        for (CartDTO cartDTO : cartDTOList) {
            // 查询有没有这件商品 没有的话就报错
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if ( productInfo == null ) {
                log.error(new Date() + "【删减商品】" + ResultEnum.PRODUCT_NOT_EXIST.getMsg() + "productId={}", cartDTO.getProductId());
                throw new WxorderException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 商品库存-购物车购买数量
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            // 小于0代表购买失败，报错否则的话就是购买成功，重新设置库存数量并且设置回去
            if ( result < 0 ) {
                throw new WxorderException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);

            repository.save(productInfo);
        }
    }

    // 修改商品为上架状态
    @Override
    public ProductInfo onShelfProduct(String productId) throws WxorderException {

        // 判断ID是否传入
        if ( productId.isEmpty() ) {
            log.error(new Date() + "【修改商品状态(上架)】" + ProductEnum.NO_PRODUCTID.getMsg());
            throw new WxorderException(ProductEnum.NO_PRODUCTID);
        }

        ProductInfo productInfo = repository.findOne(productId);

        // 判断是否查到数据
        if ( productInfo == null ) {
            log.error(new Date() + "【修改商品状态(上架)】" + ProductEnum.NO_PRODUCT.getMsg());
            throw new WxorderException(ProductEnum.NO_PRODUCT);
        }

        // 判断状态是否为已上架状态
        if ( productInfo.getProductStatus().equals(ProductStatusEnum.UP.getCode()) ) {
            log.error(new Date() + "【修改商品状态(上架)】" + ProductEnum.IS_ON_SHELF.getMsg());
            throw new WxorderException(ProductEnum.IS_ON_SHELF);
        }
        // 修改状态为上架
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo = this.save(productInfo);

        // 判断是否修改成功
        if ( !productInfo.getProductStatus().equals(ProductStatusEnum.UP.getCode()) ) {
            log.error(new Date() + "【修改商品状态(上架)】" + ProductEnum.MODIFY_STATUS_ERROR.getMsg());
            throw new WxorderException(ProductEnum.MODIFY_STATUS_ERROR);
        }

        return productInfo;
    }

    // 修改商品为下架状态
    @Override
    public ProductInfo downShelfProduct(String productId) throws WxorderException {

        // 是否传入ID
        if ( productId.isEmpty() ) {
            log.error(new Date() + "【修改商品状态(下架)】" + ProductEnum.NO_PRODUCTID.getMsg());
            throw new WxorderException(ProductEnum.NO_PRODUCTID);
        }

        // 查询
        ProductInfo productInfo = repository.findOne(productId);
        // 判断是否查询到
        if ( productInfo == null ) {
            log.error(new Date() + "【修改商品状态(下架)】" + ProductEnum.NO_PRODUCT.getMsg());
            throw new WxorderException(ProductEnum.NO_PRODUCT);
        }
        // 判断是否已经下架
        if ( productInfo.getProductStatus().equals(ProductStatusEnum.DOWN.getCode()) ) {
            log.error(new Date() + "【修改商品状态(下架)】" + ProductEnum.is_DOWN_SHELF.getMsg());
            throw new WxorderException(ProductEnum.is_DOWN_SHELF);
        }
        // 修改状态
        productInfo.setProductStatus((ProductStatusEnum.DOWN.getCode()));
        productInfo = this.save(productInfo);

        // 判断是否修改成功
        if ( !productInfo.getProductStatus().equals(ProductStatusEnum.DOWN.getCode()) ) {
            log.error(new Date() + "【修改商品状态(上架)】" + ProductEnum.MODIFY_STATUS_ERROR.getMsg());
            throw new WxorderException(ProductEnum.MODIFY_STATUS_ERROR);
        }

        return productInfo;
    }

    // 删除商品
    @Override
    @Transactional
    public Integer deleteProduct(String productId) throws WxorderException {

        // 查询是否传递ID
        if ( productId.isEmpty() ) {
            log.error(new Date() + "【删除商品】" + ProductEnum.NO_PRODUCTID.getMsg());
            throw new WxorderException(ProductEnum.NO_PRODUCTID);
        }
        // 找到这条数据
        ProductInfo productInfo = repository.findOne(productId);
        if ( productInfo == null ) {
            log.error(new Date() + "【删除商品】" + ProductEnum.NO_PRODUCT.getMsg());
            throw new WxorderException(ProductEnum.NO_PRODUCT);
        }

        // 删除
        return repository.deleteByProductId(productId);

    }
}
