package com.people.rent.convert;

import com.rent.model.bo.product.ProductAttrBO;
import com.rent.model.bo.product.ProductAttrPageBO;
import com.rent.model.bo.product.ProductAttrSimpleBO;
import com.rent.model.bo.product.ProductAttrValueBO;
import com.rent.model.vo.adminprodcut.admins.AdminsProductAttrPageVO;
import com.rent.model.vo.adminprodcut.admins.AdminsProductAttrSimpleVO;
import com.rent.model.vo.adminprodcut.admins.AdminsProductAttrVO;
import com.rent.model.vo.adminprodcut.admins.AdminsProductAttrValueVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductAttrConvert {

    ProductAttrConvert INSTANCE = Mappers.getMapper(ProductAttrConvert.class);

    @Mappings({})
    AdminsProductAttrPageVO convert2(ProductAttrPageBO result);

    @Mappings({})
    List<AdminsProductAttrSimpleVO> convert(List<ProductAttrSimpleBO> result);

    @Mappings({})
    AdminsProductAttrVO convert3(ProductAttrBO productAttrBO);

    @Mappings({})
    AdminsProductAttrValueVO convert4(ProductAttrValueBO productAttrValueBO);

}
