package com.people.rent.convert;

import com.rent.model.bo.OrderInfoBO;
import com.rent.model.dataobject.OrderLogisticsDetailDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 订单物流 convert
 *
 * @author Sin
 * @time 2019-03-23 14:39
 */
@Mapper
public interface OrderLogisticsDetailConvert {

    OrderLogisticsDetailConvert INSTANCE = Mappers.getMapper(OrderLogisticsDetailConvert.class);

    @Mappings({})
    OrderInfoBO.LogisticsDetail convertLogisticsDetail(OrderLogisticsDetailDO orderLogisticsDetailDO);
}
