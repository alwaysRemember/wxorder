package com.thj.wxorder.service;

import com.thj.wxorder.dataobject.SellerInfo;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/20 15:37
 **/

public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);
}
