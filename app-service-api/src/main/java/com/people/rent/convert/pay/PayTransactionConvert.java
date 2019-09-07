package com.people.rent.convert.pay;

import com.rent.model.bo.PayTransactionBO;
import com.rent.model.dataobject.transaction.PayTransactionDO;
import com.rent.model.dataobject.transaction.PayTransactionExtensionDO;
import com.rent.model.dto.transaction.PayTransactionCreateDTO;
import com.rent.model.dto.transaction.PayTransactionSubmitDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PayTransactionConvert {

    PayTransactionConvert INSTANCE = Mappers.getMapper(PayTransactionConvert.class);

    @Mappings({})
    PayTransactionDO convert(PayTransactionCreateDTO payTransactionCreateDTO);

    @Mappings({})
    PayTransactionBO convert(PayTransactionDO payTransactionDO);

    @Mappings({})
    List<PayTransactionBO> convertList(List<PayTransactionDO> list);

    @Mappings({})
    PayTransactionExtensionDO convert(PayTransactionSubmitDTO payTransactionSubmitDTO);

}
