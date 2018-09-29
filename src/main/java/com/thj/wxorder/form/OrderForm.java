package com.thj.wxorder.form;

import com.thj.wxorder.dataobject.OrderDetail;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/24 16:23
 **/
@Data
public class OrderForm {


    // 买家姓名
    @NotEmpty(message = "姓名必填")
    private String name;

    // 买家手机号
    @NotEmpty(message = "手机号必填")
    private String phone;

    // 买家地址
    @NotEmpty(message = "地址必填")
    private String address;

    // 买家openid
    @NotEmpty(message = "openid必填")
    private String openid;

    // 买家选择的商品
    private List<OrderDetail> items;

}
