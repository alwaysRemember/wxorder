package com.thj.wxorder.dataobject;

import lombok.Data;

/**
 * Author yaer
 * 2018-07-01 15:53
 *
 * 接口返回数据模型,接收一个泛型
 */
@Data
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;



}
