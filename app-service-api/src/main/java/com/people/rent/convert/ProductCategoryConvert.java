package com.people.rent.convert;

import com.rent.model.bo.ProductCategoryBO;
import com.rent.model.dataobject.ProductCategoryDO;

import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface ProductCategoryConvert {
    ProductCategoryConvert INSTANCE = Mappers.getMapper(ProductCategoryConvert.class);
    @Mappings({})
    ProductCategoryBO convertToBO(ProductCategoryDO category);

    @Mappings({})
    List<ProductCategoryBO> convertToBO(List<ProductCategoryDO> categoryList);

//    @Mappings({})
//    ProductCategoryDO convert(ProductCategoryAddDTO productCategoryAddDTO);
//
//    @Mappings({})
//    ProductCategoryDO convert(ProductCategoryUpdateDTO productCategoryUpdateDTO);

}
