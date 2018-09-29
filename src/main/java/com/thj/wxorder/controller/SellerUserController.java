package com.thj.wxorder.controller;

import com.thj.wxorder.constant.CookieConstant;
import com.thj.wxorder.constant.RedisConstant;
import com.thj.wxorder.dataobject.SellerInfo;
import com.thj.wxorder.enums.SellerInfoEnum;
import com.thj.wxorder.exception.WxorderException;
import com.thj.wxorder.service.SellerService;
import com.thj.wxorder.util.CookieUtil;
import com.thj.wxorder.util.ResultVOUtil;
import com.thj.wxorder.viewObject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户操作
 *
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/21 10:31
 **/

@Slf4j
@RestController
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 登录
     *
     * @param openid 微信openid
     * @return
     */
    @GetMapping("/login")
    public ResultVO login(@RequestParam("openid") String openid, HttpServletResponse response) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        // openid和数据库里面的数据进行匹配
        SellerInfo sellerInfo;
        try {
            sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        } catch (WxorderException e) {
            return resultVOUtil.error(-1, e.getMessage());
        }
        Map<String, String> map = new HashMap<>();
        // 数据库无此用户
        if ( sellerInfo == null ) {
            map.put("redirectUrl", "http://www.baidu.com");
            return resultVOUtil.error(SellerInfoEnum.NO_USER_INFO.getCode(), SellerInfoEnum.NO_USER_INFO.getMsg(), map);
        }

        // 设置token到redis

        // 生成token
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;

        // 设置redis  key value 过期时间 时间格式
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        // 设置token到cookie
        CookieUtil.set(response,CookieConstant.TOKEN,token,7200);

        map.put("redirectUrl","http://www.baidu.com");
        return resultVOUtil.success(map);
    }

    /**
     * 退出
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public ResultVO logout(HttpServletRequest request,
                           HttpServletResponse response) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();
        // 从cookie查询
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if ( cookie !=null ){

            // 清除redis
            String redisKey = String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue());
            stringRedisTemplate.opsForValue().getOperations().delete(redisKey);
            // 清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);

            return resultVOUtil.success();
        }
        return resultVOUtil.error(-1,"未找到cookie");
    }
}
