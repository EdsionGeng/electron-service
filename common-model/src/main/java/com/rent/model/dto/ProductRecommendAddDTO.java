package com.rent.model.dto;


import com.rent.model.constant.ProductRecommendTypeEnum;
import com.rent.model.validator.InEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 商品推荐添加 DTO
 */
@Data
@Accessors(chain = true)
public class ProductRecommendAddDTO implements Serializable {

    @InEnum(value = ProductRecommendTypeEnum.class, message = "修改推荐类型必须是 {value}")
    @NotNull(message = "推荐类型不能为空")
    private Integer type;
    @NotNull(message = "商品编号不能为空")
    private Integer productSpuId;
    @NotNull(message = "排序不能为空")
    private Integer sort;
    @Size(max = 255, message = "备注最大长度为 255 位")
    private String memo;

}
