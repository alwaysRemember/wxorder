package com.thj.wxorder.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/1 16:47
 **/

@Component
public class WechatPayConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public BestPayServiceImpl bestPayService(){


        //设置支付配置
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return bestPayService;
    }
    @Bean
    public WxPayH5Config wxPayH5Config(){
        // 设置H5支付所需的配置
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(wechatAccountConfig.getMpAppId());
        wxPayH5Config.setAppSecret(wechatAccountConfig.getMpAppSecret());
        wxPayH5Config.setKeyPath(wechatAccountConfig.getKeyPath());
        wxPayH5Config.setMchId(wechatAccountConfig.getMchId());
        wxPayH5Config.setMchKey(wechatAccountConfig.getMchKey());
        wxPayH5Config.setNotifyUrl(wechatAccountConfig.getNotifyUrl());
        return wxPayH5Config;
    }
}
