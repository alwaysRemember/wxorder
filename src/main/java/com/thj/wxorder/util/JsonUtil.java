package com.thj.wxorder.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON格式化工具
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/1 17:51
 **/

public class JsonUtil {

    // 对象转json方法
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return ((Gson) gson).toJson(object);
    }
}
