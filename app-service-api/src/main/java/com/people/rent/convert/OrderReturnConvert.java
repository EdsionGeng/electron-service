package com.people.rent.convert;

import com.rent.model.bo.OrderReturnInfoBO;
import com.rent.model.bo.OrderReturnListBO;
import com.rent.model.dataobject.OrderItemDO;
import com.rent.model.dataobject.OrderReturnDO;
import com.rent.model.dto.OrderReturnApplyDTO;
import com.rent.model.dto.OrderReturnCreateDTO;
import com.rent.model.dto.OrderReturnQueryDTO;
import com.rent.model.po.OrderReturnApplyPO;
import com.rent.model.po.OrderReturnQueryPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 订单 return
 *
 * @author Sin
 * @time 2019-03-30 15:46
 */
@Mapper
public interface OrderReturnConvert {

    OrderReturnConvert INSTANCE = Mappers.getMapper(OrderReturnConvert.class);

    @Mappings({})
    OrderReturnDO convert(OrderReturnCreateDTO orderReturnCreate);

    @Mappings({})
    OrderReturnDO convert(OrderReturnApplyDTO orderReturnApplyDTO);

    @Mappings({})
    OrderReturnInfoBO.ReturnInfo convert(OrderReturnDO orderReturnDO);

    @Mappings({})
    List<OrderReturnInfoBO.OrderItem> convert(List<OrderItemDO> orderItemDOList);

    @Mappings({})
    List<OrderReturnListBO.OrderReturn> convertListBO(List<OrderReturnDO> orderReturnDOList);

    @Mappings({})
    OrderReturnApplyDTO convert(OrderReturnApplyPO orderReturnApplyPO);

    @Mappings({})
    OrderReturnQueryDTO convert(OrderReturnQueryPO orderReturnQueryPO);
}
