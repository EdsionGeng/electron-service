package com.people.rent.convert;

import com.rent.model.TimeChooseBO;
import com.rent.model.dataobject.TimeChooseDO;
import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TimeChooseConvert {

    TimeChooseConvert INSTANCE = Mappers.getMapper(TimeChooseConvert.class);

    @Mappings({})
    List<TimeChooseBO> convertUserAddressBOList(List<TimeChooseDO> userAddressDOList);
}
