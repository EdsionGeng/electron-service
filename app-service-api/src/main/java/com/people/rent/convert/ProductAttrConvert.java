package com.people.rent.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductAttrConvert {

    ProductAttrConvert INSTANCE = Mappers.getMapper(ProductAttrConvert.class);

//    @Mappings({})
//    AdminsProductAttrPageVO convert2(ProductAttrPageBO result);
//
//    @Mappings({})
//    List<AdminsProductAttrSimpleVO> convert(List<ProductAttrSimpleBO> result);
//
//    @Mappings({})
//    AdminsProductAttrVO convert3(ProductAttrBO productAttrBO);
//
//    @Mappings({})
//    AdminsProductAttrValueVO convert4(ProductAttrValueBO productAttrValueBO);

}
