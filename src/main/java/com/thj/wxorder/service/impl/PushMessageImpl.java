package com.thj.wxorder.service.impl;

import com.thj.wxorder.config.WechatAccountConfig;
import com.thj.wxorder.dto.OrderDTO;
import com.thj.wxorder.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/22 16:32
 **/
@Slf4j
@Service
public class PushMessageImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig accountConfig;


    // 订单消息推送
    @Override
    public void orderStatus(OrderDTO orderDTO) {

        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();

        // 模版id
        templateMessage.setTemplateId(accountConfig.getTemplateId());

        // openid
        templateMessage.setToUser("123123123123");

        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","亲，记得收货"),
                new WxMpTemplateData("keyword1","微信点餐"),
                new WxMpTemplateData("keyword2",":15945124512"),
                new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4",orderDTO.getOrderStatusEnum().getMsg()),
                new WxMpTemplateData("keyword5",orderDTO.getOrderAmount()+""),
                new WxMpTemplateData("remark","欢迎再次光临")
        );
        templateMessage.setData(data);

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }catch (WxErrorException e){
            log.error(new Date()+"【微信模版消息】，发送失败,{}",e);
        }
    }
}
