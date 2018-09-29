package com.thj.wxorder.controller;

import com.thj.wxorder.config.ProjectUrlConfig;
import com.thj.wxorder.enums.ResultEnum;
import com.thj.wxorder.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * weixin-java-tools SDK方式获取access_token
 *
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/27 13:44
 **/
@Controller
@RequestMapping(value = "/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Qualifier("WxMpService")
    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping(value = "/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) throws UnsupportedEncodingException {


        // 配置


        String url = projectUrlConfig.getWechatMpAuthorize();
        // 生成用户授权url ，第一个参数为授权后跳转连接
        String result = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl, "utf-8"));
        log.info(new Date() + "【微信网页授权】获取code,result={}", result);

        return "redirect:" + result;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {

        ResultVOUtil resultVOUtil = new ResultVOUtil();

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();

        // 获取access_token 为了防止异常要try一下
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error(new Date() + "【微信网页授权】{}", e);
            resultVOUtil.error(-1, ResultEnum.WECHAT_MP_ERROR.getMsg());
        }

        // 获取openid
        String openid = wxMpOAuth2AccessToken.getOpenId();


        log.info(new Date() + "【微信网页授权】url=" + returnUrl + "?openid=" + openid);

        return "redirect:" + returnUrl + "?openid=" + openid;

    }

    // 开放平台获取redirectUrl
    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) throws UnsupportedEncodingException {
        String url = projectUrlConfig.getWechatOpenAuthorize();
        String redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN, URLEncoder.encode(returnUrl, "utf-8"));

        return "redirect:" + redirectUrl;
    }

    // 开放平台获取openid
    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) throws WxErrorException {

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error(new Date() + "【微信网页授权】{}", e);
        }

        String openid = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:" + returnUrl + "?openid=" + openid;
    }
}
