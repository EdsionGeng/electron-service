package com.people.rent.convert;

import com.rent.model.bo.CouponTemplateBO;
import com.rent.model.bo.CouponTemplatePageBO;
import com.rent.model.dataobject.CouponTemplateDO;
import com.rent.model.dto.CouponCardTemplateAddDTO;
import com.rent.model.dto.CouponCardTemplateUpdateDTO;
import com.rent.model.dto.CouponCodeTemplateAddDTO;
import com.rent.model.dto.CouponCodeTemplateUpdateDTO;
import com.rent.model.vo.UsersCouponTemplateVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CouponTemplateConvert {
    Users USERS = Mappers.getMapper(Users.class);

    Admins ADMINS = Mappers.getMapper(Admins.class);

    CouponTemplateConvert INSTANCE = Mappers.getMapper(CouponTemplateConvert.class);

    @Mapper
    interface Admins {

//        @Mappings({})
//        AdminsCouponTemplateVO convert(CouponTemplateBO template);
//
//        @Mappings({})
//        AdminsCouponTemplatePageVO convertPage(CouponTemplatePageBO result);
//
//        @Mappings({})
//        List<AdminsCouponTemplateVO> convertList(List<CouponTemplateBO> templates);

    }

    @Mapper
    interface Users {

        @Mappings({})
        UsersCouponTemplateVO convert2(CouponTemplateBO template);

    }

    @Mappings({})
    List<CouponTemplateBO> convertToBO(List<CouponTemplateDO> templateList);

    @Mappings({})
    CouponTemplateDO convert(CouponCodeTemplateUpdateDTO template);

    @Mappings({})
    CouponTemplateDO convert(CouponCardTemplateAddDTO template);

    @Mappings({})
    CouponTemplateDO convert(CouponCardTemplateUpdateDTO template);

    @Mappings({})
    CouponTemplateDO convert(CouponCodeTemplateAddDTO template);

    @Mappings({})
    CouponTemplateBO convert(CouponTemplateDO template);
}
