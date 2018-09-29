package com.thj.wxorder.service.impl;

import com.thj.wxorder.dataobject.SellerInfo;
import com.thj.wxorder.enums.SellerInfoEnum;
import com.thj.wxorder.exception.WxorderException;
import com.thj.wxorder.repository.SellerInfoRepository;
import com.thj.wxorder.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/20 15:38
 **/
@Service
@Slf4j
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;


    // 根据openid查询用户
    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) throws WxorderException {

        if ( openid.isEmpty() ){
            log.error(new Date()+"【查询用户】"+SellerInfoEnum.OPENID_IS_NULL.getMsg());
            throw new WxorderException(SellerInfoEnum.OPENID_IS_NULL);
        }

        return sellerInfoRepository.findByOpenid(openid);
    }
}
