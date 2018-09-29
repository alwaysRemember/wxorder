package com.thj.wxorder.controller;

import com.sun.org.apache.xpath.internal.operations.String;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * 微信授权成功返回页面
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/27 11:16
 **/
@RestController
@RequestMapping(value = "/wx")
@Slf4j
public class wxController {



    @GetMapping(value = "/success")
    public void success(@RequestParam("code") String code){
        log.info(new Date()+"【微信授权】微信授权成功");
        log.info(new Date()+"【微信授权】code={}",code);

        java.lang.String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxdd3a3079f7ddb38b&secret=50e560b7249f6309c0c60f9647429956&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        java.lang.String response = restTemplate.getForObject(url, java.lang.String.class);
    }
}
