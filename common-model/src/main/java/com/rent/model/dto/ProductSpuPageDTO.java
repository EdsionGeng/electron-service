package com.rent.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 商品 Spu 分页 DTO
 */
@Data
@Accessors(chain = true)
public class ProductSpuPageDTO {

    /**
     * 商品名
     * <p>
     * 模糊匹配
     */
    private String name;
    /**
     * 分类编号
     */
    private Integer cid;
    /**
     * 是否可见
     */
    private Boolean visible;
    /**
     * 是否有库存
     * <p>
     * 允许为空。空时，不进行筛选
     */
    private Boolean hasQuantity;

    /**
     * 商铺ID
     */
    private Integer shopId;

    @NotNull(message = "页码不能为空")
    private Integer pageNo;
    @NotNull(message = "每页条数不能为空")
    private Integer pageSize;

}
