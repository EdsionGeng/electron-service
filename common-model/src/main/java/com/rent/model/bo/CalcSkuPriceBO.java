package com.rent.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 计算商品 SKU 价格结果 BO
 */
@Data
@Accessors(chain = true)
public class CalcSkuPriceBO implements Serializable {

    /**
     * 满减送促销活动
     */
    private PromotionActivityBO fullPrivilege;
    /**
     * 限时折扣促销活动
     */
    private PromotionActivityBO timeLimitedDiscount;
    /**
     * 原价格，单位：分。
     */
    private Integer originalPrice;
    /**
     * 购买价格，单位：分。
     */
    private Integer buyPrice;

}
