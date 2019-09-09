package com.people.rent.cart;


import com.people.rent.convert.CartConvert;
import com.people.rent.coupon.CouponService;
import com.people.rent.order.OrderService;
import com.rent.model.CommonResult;
import com.rent.model.bo.CalcOrderPriceBO;
import com.rent.model.bo.CalcSkuPriceBO;
import com.rent.model.bo.CartItemBO;
import com.rent.model.bo.CouponCardAvailableBO;
import com.rent.model.dto.CalcOrderPriceDTO;
import com.rent.model.vo.UsersCalcSkuPriceVO;
import com.rent.model.vo.UsersCartDetailVO;
import com.rent.model.vo.UsersOrderConfirmCreateVO;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.rent.model.CommonResult.success;

@RestController
public class UserCartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private CouponService couponService;

    @PostMapping("add")
    public CommonResult<Integer> add(@RequestParam("skuId") Integer skuId,
                                     @RequestParam("quantity") Integer quantity,
                                     @RequestParam("timeId") Integer timeId,
                                     @RequestParam("startTime") Date startTime,
                                     @Param("endTime") Date endTime) {
        // 添加到购物车
        this.cartService.add(null, skuId, quantity, timeId, startTime, endTime);
        // 获得目前购物车商品总数量
        return success(cartService.count(null));
    }

    @PostMapping("update_quantity")
    public CommonResult<UsersCartDetailVO> updateQuantity(@RequestParam("skuId") Integer skuId, // TODO 芋艿，先暂用这个 VO 。等促销活动出来后，做调整
                                                          @RequestParam("quantity") Integer quantity,
                                                          @RequestParam("timeId") Integer timeId,
                                                          @RequestParam("startTime") Date startTime,
                                                          @Param("endTime") Date endTime) {
        // 添加到购物车
        this.cartService.updateQuantity(null,
                skuId, quantity,timeId,startTime,endTime);
        // 获得目前购物车明细
        return getCartDetail();
    }

    @PostMapping("update_selected")
    public CommonResult<UsersCartDetailVO> updateSelected(@RequestParam("skuIds") Set<Integer> skuIds, // TODO 芋艿，先暂用这个 VO 。等促销活动出来后，做调整
                                                          @RequestParam("selected") Boolean selected) {
        // 添加到购物车
        this.cartService.updateSelected(null, skuIds, selected);
        // 获得目前购物车明细
        return getCartDetail();
    }

    @GetMapping("count")
    public CommonResult<Integer> count() {
        return success(this.cartService.count(null));
    }

    @GetMapping("/list")

    public CommonResult<UsersCartDetailVO> list() { // TODO 芋艿，先暂用这个 VO 。等促销活动出来后，做调整
        return getCartDetail();
    }

    private CommonResult<UsersCartDetailVO> getCartDetail() {
        // 获得购物车中选中的
        List<CartItemBO> cartItems = cartService.list(null, null);
        // 购物车为空时，构造空的 UsersOrderConfirmCreateVO 返回
        if (cartItems.isEmpty()) {
            UsersCartDetailVO result = new UsersCartDetailVO();
            result.setItemGroups(Collections.emptyList());
            result.setFee(new UsersCartDetailVO.Fee(0, 0, 0, 0));
            return success(result);
        }
        // 计算商品价格
        CalcOrderPriceBO calcOrder = list0(cartItems, null);
        // 执行数据拼装
        return success(CartConvert.INSTANCE.convert2(calcOrder));
    }

    @GetMapping("/confirm_create_order")
    public CommonResult<UsersOrderConfirmCreateVO> getConfirmCreateOrder(@RequestParam(value = "couponCardId", required = false) Integer couponCardId) {
        Integer userId = null;
        // 获得购物车中选中的
        List<CartItemBO> cartItems = cartService.list(userId, true);
        // 购物车为空时，构造空的 UsersOrderConfirmCreateVO 返回
        if (cartItems.isEmpty()) {
            UsersOrderConfirmCreateVO result = new UsersOrderConfirmCreateVO();
            result.setItemGroups(Collections.emptyList());
            result.setFee(new UsersOrderConfirmCreateVO.Fee(0, 0, 0, 0));
            return success(result);
        }
        // 计算商品价格
        CalcOrderPriceBO calcOrderPrice = list0(cartItems, couponCardId);
        // 获得优惠劵
        List<CouponCardAvailableBO> couponCards = couponService.getCouponCardList(userId,
                CartConvert.INSTANCE.convertList(calcOrderPrice.getItemGroups()));
        // 执行数据拼装
        return success(CartConvert.INSTANCE.convert(calcOrderPrice).setCouponCards(couponCards));
    }

    private CalcOrderPriceBO list0(List<CartItemBO> cartItems, Integer couponCardId) {
        // 创建计算的 DTO
        CalcOrderPriceDTO calcOrderPriceDTO = new CalcOrderPriceDTO()
                .setUserId(null)
                .setItems(new ArrayList<>(cartItems.size()))
                .setCouponCardId(couponCardId);
        for (CartItemBO item : cartItems) {
            calcOrderPriceDTO.getItems().add(new CalcOrderPriceDTO.Item(item.getSkuId(), item.getQuantity(), item.getSelected(),
                    item.getTimeId(), item.getStartTime(), item.getEndTime()));
        }
        // 执行计算
        return cartService.calcOrderPrice(calcOrderPriceDTO);
    }

    @GetMapping("/calc_sku_price")
    public CommonResult<UsersCalcSkuPriceVO> calcSkuPrice(@RequestParam("skuId") Integer skuId) {
        // 计算 sku 的价格
        CalcSkuPriceBO calcSkuPrice = cartService.calcSkuPrice(skuId);
        return success(CartConvert.INSTANCE.convert2(calcSkuPrice));
    }

    public CommonResult<Object> confirmOrder() {
        // 查询购物车列表（选中的）
//        cartService.list(userId, true);
        // 查询确认订单信息的明细

        return null;
    }
}
