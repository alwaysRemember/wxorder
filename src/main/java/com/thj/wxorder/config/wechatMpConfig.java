package com.thj.wxorder.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/27 13:55
 **/
@Component
public class wechatMpConfig {

    @Autowired
    private WechatAccountConfig accountConfig;

    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService = new WxMpServiceImpl();

        wxMpService.setWxMpConfigStorage(WxMpConfigStorage());

        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage WxMpConfigStorage  (){
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();

        wxMpConfigStorage.setAppId(accountConfig.getMpAppId());
        wxMpConfigStorage.setSecret(accountConfig.getMpAppSecret());

        return wxMpConfigStorage;
    }
}
