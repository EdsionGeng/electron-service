package com.people.rent.convert;

import com.rent.model.bo.CouponCardAvailableBO;
import com.rent.model.bo.CouponCardBO;
import com.rent.model.bo.CouponCardDetailBO;
import com.rent.model.bo.CouponCardPageBO;
import com.rent.model.dataobject.CouponCardDO;
import com.rent.model.vo.UsersCouponCardPageVO;
import com.rent.model.vo.UsersCouponCardVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CouponCardConvert {

    CouponCardConvert INSTANCE = Mappers.getMapper(CouponCardConvert.class);

    @Mappings({})
    UsersCouponCardVO convert(CouponCardBO result);

    @Mappings({})
    UsersCouponCardPageVO convert2(CouponCardPageBO result);

    @Mappings({})
    List<CouponCardBO> convertToBO(List<CouponCardDO> cardList);

    @Mappings({})
    CouponCardBO convert(CouponCardDO card);

    @Mappings({})
    CouponCardDetailBO convert2(CouponCardDO card);

    @Mappings({})
    CouponCardAvailableBO convert2(CouponCardDO card,  boolean x); // TODO 芋艿，临时用来解决 mapstruct 无法正确匹配方法的问题
}
