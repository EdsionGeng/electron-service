package com.people.rent.choose;

import com.rent.model.dataobject.TimeChooseDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeChooseMapper {

    List<TimeChooseDO> selectTimeChooseList(Integer deleted);
}
