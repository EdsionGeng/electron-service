package com.people.rent.logistics;

import com.google.common.collect.Lists;
import com.people.rent.convert.OrderLogisticsConvert;
import com.people.rent.order.OrderItemMapper;
import com.people.rent.order.OrderMapper;
import com.rent.model.CommonResult;
import com.rent.model.bo.OrderLastLogisticsInfoBO;
import com.rent.model.bo.OrderLogisticsInfoBO;
import com.rent.model.bo.OrderLogisticsInfoWithOrderBO;
import com.rent.model.constant.DeletedStatusEnum;
import com.rent.model.constant.OrderErrorCodeEnum;
import com.rent.model.dataobject.OrderDO;
import com.rent.model.dataobject.OrderItemDO;
import com.rent.model.dataobject.OrderLogisticsDO;
import com.rent.model.dataobject.OrderLogisticsDetailDO;
import com.rent.util.utils.DateUtil;
import com.rent.util.utils.ServiceExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderLogisticsService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderLogisticsMapper orderLogisticsMapper;
    @Autowired
    private OrderLogisticsDetailMapper orderLogisticsDetailMapper;

    public CommonResult<OrderLogisticsInfoBO> getLogisticsInfo(Integer id) {
        OrderLogisticsDO orderLogisticsDO = orderLogisticsMapper.selectById(id);
        if (orderLogisticsDO == null) {
            return CommonResult.success(null);
        }

        List<OrderLogisticsDetailDO> orderLogisticsDetailDOList = orderLogisticsDetailMapper
                .selectByOrderLogisticsId(orderLogisticsDO.getId());

        // 转换数据结构
        List<OrderLogisticsInfoBO.LogisticsDetail> logisticsDetails
                = OrderLogisticsConvert.INSTANCE.convert(orderLogisticsDetailDOList);

        OrderLogisticsInfoBO orderLogisticsInfo2BO = OrderLogisticsConvert.INSTANCE.convert(orderLogisticsDO);
        orderLogisticsInfo2BO.setDetails(logisticsDetails);
        return CommonResult.success(orderLogisticsInfo2BO);
    }

    public CommonResult<OrderLastLogisticsInfoBO> getLastLogisticsInfo(Integer id) {
        OrderLogisticsDO orderLogisticsDO = orderLogisticsMapper.selectById(id);
        if (orderLogisticsDO == null) {
            return CommonResult.success(null);
        }

        OrderLogisticsDetailDO orderLastLogisticsDetailDO = orderLogisticsDetailMapper.selectLastByLogisticsId(id);

        // 转换数据结构
        OrderLastLogisticsInfoBO.LogisticsDetail lastLogisticsDetail
                = OrderLogisticsConvert.INSTANCE.convertLastLogisticsDetail(orderLastLogisticsDetailDO);

        OrderLastLogisticsInfoBO lastLogisticsInfoBO = OrderLogisticsConvert
                .INSTANCE.convertOrderLastLogisticsInfoBO(orderLogisticsDO);

        lastLogisticsInfoBO.setLastLogisticsDetail(lastLogisticsDetail);
        return CommonResult.success(lastLogisticsInfoBO);
    }


    public CommonResult<OrderLogisticsInfoWithOrderBO> getOrderLogisticsInfo(Integer userId, Integer orderId) {
        OrderDO orderDO = orderMapper.selectById(orderId);

        if (orderDO == null) {
            return ServiceExceptionUtil.error(OrderErrorCodeEnum.ORDER_NOT_EXISTENT.getCode());
        }

        if (!userId.equals(orderDO.getUserId())) {
            return ServiceExceptionUtil.error(OrderErrorCodeEnum.ORDER_NOT_USER_ORDER.getCode());
        }

        // 获取订单所发货的订单 id
        List<OrderItemDO> orderItemDOList = orderItemMapper.selectByDeletedAndOrderId(
                DeletedStatusEnum.DELETED_NO.getValue(), orderId);

        // 获取物流 信息
        Set<Integer> orderLogisticsIds = orderItemDOList.stream()
                .filter(o -> o.getOrderLogisticsId() != null)
                .map(o -> o.getOrderLogisticsId())
                .collect(Collectors.toSet());

        List<OrderLogisticsDO> orderLogisticsDOList = Collections.emptyList();
        List<OrderLogisticsDetailDO> orderLogisticsDetailDOList = Collections.emptyList();
        if (!CollectionUtils.isEmpty(orderLogisticsIds)) {
            orderLogisticsDOList = orderLogisticsMapper.selectByIds(orderLogisticsIds);
            orderLogisticsDetailDOList = orderLogisticsDetailMapper.selectByOrderLogisticsIds(orderLogisticsIds);
        }

        // 转换 return 的数据
        List<OrderLogisticsInfoWithOrderBO.Logistics> logistics
                = OrderLogisticsConvert.INSTANCE.convertLogistics(orderLogisticsDOList);

        List<OrderLogisticsInfoWithOrderBO.LogisticsDetail> logisticsDetails
                = OrderLogisticsConvert.INSTANCE.convertLogisticsDetail(orderLogisticsDetailDOList);

        logisticsDetails.stream().map(o -> {
            o.setLogisticsTimeText(DateUtil.format(o.getLogisticsTime(), "yyyy-MM-dd HH:mm"));
            return o;
        }).collect(Collectors.toList());

        Map<Integer, List<OrderLogisticsInfoWithOrderBO.LogisticsDetail>> logisticsDetailMultimap
                = logisticsDetails.stream().collect(
                Collectors.toMap(
                        o -> o.getOrderLogisticsId(),
                        item -> Lists.newArrayList(item),
                        (oldVal, newVal) -> {
                            oldVal.addAll(newVal);
                            return oldVal;
                        }
                )
        );

        logistics.stream().map(o -> {
            if (logisticsDetailMultimap.containsKey(o.getId())) {
                o.setDetails(logisticsDetailMultimap.get(o.getId()));
            }
            return o;
        }).collect(Collectors.toList());

        return CommonResult.success(
                new OrderLogisticsInfoWithOrderBO()
                        .setOrderId(orderId)
                        .setOrderNo(orderDO.getOrderNo())
                        .setLogistics(logistics)
        );
    }
}

