package com.thj.wxorder.exception;

import com.thj.wxorder.enums.ProductCategoryEnum;
import com.thj.wxorder.enums.ProductEnum;
import com.thj.wxorder.enums.ResultEnum;
import com.thj.wxorder.enums.SellerInfoEnum;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/5 14:59
 **/

public class WxorderException extends RuntimeException {

    private Integer code;


    public WxorderException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public WxorderException(ProductCategoryEnum productCategoryEnum){
        super(productCategoryEnum.getMsg());
        this.code = productCategoryEnum.getCode();
    }

    public WxorderException(SellerInfoEnum sellerInfoEnum){
        super(sellerInfoEnum.getMsg());
        this.code = sellerInfoEnum.getCode();
    }

    public WxorderException(ProductEnum productEnum){
        super(productEnum.getMsg());
        this.code = productEnum.getCode();
    }

    public WxorderException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
