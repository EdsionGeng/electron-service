package com.people.rent.convert;


import com.rent.model.bo.BannerBO;
import com.rent.model.bo.BannerPageBO;
import com.rent.model.dataobject.BannerDO;
import com.rent.model.dto.BannerAddDTO;
import com.rent.model.dto.BannerUpdateDTO;
import com.rent.model.vo.AdminsBannerPageVO;
import com.rent.model.vo.AdminsBannerVO;
import com.rent.model.vo.UsersBannerVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BannerConvert {

    Users USERS = Mappers.getMapper(Users.class);

    Admins ADMINS = Mappers.getMapper(Admins.class);
    BannerConvert INSTANCE = Mappers.getMapper(BannerConvert.class);

    @Mappings({})
    BannerBO convertToBO(BannerDO banner);

    @Mappings({})
    List<BannerBO> convertToBO(List<BannerDO> bannerList);

    @Mappings({})
    BannerDO convert(BannerAddDTO bannerAddDTO);

    @Mappings({})
    BannerDO convert(BannerUpdateDTO bannerUpdateDTO);
    @Mapper
    interface Admins {

        @Mappings({})
        AdminsBannerVO convert(BannerBO bannerBO);

        @Mappings({})
        AdminsBannerPageVO convert3(BannerPageBO bannerPageBO);

    }

    @Mapper
    interface Users {

        @Mappings({})
        List<UsersBannerVO> convertList(List<BannerBO> banners);

    }
}