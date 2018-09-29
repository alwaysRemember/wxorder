package com.thj.wxorder.util;

import com.thj.wxorder.viewObject.ResultVO;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/3 11:41
 **/

public class ResultVOUtil {


    public static ResultVO success(Object object) {

        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;
    }

    public static ResultVO error(Integer code, String msg, Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setData(object);
        resultVO.setMsg(msg);

        return resultVO;
    }
}
