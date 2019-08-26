package com.people.rent.convert;


import com.rent.model.bo.BannerBO;
import com.rent.model.dataobject.BannerDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BannerConvert {

    BannerConvert INSTANCE = Mappers.getMapper(BannerConvert.class);

    @Mappings({})
    BannerBO convertToBO(BannerDO banner);

    @Mappings({})
    List<BannerBO> convertToBO(List<BannerDO> bannerList);

    @Mappings({})
    BannerDO convert(BannerAddDTO bannerAddDTO);

    @Mappings({})
    BannerDO convert(BannerUpdateDTO bannerUpdateDTO);

}