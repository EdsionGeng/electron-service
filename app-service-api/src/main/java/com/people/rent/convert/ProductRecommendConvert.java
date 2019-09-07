package com.people.rent.convert;

import com.rent.model.bo.ProductRecommendBO;
import com.rent.model.bo.ProductRecommendPageBO;
import com.rent.model.bo.ProductSpuBO;
import com.rent.model.dataobject.ProductRecommendDO;
import com.rent.model.dto.ProductRecommendAddDTO;
import com.rent.model.dto.ProductRecommendUpdateDTO;
import com.rent.model.vo.AdminsProductRecommendPageVO;
import com.rent.model.vo.AdminsProductRecommendVO;
import com.rent.model.vo.UsersProductRecommendVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductRecommendConvert {

    ProductRecommendConvert INSTANCE = Mappers.getMapper(ProductRecommendConvert.class);

    @Mappings({})
    AdminsProductRecommendVO convert(ProductRecommendBO bannerBO);

    @Mappings({})
    AdminsProductRecommendPageVO convert(ProductRecommendPageBO result);

    @Mappings({})
    UsersProductRecommendVO convert(ProductSpuBO productSpu);



    @Mappings({})
    ProductRecommendBO convertToBO(ProductRecommendDO recommend);

    @Mappings({})
    List<ProductRecommendBO> convertToBO(List<ProductRecommendDO> recommendList);

    @Mappings({})
    ProductRecommendDO convert(ProductRecommendAddDTO recommendAddDTO);

    @Mappings({})
    ProductRecommendDO convert(ProductRecommendUpdateDTO recommendUpdateDTO);

    //    @Mappings({})
    //    List<UsersProductRecommendVO> convertList(List<ProductRecommendBO> banners);

}
