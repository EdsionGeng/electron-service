package com.people.rent.datadict;

import com.rent.model.dataobject.DataDictDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DataDictMapper  {

    DataDictDO selectByEnumValueAndValue(
            @Param("enumValue") String enumValue,
            @Param("value") String value
    );

    List<DataDictDO> selectByEnumValueAndValues(
            @Param("enumValue") String enumValue,
            @Param("values") Collection<String> values
    );

    List<DataDictDO> selectByEnumValue(
            @Param("enumValue") String enumValue
    );

   List<DataDictDO> selectList();
   //{
   //     return selectList(new QueryWrapper<>());
  //  }

}
