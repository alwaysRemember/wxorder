package com.thj.wxorder.controller;

import com.thj.wxorder.backstage.OrderBackstage;
import com.thj.wxorder.backstage.OrderDetailBackstage;
import com.thj.wxorder.backstage.ProductInfoBackstage;
import com.thj.wxorder.dataobject.ProductCategory;
import com.thj.wxorder.dataobject.ProductInfo;
import com.thj.wxorder.dto.OrderDTO;
import com.thj.wxorder.enums.ProductCategoryEnum;
import com.thj.wxorder.exception.WxorderException;
import com.thj.wxorder.service.*;
import com.thj.wxorder.util.ResultVOUtil;
import com.thj.wxorder.viewObject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 卖家端订单
 *
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/7 10:00
 **/


@RestController
@RequestMapping("/seller/order")
@Slf4j
public class WxOrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderBackstageService orderBackstageService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductBackstageService productBackstageService;

    @Autowired
    private ProductCategoryService productCategoryService;


    /**
     * 订单列表
     *
     * @param page 页数 从第一页开始
     * @param size 条数
     * @return
     */
    @GetMapping("/list")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        Page<OrderBackstage> orderBackstagePage = orderBackstageService.findList(page, size);

        return resultVOUtil.success(orderBackstagePage);

    }

    /**
     * 取消订单
     *
     * @param orderId 订单id
     * @return
     */
    @GetMapping("/cancel")
    public ResultVO cancel(@RequestParam(value = "orderId", defaultValue = "") String orderId) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        OrderDTO orderDTO;
        if ( orderId.isEmpty() ) {
            return resultVOUtil.error(-1, "数据出错");
        }

        try {
            orderDTO = orderService.cancel(orderService.findOne(orderId));
        } catch (WxorderException e) {
            log.error(new Date() + "【卖家端取消订单】" + e.getMessage() + "orderId={}", orderId);
            return resultVOUtil.error(-1, e.getMessage());
        }


        return resultVOUtil.success(orderDTO);

    }


    /**
     * 订单详情
     *
     * @param orderId 订单id
     * @return
     */
    @GetMapping("/orderDetail")
    public ResultVO orderDetail(@RequestParam(value = "orderId", defaultValue = "") String orderId) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        if ( orderId.isEmpty() ) {
            return resultVOUtil.error(-1, "数据出错");
        }
        OrderDetailBackstage orderDetailBackstage;
        try {
            orderDetailBackstage = orderBackstageService.orderDetail(orderId);
        } catch (WxorderException e) {
            return resultVOUtil.error(-1, e.getMessage());
        }


        return resultVOUtil.success(orderDetailBackstage);

    }


    /**
     * 完结订单
     *
     * @param orderId 订单id
     * @return
     */
    @GetMapping("/finish")
    public ResultVO finish(@RequestParam(value = "orderId", defaultValue = "") String orderId) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        OrderDTO orderDTO;

        if ( orderId.isEmpty() ) {
            return resultVOUtil.error(-1, "数据出错");
        }


        try {
            orderDTO = orderService.finish(orderService.findOne(orderId));
        } catch (WxorderException e) {
            return resultVOUtil.error(-1, e.getMessage());
        }

        return resultVOUtil.success(orderDTO);
    }


    /**
     * 获取所有商品
     *
     * @param page 页数
     * @param size 数量
     * @return
     */
    @GetMapping("/getProductInfoList")
    public ResultVO getProductInfoList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);

        return resultVOUtil.success(productInfoPage);
    }

    /**
     * 获取上架的商品列表
     *
     * @return
     */
    @GetMapping("/getOnShelfProductInfoList")
    public ResultVO getOnShelfProductInfoList() {
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        List<ProductInfo> productInfoPage = productService.findUpAll();

        return resultVOUtil.success(productInfoPage);
    }

    /**
     * 上架商品
     *
     * @param productId 商品ID
     * @return
     */
    @PostMapping("/onShelfProduct")
    public ResultVO onShelfProduct(@RequestParam(value = "productId", defaultValue = "") String productId) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        ProductInfo productInfo;
        try {
            productInfo = productService.onShelfProduct(productId);
        } catch (WxorderException e) {
            return resultVOUtil.error(-1, e.getMessage());
        }


        return resultVOUtil.success(productInfo);
    }

    /**
     * 下架商品
     *
     * @param productId 商品ID
     * @return
     */
    @PostMapping("/downShelfProduct")
    public ResultVO downShelfProduct(@RequestParam(value = "productId", defaultValue = "") String productId) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        ProductInfo productInfo;
        try {
            productInfo = productService.downShelfProduct(productId);
        } catch (WxorderException e) {
            return resultVOUtil.error(-1, e.getMessage());
        }

        return resultVOUtil.success(productInfo);
    }


    /**
     * 添加商品
     *
     * @param productInfoBackstage 商品数据
     * @return
     */
    @PostMapping("/addProduct")
    public ResultVO addProduct(@Valid @RequestBody ProductInfoBackstage productInfoBackstage, BindingResult bindingResult) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();
        if ( bindingResult.hasErrors() ) {
            log.error(new Date() + "【添加商品】添加商品失败，参数不正确,productInfoBackstage={}", productInfoBackstage);
            return resultVOUtil.error(-1, "添加商品失败，参数不正确");
        }
        ProductInfo result;

        try {
            result = productBackstageService.addProduct(productInfoBackstage);
        } catch (WxorderException e) {
            return resultVOUtil.error(-1, e.getMessage());
        }

        return resultVOUtil.success(result);
    }

    /**
     * 删除商品
     *
     * @param productId 商品ID
     * @return
     */
    @PostMapping("/deleteProduct")
    public ResultVO deleteProduct(@RequestParam(value = "productId", defaultValue = "") String productId) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        if ( productId.isEmpty() ) {
            log.error(new Date() + "【删除商品】删除商品失败，参数不正确,productId={}", productId);
            return resultVOUtil.error(-1, "参数不正确");
        }
        Integer integer;
        try {
            integer = productService.deleteProduct(productId);
        } catch (WxorderException e) {
            return resultVOUtil.error(-1, e.getMessage());
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("deleteNum", integer);
        return resultVOUtil.success(result);
    }

    /**
     * 更新商品
     *
     * @param productInfo 商品信息
     * @return
     */
    @PostMapping("/updateProduct")
    public ResultVO updateProduct(@Valid @RequestBody ProductInfo productInfo, BindingResult bindingResult) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        if ( bindingResult.hasErrors() ) {
            log.error(new Date() + "【更新商品】更新商品失败，参数不正确，productInfo={}", productInfo);
            return resultVOUtil.error(-1, "更新商品失败，参数不正确");
        }

        ProductInfo productInfo1;

        try {
            productInfo1 = productBackstageService.updateProduct(productInfo);
        } catch (WxorderException e) {
            return resultVOUtil.error(-1, e.getMessage());
        }

        return resultVOUtil.success(productInfo1);
    }


    /**
     * 添加类目
     *
     * @param productCategory 类目信息
     * @return
     */
    @PostMapping("/addCategory")
    public ResultVO addCategory(@Valid @RequestBody ProductCategory productCategory, BindingResult bindingResult) {
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        if ( bindingResult.hasErrors() ) {
            log.error(new Date() + "【添加类目】" + ProductCategoryEnum.PARAMS_ERROR.getMsg());
            return resultVOUtil.error(-1, ProductCategoryEnum.PARAMS_ERROR.getMsg());
        }

        ProductCategory result;
        try {
            result = productCategoryService.save(productCategory);
        }catch (WxorderException e){
            return resultVOUtil.error(-1,e.getMessage());
        }


        return resultVOUtil.success(result);
    }

    /**
     * 更新类目
     * @param productCategory
     * @param bindingResult
     * @return
     */
    @PostMapping("/updateCategory")
    public ResultVO updateCategory(@Valid @RequestBody ProductCategory productCategory , BindingResult bindingResult){
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        if ( bindingResult.hasErrors() ){
            log.error(new Date() + "【更新类目】" + ProductCategoryEnum.PARAMS_ERROR.getMsg());
            return resultVOUtil.error(-1, ProductCategoryEnum.PARAMS_ERROR.getMsg());
        }

        ProductCategory result;
        try {
            result = productCategoryService.update(productCategory);
        }catch (WxorderException e){
            return resultVOUtil.error(-1,e.getMessage());
        }


        return resultVOUtil.success(result);
    }

    /**
     * 类目列表
     * @param page  页数
     * @param size  数据条数
     * @return
     */
    @GetMapping("/categoryPage")
    public ResultVO categoryPage(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "size",defaultValue = "10") Integer size){

        ResultVOUtil resultVOUtil = new ResultVOUtil();

        PageRequest pageRequest = new PageRequest(page-1,size);

        Page<ProductCategory> productCategoryPage;

        try {
            productCategoryPage= productCategoryService.categoryPage(pageRequest);
        } catch (WxorderException e){
            return resultVOUtil.error(-1,e.getMessage());
        }
        return resultVOUtil.success(productCategoryPage);
    }

    /**
     * 查找列表
     * @param categoryName  查找值（类目名字）
     * @return
     */
    @PostMapping("/findList")
    public ResultVO findList(@RequestParam(value = "categoryName",defaultValue = "") String categoryName){
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        List<ProductCategory> productCategoryList;

        try {
            productCategoryList= productCategoryService.findList(categoryName);
        }catch (WxorderException e){
            return resultVOUtil.error(-1,e.getMessage());
        }

        return resultVOUtil.success(productCategoryList);
    }

    /**
     * 删除类目
     * @param categoryId    类目ID
     * @return
     */
    @PostMapping("/deleteCategory")
    public ResultVO deleteCategory(@RequestParam(value = "categoryId")Integer categoryId){
        ResultVOUtil resultVOUtil = new ResultVOUtil();

        ProductCategory productCategory = productCategoryService.deleteCategory(categoryId);

        return resultVOUtil.success(productCategory);
    }
}
