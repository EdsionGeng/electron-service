package com.rent.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠劵 BO
 */
@Data
@Accessors(chain = true)
public class CouponCardBO implements Serializable {

    // ========== 基本信息 BEGIN ==========
    /**
     * 优惠劵编号
     */
    private Integer id;
    /**
     * 优惠劵（码）分组编号
     */
    private Integer templateId;
    /**
     * 优惠劵名
     */
    private String title;
//    /**
//     * 核销码
//     */
//    private String verifyCode;
    /**
     * 优惠码状态
     *
     * 1-未使用
     * 2-已使用
     * 3-已失效
     */
    private Integer status;

    // ========== 基本信息 END ==========

    // ========== 领取情况 BEGIN ==========
    /**
     * 用户编号
     */
    private Integer userId;
    /**
     * 领取类型
     *
     * 1 - 用户主动领取
     * 2 - 后台自动发放
     */
    private Integer takeType;
    // ========== 领取情况 END ==========

    // ========== 使用规则 BEGIN ==========
    /**
     * 是否设置满多少金额可用，单位：分
     */
    private Integer priceAvailable;
    /**
     * 生效开始时间
     */
    private Date validStartTime;
    /**
     * 生效结束时间
     */
    private Date validEndTime;
    // ========== 使用规则 END ==========

    // ========== 使用效果 BEGIN ==========
    /**
     * 优惠类型
     *
     * 1-代金卷
     * 2-折扣卷
     */
    private Integer preferentialType;
    /**
     * 折扣
     */
    private Integer percentOff;
    /**
     * 优惠金额，单位：分。
     */
    private Integer priceOff;
    /**
     * 折扣上限，仅在 {@link #preferentialType} 等于 2 时生效。
     *
     * 例如，折扣上限为 20 元，当使用 8 折优惠券，订单金额为 1000 元时，最高只可折扣 20 元，而非 80  元。
     */
    private Integer discountPriceLimit;
    // ========== 使用效果 END ==========

    // ========== 使用情况 BEGIN ==========
    /**
     * 使用时间
     */
    private Date usedTime;

    // TODO 芋艿，后续要加优惠劵的使用日志，因为下单后，可能会取消。

    // ========== 使用情况 END ==========

    /**
     * 创建时间
     */
    private Date createTime;

}
