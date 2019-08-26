package com.people.rent.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductBrandConvert {

    ProductBrandConvert INSTANCE = Mappers.getMapper(ProductBrandConvert.class);

//    @Mappings({})
//    AdminsProductBrandVO convert(ProductBrandBO result);
//
//    @Mappings({})
//    AdminsProductBrangPageVO convert(ProductBrangPageBO result);

}
