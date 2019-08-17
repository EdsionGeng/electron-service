package com.rent.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 商品 Sku 明细 BO
 */
@Data
@Accessors(chain = true)
public class ProductSkuDetailVO {

    @ApiModelProperty(value = "sku 编号", required = true, example = "1")
    private Integer id;
    @ApiModelProperty(value = "SPU 编号", required = true, example = "1")
    private Integer spuId;
    @ApiModelProperty(value = "图片地址", required = true, example = "http://www.iocoder.cn")
    private String picURL;
//    @ApiModelProperty(value = "规格值数组", required = true)
//    private List<UsersProductAttrAndValuePairVO> attrs;
    @ApiModelProperty(value = "价格，单位：分", required = true, example = "100")
    private Integer price;

}
