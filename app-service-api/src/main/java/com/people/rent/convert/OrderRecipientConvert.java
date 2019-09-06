package com.people.rent.convert;

import com.rent.model.bo.OrderInfoBO;
import com.rent.model.bo.OrderRecipientBO;
import com.rent.model.bo.UserAddressBO;
import com.rent.model.dataobject.OrderRecipientDO;
import com.rent.model.dto.OrderCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 订单收件人信息
 *
 * @author Sin
 * @time 2019-03-31 12:50
 */
@Mapper
public interface OrderRecipientConvert {

    OrderRecipientConvert INSTANCE = Mappers.getMapper(OrderRecipientConvert.class);

    @Mappings({})
    OrderRecipientDO
    convert(OrderCreateDTO orderCreateDTO);

    @Mappings({})
    OrderRecipientDO convert(UserAddressBO userAddressBO);

    @Mappings({})
    OrderRecipientBO convert(OrderRecipientDO orderRecipientDO);

    @Mappings({})
    List<OrderRecipientBO> convert(List<OrderRecipientDO> orderRecipientDOList);

    @Mappings({})
    OrderInfoBO.Recipient convertOrderInfoRecipient(OrderRecipientDO orderRecipientDO);
}
