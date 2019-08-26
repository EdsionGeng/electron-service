package com.people.rent.convert;

import com.rent.model.dto.OrderReturnApplyDTO;
import com.rent.model.dto.OrderReturnQueryDTO;
import com.rent.model.po.OrderReturnApplyPO;
import com.rent.model.po.OrderReturnQueryPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 订单退货
 *
 * @author Sin
 * @time 2019-04-25 21:54
 */
@Mapper
public interface OrderReturnConvert {

    OrderReturnConvert INSTANCE = Mappers.getMapper(OrderReturnConvert.class);

    @Mappings({})
    OrderReturnApplyDTO convert(OrderReturnApplyPO orderReturnApplyPO);

    @Mappings({})
    OrderReturnQueryDTO convert(OrderReturnQueryPO orderReturnQueryPO);
}
