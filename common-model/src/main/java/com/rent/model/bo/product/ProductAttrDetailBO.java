package com.rent.model.bo.product;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品规格明细 VO
 */
@Data
@Accessors(chain = true)
public class ProductAttrDetailBO implements Serializable {

    /**
     * 规格编号
     */
    private Integer id;
    /**
     * 规格名
     */
    private String name;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 规格值数组
     */
    private List<ProductAttrValueDetailBO> values;

}
