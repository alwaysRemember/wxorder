package com.thj.wxorder.util;

import com.thj.wxorder.enums.CodeEnum;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/8 14:05
 **/

public class EnumUtil {

    // 获取枚举
    public static <T extends CodeEnum>T getByCode(Integer code, Class<T> enumClass){
        for (T each: enumClass.getEnumConstants()){
            if ( code.equals(each.getCode()) ){
                return each;
            }
        }
        return null;
    }
}
