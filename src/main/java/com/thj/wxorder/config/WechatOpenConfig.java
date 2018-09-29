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
 * @date 2018/8/20 16:27
 **/
@Component
public class WechatOpenConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public WxMpService WxMpService(){
        WxMpService wxMpService = new WxMpServiceImpl();

        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());

        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage (){
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(wechatAccountConfig.getOpenAppId());
        wxMpInMemoryConfigStorage.setSecret(wechatAccountConfig.getOpenAppSecret());

        return wxMpInMemoryConfigStorage;
    }
}
