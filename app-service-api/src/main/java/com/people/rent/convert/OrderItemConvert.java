package com.people.rent.convert;


import com.rent.model.bo.OrderInfoBO;
import com.rent.model.bo.OrderItemBO;
import com.rent.model.dataobject.OrderItemDO;
import com.rent.model.dto.OrderCreateDTO;
import com.rent.model.dto.OrderItemUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 订单 item convert
 *
 * @author Sin
 * @time 2019-03-23 14:34
 */
@Mapper
public interface OrderItemConvert {

    OrderItemConvert INSTANCE = Mappers.getMapper(OrderItemConvert.class);

    @Mappings({})
    OrderItemDO convert(OrderItemUpdateDTO orderItemUpdateDTO);

    @Mappings({})
    List<OrderItemBO> convertOrderItemBO(List<OrderItemDO> orderItemDOList);

    @Mappings({})
    List<OrderItemDO> convert(List<OrderCreateDTO.OrderItem> orderCreateItemDTOList);

    @Mappings({})
    List<OrderItemBO> convertOrderItemDO(List<OrderItemDO> orderItemDOList);

    @Mappings({})
    List<OrderInfoBO.OrderItem> convertOrderInfoWithOrderItem(List<OrderItemDO> orderItemDOList);
}
