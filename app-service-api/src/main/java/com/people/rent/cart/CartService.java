package com.people.rent.cart;

import com.people.rent.product.ProductSpuService;
import com.rent.model.bo.ProductSkuBO;
import com.rent.model.constant.CartItemStatusEnum;
import com.rent.model.constant.CommonStatusEnum;
import com.rent.model.constant.OrderErrorCodeEnum;
import com.rent.model.dataobject.CartItemDO;
import com.rent.util.utils.ServiceExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartService {
    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private CartMapper cartMapper;


    @SuppressWarnings("Duplicates")
    public Boolean add(Integer userId, Integer skuId, Integer quantity) {
        // 查询 SKU 是否合法
        ProductSkuBO sku = productSpuService.getProductSku(skuId);
        if (sku == null
                || CommonStatusEnum.DISABLE.getValue().equals(sku.getStatus())) { // sku 被禁用
            throw ServiceExceptionUtil.exception(OrderErrorCodeEnum.CARD_ITEM_SKU_NOT_FOUND.getCode());
        }
        // TODO 芋艿，后续基于商品是否上下架进一步完善。
        // 查询 CartItemDO
        CartItemDO item = cartMapper.selectByUserIdAndSkuIdAndStatus(userId, skuId, CartItemStatusEnum.ENABLE.getValue());
        // 存在，则进行数量更新
        if (item != null) {
            return updateQuantity0(item, sku, quantity);
        }
        // 不存在，则进行插入
        return add0(userId, sku, quantity);
    }

    private Boolean add0(Integer userId, ProductSkuBO sku, Integer quantity) {
        // 校验库存
        if (quantity > sku.getQuantity()) {
            throw ServiceExceptionUtil.exception(OrderErrorCodeEnum.CARD_ITEM_SKU_NOT_FOUND.getCode());
        }
        // 创建 CartItemDO 对象，并进行保存。
        CartItemDO item = new CartItemDO()
                // 基础字段
                .setStatus(CartItemStatusEnum.ENABLE.getValue()).setSelected(true)
                // 买家信息
                .setUserId(userId)
                // 商品信息
                .setSpuId(sku.getSpuId()).setSkuId(sku.getId()).setQuantity(quantity);
        item.setCreateTime(new Date());
        cartMapper.insert(item);
        // 返回成功
        return true;
    }

}
