package com.thj.wxorder.dataobject;

import com.thj.wxorder.enums.OrderStatusEnum;
import com.thj.wxorder.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/3 16:19
 **/

@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    // 订单ID
    @Id
    private String orderId;

    // 买家名字
    private String buyerName;

    // 买家电话
    private String buyerPhone;

    // 买家地址
    private String buyerAddress;

    // 买家微信openid
    private String buyerOpenid;

    // 订单总价
    private BigDecimal orderAmount;

    // 订单状态 默认 0 新下单;
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    // 支付状态 默认 0 未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    // 创建时间
    private Date createTime;

    // 修改时间
    private Date updateTime;


}
