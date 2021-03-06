package com.miaosha.miaoshaproduct.controller;


import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.domain.entity.User;
import com.miaosha.miaoshaproduct.service.IUserService;
import com.miaosha.miaoshaproduct.service.OrderFeignService;
import com.miaosha.miaoshaproduct.service.ProductFeignService;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户服务 下订单
 *
 * @author chenqi
 * @return
 * @date 2021/3/1 15:11
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private OrderFeignService orderFeignService;

    @Autowired
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    private ProductFeignService productFeignService;

    /**
     * 用户下订单
     * 1. 配置了限流,并持久化到nacos上
     * 2. 降级需要单独使用@SentinelResource,保证 参数一致 返回值一致
     *
     * @return java.lang.String
     * @author chenqi
     * @date 2021/3/1 15:13
     */
    @RequestMapping(value = "/placeOrder", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public CommonResult placeOrder(String productId) {
        try {
            CommonResult<ProductDTO> productDTOCommonResult = productFeignService.findProductById(Long.valueOf(productId));
            if (productDTOCommonResult.getCode() != 200) {
                return productDTOCommonResult;
            }
            ProductDTO productDTO = productDTOCommonResult.getData();

            CommonResult<OrderDTO> commonResult = userService.userPlaceOrder(productDTO);


            CommonResult result = orderFeignService.placeOrder(commonResult.getData());
            if (result.getCode() != 200) {
                return result;
            }
            return CommonResult.success(null);
        } catch (Exception e) {
            logger.error("用户服务异常;", e);
            return CommonResult.failed("用户服务异常");
        }
    }

    /**
     * 用户支付订单
     *
     * @param productId
     * @return com.miaosha.miaoshaproduct.utils.CommonResult
     * @author chenqi
     * @date 2021/3/5 10:47
     */
    @RequestMapping(value = "/payOrder", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public CommonResult payOrder(String productId) {
        //1. 校验订单状态
        //2. 判断用户余额是否足够
        //3. 修改订单状态
        return CommonResult.success(null);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public CommonResult getUserById(@PathVariable("id") Long id) {
        User user=userService.getUserById(id);
        return CommonResult.success(user);
    }

}
