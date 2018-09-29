package com.thj.wxorder.aspect;

import com.thj.wxorder.constant.CookieConstant;
import com.thj.wxorder.constant.RedisConstant;
import com.thj.wxorder.exception.WxorderAuthorizeException;
import com.thj.wxorder.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * AOP处理请求拦截
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/22 14:34
 **/

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 校验文件选择
    @Pointcut("execution(public * com.thj.wxorder.controller.WxOrderController.*(..))")
    public void verify() {

    }

    // 在执行方法前进行校验
    @Before("verify()")
    public void doVerify(){

        // 获取request数据
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 查询cookie
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if ( cookie == null ){
            log.warn(new Date()+"【登录校验】Cookie中查不到Token");
            throw new WxorderAuthorizeException();
        }

        // 去redis查
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

        if ( tokenValue.isEmpty() ){
            log.warn(new Date()+"【登录校验】redis中查不到Token");
            throw new WxorderAuthorizeException();
        }


    }

}
