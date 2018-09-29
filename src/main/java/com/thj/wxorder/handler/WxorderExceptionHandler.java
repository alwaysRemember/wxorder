package com.thj.wxorder.handler;

import com.thj.wxorder.enums.SellerInfoEnum;
import com.thj.wxorder.exception.WxorderAuthorizeException;
import com.thj.wxorder.exception.WxorderException;
import com.thj.wxorder.util.ResultVOUtil;
import com.thj.wxorder.viewObject.ResultVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/22 14:48
 **/
@ControllerAdvice
@RestController
public class WxorderExceptionHandler {

    // 拦截登录异常
    @ExceptionHandler(value = WxorderAuthorizeException.class)
    public ResultVO handlerAuthorizeException(){
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        return resultVOUtil.error(SellerInfoEnum.NO_LOGIN.getCode(),SellerInfoEnum.NO_LOGIN.getMsg());
    }



    // WxorderException 异常捕获
    @ExceptionHandler(value = WxorderException.class)
    public ResultVO handlerWxorderException(WxorderException e){
        ResultVOUtil resultVOUtil = new ResultVOUtil();
        return resultVOUtil.error(-1,e.getMessage());
    }

    // 捕获特定的异常 返回状态码为403
    @ExceptionHandler(value = ResponseException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    private void handlerResponseException(){


    }

}
