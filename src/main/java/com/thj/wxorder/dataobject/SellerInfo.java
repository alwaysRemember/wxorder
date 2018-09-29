package com.thj.wxorder.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/20 15:21
 **/
@Data
@Entity
@DynamicUpdate
public class SellerInfo {

    @Id
    private String sellerId;

    // 用户姓名
    private String username;

    // 用户密码
    private String password;

    // 用户openid（微信官方拿到的）
    private String openid;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;


}
