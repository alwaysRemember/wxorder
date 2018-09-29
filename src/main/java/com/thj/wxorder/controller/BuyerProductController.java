package com.thj.wxorder.controller;

import com.thj.wxorder.dataobject.ProductCategory;
import com.thj.wxorder.dataobject.ProductInfo;
import com.thj.wxorder.service.CategoryService;
import com.thj.wxorder.service.ProductService;
import com.thj.wxorder.util.ResultVOUtil;
import com.thj.wxorder.viewObject.ProductInfoVO;
import com.thj.wxorder.viewObject.ProductVO;
import com.thj.wxorder.viewObject.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/3 10:13
 * <p>
 * 买家商品API
 **/

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    public ResultVO list() {

        // 查所有上架商品

        List<ProductInfo> productInfoList = productService.findUpAll();

        // 查类目

        /*List<Integer> categoryTypeList = new ArrayList<>();

        for (ProductInfo productInfo : productInfoList) {
            categoryTypeList.add(productInfo.getCategoryType());
        }*/
        // 使用java8的lambda表达式
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);


        // 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        // 遍历productCategoryList 插入商品详情对象
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            // 遍历商品详情对象 如果 类目编号跟类目相同则创建详情对象并且添加数据
            for (ProductInfo productInfo : productInfoList) {
                if ( productInfo.getCategoryType().equals(productCategory.getCategoryType()) ) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    // COPY productInfo 添加到productInfoVO对象里面 spring的方法
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }

            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }





        return ResultVOUtil.success(productVOList);
    }

}
