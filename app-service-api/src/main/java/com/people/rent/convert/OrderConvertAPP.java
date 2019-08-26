package com.people.rent.convert;

import com.rent.model.bo.CartItemBO;
import com.rent.model.dto.OrderCreateDTO;
import com.rent.model.dto.OrderItemUpdateDTO;
import com.rent.model.dto.OrderLogisticsUpdateDTO;
import com.rent.model.dto.OrderQueryDTO;
import com.rent.model.po.OrderCreatePO;
import com.rent.model.po.OrderItemUpdatePO;
import com.rent.model.po.OrderLogisticsPO;
import com.rent.model.po.OrderPageQueryPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * application 订单 convert
 *
 * TODO 这种方式 文件名不能一样哈!
 *
 * @author Sin
 * @time 2019-03-24 10:45
 */
@Mapper
public interface OrderConvertAPP {

    OrderConvertAPP INSTANCE = Mappers.getMapper(OrderConvertAPP.class);

    @Mappings({})
    OrderQueryDTO convert(OrderPageQueryPO orderPageQueryVO);

    @Mappings({})
    OrderLogisticsUpdateDTO convert(OrderLogisticsPO orderLogisticsVO);

    @Mappings({})
    OrderItemUpdateDTO convert(OrderItemUpdatePO orderItemUpdateVO);

    @Mappings({})
    OrderCreateDTO convert(OrderCreatePO orderCreatePO);

    @Mappings({})
    List<OrderCreateDTO.OrderItem> convert(List<CartItemBO> cartItems);

    default OrderCreateDTO createOrderCreateDTO(Integer userId, Integer userAddressId, String remark, String ip,
                                                List<CartItemBO> cartItems, Integer couponCardId) {
        return new OrderCreateDTO()
                .setUserId(userId)
                .setUserAddressId(userAddressId)
                .setRemark(remark)
                .setIp(ip)
                .setOrderItems(this.convert(cartItems))
                .setCouponCardId(couponCardId);
    }

}
