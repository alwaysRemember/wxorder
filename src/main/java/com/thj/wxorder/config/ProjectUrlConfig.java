package com.thj.wxorder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/21 10:19
 **/
@Data
@ConfigurationProperties(prefix = "project-url")
@Component
public class ProjectUrlConfig {

    // 微信公众帐号授权url
    public String wechatMpAuthorize;

    // 微信开放平台授权url
    public String  wechatOpenAuthorize;

    // 点餐系统url
    public String sell;
}
