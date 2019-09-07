package com.people.rent.convert.pay;


import com.rent.model.bo.PayRefundBO;
import com.rent.model.dataobject.transaction.PayRefundDO;
import com.rent.model.dto.transaction.PayRefundSubmitDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface PayRefundConvert {

    PayRefundConvert INSTANCE = Mappers.getMapper(PayRefundConvert.class);

    @Mappings({})
    PayRefundDO convert(PayRefundSubmitDTO payRefundSubmitDTO);

    @Mappings({})
    PayRefundBO convert(PayRefundDO refund);

    @Mappings({})
    List<PayRefundBO> convertList(List<PayRefundDO> refunds);

}
