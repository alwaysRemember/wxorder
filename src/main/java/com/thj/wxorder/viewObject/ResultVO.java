package com.thj.wxorder.viewObject;

import lombok.Data;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/3 10:18
 * 接口返回结构
 **/

@Data
public class ResultVO<T> {

    /*状态码*/
    private Integer code;

    /*提示信息*/
    protected String msg;

    /*具体内容*/
    private T Data;

}
