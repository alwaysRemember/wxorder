package com.thj.wxorder.util;

import java.util.Random;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/5 15:26
 **/

public class KeyUtil {

    /**
     * 生成唯一主键
     * 格式：时间加随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();



        Integer number = random.nextInt(900000)+100000;

        return  String.valueOf(number)+System.currentTimeMillis();

    }
}
