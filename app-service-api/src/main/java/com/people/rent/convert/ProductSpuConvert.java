package com.people.rent.convert;


import com.rent.model.bo.ProductSpuBO;
import com.rent.model.bo.ProductSpuPageBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface ProductSpuConvert {

    ProductSpuConvert INSTANCE = Mappers.getMapper(ProductSpuConvert.class);

    @Mappings({})
    AdminsProductSpuDetailVO convert(ProductSpuDetailBO productSpuDetailBO);

//    @Mappings({})
//    CommonResult<AdminsProductSpuDetailVO> convert(CommonResult<ProductSpuDetailBO> result);

    @Mappings({})
    AdminsProductSpuPageVO convert2(ProductSpuPageBO result);

    @Mappings({})
    List<AdminsProductSpuVO> convert3(List<ProductSpuBO> result);

    @Mappings({})
    UsersProductSpuPageVO convert3(ProductSpuPageBO result);

    @Mappings({})
    UsersProductSpuDetailVO convert4(ProductSpuDetailBO result);

}
