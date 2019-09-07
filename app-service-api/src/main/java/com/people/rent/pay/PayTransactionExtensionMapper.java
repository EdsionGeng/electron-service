package com.people.rent.pay;

import com.rent.model.dataobject.transaction.PayTransactionExtensionDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayTransactionExtensionMapper {

    void insert(PayTransactionExtensionDO entity);

    int update(@Param("entity") PayTransactionExtensionDO entity,
               @Param("whereStatus") Integer whereStatus);

    PayTransactionExtensionDO selectByTransactionCode(@Param("transactionCode") String transactionCode);

    PayTransactionExtensionDO selectById(@Param("id") Integer id);

}
