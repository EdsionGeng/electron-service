package com.people.rent.convert;

import com.rent.model.bo.CalcSkuPriceBO;
import com.rent.model.bo.ProductBO;
import com.rent.model.bo.ProductSpuDetailBO;
import com.rent.model.bo.PromotionActivityBO;
import com.rent.model.dataobject.ESProductDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductSearchConvert {

    ProductSearchConvert INSTANCE = Mappers.getMapper(ProductSearchConvert.class);

    @Mappings({})
    ESProductDO convert(ProductSpuDetailBO spu);

    @Mappings({})
    default ESProductDO convert(ProductSpuDetailBO spu, CalcSkuPriceBO calcSkuPrice) {
        // Spu 的基础数据
        ESProductDO product = this.convert(spu);
        product.setOriginalPrice(calcSkuPrice.getOriginalPrice()).setBuyPrice(calcSkuPrice.getBuyPrice());
        // 设置促销活动相关字段
        if (calcSkuPrice.getTimeLimitedDiscount() != null) {
            PromotionActivityBO activity = calcSkuPrice.getTimeLimitedDiscount();
            product.setPromotionActivityId(activity.getId()).setPromotionActivityTitle(activity.getTitle())
                    .setPromotionActivityType(activity.getActivityType());
        }
        // 返回
        return product;
    }

    List<ProductBO> convert(List<ESProductDO> list);

}
