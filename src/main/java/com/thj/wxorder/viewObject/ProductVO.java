package com.thj.wxorder.viewObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thj.wxorder.dataobject.ProductInfo;
import lombok.Data;

import java.util.List;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/3 10:26
 * 商品（包含类目）
 **/

@Data
public class ProductVO {

    /*类目名字*/
    @JsonProperty("name")
    private String categoryName;

    /*类目*/
    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
