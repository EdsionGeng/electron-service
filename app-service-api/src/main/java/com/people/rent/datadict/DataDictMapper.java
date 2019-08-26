package com.people.rent.datadict;

import com.rent.model.dataobject.DataDictDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataDictMapper  {

    List<DataDictDO> selectList();
}
