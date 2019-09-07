package com.rent.model.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品推荐 DO
 */
@Data
@Accessors(chain = true)
public class ProductRecommendDO extends DeletableDO {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 类型
     *
     *
     */
    private Integer type;
    /**
     * 商品 Spu 编号
     */
    private Integer productSpuId;
    // TODO 芋艿，商品 spu 名
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态
     *
     * {@link cn.iocoder.common.framework.constant.CommonStatusEnum}
     */
    private Integer status;
    /**
     * 备注
     */
    private String memo;

}
