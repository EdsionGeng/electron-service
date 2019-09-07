package com.rent.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品推荐 BO
 */
@Data
@Accessors(chain = true)
public class ProductRecommendBO implements Serializable {

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
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态
     *
     *
     */
    private Integer status;
    /**
     * 备注
     */
    private String memo;
    /**
     * 创建时间
     */
    private Date createTime;

}
