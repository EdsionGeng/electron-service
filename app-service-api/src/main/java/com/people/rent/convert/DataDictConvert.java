package com.people.rent.convert;

import com.rent.model.bo.DataDictBO;
import com.rent.model.vo.DataDictEnumVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DataDictConvert {

    DataDictConvert INSTANCE = Mappers.getMapper(DataDictConvert.class);

    @Mappings({})
    List<DataDictEnumVO.Value> convert2(List<DataDictBO> dataDictBOs);

}
