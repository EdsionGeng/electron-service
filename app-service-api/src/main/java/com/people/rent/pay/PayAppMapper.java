package com.people.rent.pay;

import com.rent.model.dataobject.transaction.PayAppDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayAppMapper {

    PayAppDO selectById(@Param("id") String id);
}
