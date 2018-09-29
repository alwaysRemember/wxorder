package com.thj.wxorder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * webscoket配置
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/23 11:06
 **/

@Component
public class WebSocketConfig {

    // @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
