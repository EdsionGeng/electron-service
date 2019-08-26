package com.people.rent.convert;

import com.rent.model.bo.ProductCategoryBO;
import com.rent.model.vo.UsersProductCategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface ProductCategoryConvert {

    @Mapper
    interface Users {

        Users INSTANCE = Mappers.getMapper(Users.class);

        @Mappings({})
        UsersProductCategoryVO convertToVO(ProductCategoryBO category);

        @Mappings({})
        List<UsersProductCategoryVO> convertToVO(List<ProductCategoryBO> categoryList);

    }

    @Mapper
    interface Admins {

        Admins INSTANCE = Mappers.getMapper(Admins.class);

//        @Mappings({})
//        AdminsProductCategoryTreeNodeVO convert(ProductCategoryBO category);
//
//        @Mappings({})
//        AdminsProductCategoryVO convert2(ProductCategoryBO result);

    }

}
