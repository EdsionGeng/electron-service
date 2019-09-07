package com.people.rent.order;

import com.people.rent.convert.OrderReturnConvert;
import com.people.rent.datadict.DataDictService;
import com.people.rent.logistics.OrderLogisticsService;
import com.people.rent.pay.PayRefundService;
import com.rent.model.CommonResult;
import com.rent.model.bo.DataDictBO;
import com.rent.model.bo.OrderLastLogisticsInfoBO;
import com.rent.model.bo.OrderReturnInfoBO;
import com.rent.model.bo.OrderReturnListBO;
import com.rent.model.constant.*;
import com.rent.model.dataobject.OrderDO;
import com.rent.model.dataobject.OrderItemDO;
import com.rent.model.dataobject.OrderReturnDO;
import com.rent.model.dto.OrderReturnApplyDTO;
import com.rent.model.dto.OrderReturnQueryDTO;
import com.rent.model.dto.transaction.PayRefundSubmitDTO;
import com.rent.util.utils.ServiceExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderReturnService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderReturnMapper orderReturnMapper;

    @Autowired
    private OrderLogisticsService orderLogisticsService;
    @Autowired
    private PayRefundService payRefundService;
    @Autowired
    private DataDictService dataDictService;



    public CommonResult orderReturnApply(OrderReturnApplyDTO orderReturnDTO) {
        OrderDO checkOrder = orderMapper.selectById(orderReturnDTO.getOrderId());

        // 检查订单是否 存在
        if (checkOrder == null) {
            return ServiceExceptionUtil.error(OrderErrorCodeEnum.ORDER_NOT_EXISTENT.getCode());
        }

        // 转换 DO
        OrderReturnDO orderReturnDO = OrderReturnConvert.INSTANCE.convert(orderReturnDTO);
        orderReturnDO
                .setOrderId(checkOrder.getId())
                // TODO: 2019-04-27 Sin 服务号生成规则
                .setServiceNumber(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16))
                .setOrderNo(checkOrder.getOrderNo())
                .setStatus(OrderReturnStatusEnum.RETURN_APPLICATION.getValue())
                .setCreateTime(new Date());

        // 保存申请信息
        orderReturnMapper.insert(orderReturnDO);
        return CommonResult.success(null);
    }


    public String updateRefundSuccess(String orderId, Integer refundPrice) {
        return "success";
    }


    public CommonResult<OrderReturnInfoBO> orderApplyInfo(Integer orderId) {

        // 检查订单是否退货
        OrderReturnDO orderReturnDO = orderReturnMapper.selectByOrderId(orderId);
        if (orderReturnDO == null) {
            return ServiceExceptionUtil.error(OrderErrorCodeEnum.ORDER_RETURN_NO_RETURN_APPLY.getCode());
        }

        List<OrderItemDO> orderItemDOList = orderItemMapper
                .selectByDeletedAndOrderId(DeletedStatusEnum.DELETED_NO.getValue(), orderId);

        // 订单不存在
        if (CollectionUtils.isEmpty(orderItemDOList)) {
            return ServiceExceptionUtil.error(OrderErrorCodeEnum.ORDER_NOT_EXISTENT.getCode());
        }

        // 转换 returnInfo
        OrderReturnInfoBO.ReturnInfo returnInfo = OrderReturnConvert.INSTANCE.convert(orderReturnDO);
        List<OrderReturnInfoBO.OrderItem> itemList = OrderReturnConvert.INSTANCE.convert(orderItemDOList);

        // 物流信息
        CommonResult<OrderLastLogisticsInfoBO> lastLogisticsCommonResult = orderLogisticsService
                .getLastLogisticsInfo(orderReturnDO.getOrderLogisticsId());

        if (lastLogisticsCommonResult.isError()) {
            return ServiceExceptionUtil.error(OrderErrorCodeEnum.ORDER_LOGISTICS_INVOKING_FAIL.getCode());
        }

        OrderLastLogisticsInfoBO lastLogisticsInfoBO = lastLogisticsCommonResult.getData();
        OrderReturnInfoBO orderReturnInfoBO = new OrderReturnInfoBO()
                .setOrderItems(itemList)
                .setReturnInfo(returnInfo)
                .setLastLogisticsInfo(lastLogisticsInfoBO);

        return CommonResult.success(orderReturnInfoBO);
    }


    public CommonResult<OrderReturnListBO> orderReturnList(OrderReturnQueryDTO queryDTO) {
        int totalCount = orderReturnMapper.selectListCount(queryDTO);
        if (totalCount <= 0) {
            return CommonResult.success(
                    new OrderReturnListBO()
                            .setData(Collections.EMPTY_LIST)
                            .setIndex(queryDTO.getIndex())
                            .setPageSize(queryDTO.getPageSize())
                            .setTotalCount(0)
            );
        }
        List<OrderReturnDO> orderReturnDOList = orderReturnMapper.selectList(queryDTO);
        List<OrderReturnListBO.OrderReturn> orderReturnListBOList
                = OrderReturnConvert.INSTANCE.convertListBO(orderReturnDOList);

        return CommonResult.success(
                new OrderReturnListBO()
                        .setData(orderReturnListBOList)
                        .setIndex(queryDTO.getIndex())
                        .setPageSize(queryDTO.getPageSize())
                        .setTotalCount(totalCount)
        );
    }

    public CommonResult orderReturnAgree(Integer id) {
        OrderReturnDO orderReturnDO = orderReturnMapper.selectById(id);
        if (orderReturnDO == null) {
            return ServiceExceptionUtil
                    .error(OrderErrorCodeEnum.ORDER_RETURN_NOT_EXISTENT.getCode());
        }

        orderReturnMapper.updateById(
                new OrderReturnDO()
                        .setId(id)
                        .setApprovalTime(new Date())
                        .setStatus(OrderReturnStatusEnum.APPLICATION_SUCCESSFUL.getValue())
        );
        return CommonResult.success(null);
    }

    public CommonResult orderReturnRefuse(Integer id) {
        OrderReturnDO orderReturnDO = orderReturnMapper.selectById(id);
        if (orderReturnDO == null) {
            return ServiceExceptionUtil.error(OrderErrorCodeEnum.ORDER_RETURN_NOT_EXISTENT.getCode());
        }

        orderReturnMapper.updateById(
                new OrderReturnDO()
                        .setId(id)
                        .setRefuseTime(new Date())
                        .setStatus(OrderReturnStatusEnum.APPLICATION_FAIL.getValue())
        );
        return CommonResult.success(null);
    }

    public CommonResult confirmReceipt(Integer id) {
        OrderReturnDO orderReturnDO = orderReturnMapper.selectById(id);
        if (orderReturnDO == null) {
            return ServiceExceptionUtil.error(OrderErrorCodeEnum.ORDER_RETURN_NOT_EXISTENT.getCode());
        }

        orderReturnMapper.updateById(
                new OrderReturnDO()
                        .setId(id)
                        .setReceiverTime(new Date())
                        .setStatus(OrderReturnStatusEnum.ORDER_RECEIPT.getValue())
        );
        return CommonResult.success(null);
    }

    @Transactional
    public CommonResult refund(Integer id, String ip) {
        OrderReturnDO orderReturnDO = orderReturnMapper.selectById(id);
        if (orderReturnDO == null) {
            return ServiceExceptionUtil.error(OrderErrorCodeEnum.ORDER_RETURN_NOT_EXISTENT.getCode());
        }

        // TODO: 2019/5/8 sin, 发送 MQ 消息，申请退货成功!
        // TODO: 2019/5/8 sin 退款：支付系统退款
        // TODO: 2019/5/8 sin 退货+退款：退回商品签收后，支付系统退款
        // TODO: 2019/5/8 sin 事务一致性 [重要]


        CommonResult<DataDictBO> dataDictResult = dataDictService
                .getDataDict(DictKeyConstants.ORDER_RETURN_REASON, orderReturnDO.getReason());

        if (dataDictResult.isError()) {
            return ServiceExceptionUtil.error(OrderErrorCodeEnum.DICT_SERVER_INVOKING_FAIL.getCode());
        }

        // 支付退款
        String orderDescription = dataDictResult.getData()
                .getDisplayName() + "(" + orderReturnDO.getDescribe() + ")";

        CommonResult payResult = payRefundService.submitRefund(
                new PayRefundSubmitDTO()
                        .setAppId(PayAppId.APP_ID_SHOP_ORDER)
                        .setOrderId(String.valueOf(orderReturnDO.getOrderId()))
                        .setPrice(orderReturnDO.getRefundPrice())
                        .setOrderDescription(orderDescription)
                        .setCreateIp(ip)
        );

        if (!payResult.isSuccess()) {
            return ServiceExceptionUtil.error(OrderErrorCodeEnum.ORDER_RETURN_REFUND_FAILED.getCode());
        }

        // 更新 订单退货 信息
        orderReturnMapper.updateById(
                new OrderReturnDO()
                        .setId(id)
                        .setClosingTime(new Date())
                        .setStatus(OrderReturnStatusEnum.RETURN_SUCCESS.getValue())
        );

        // 更新订单
        orderMapper.updateById(
                new OrderDO()
                        .setId(orderReturnDO.getOrderId())
                        .setClosingTime(new Date())
                        .setStatus(OrderStatusEnum.COMPLETED.getValue())
        );

        // 更新订单
        orderItemMapper.updateByOrderId(
                orderReturnDO.getOrderId(),
                new OrderItemDO()
                        .setClosingTime(new Date())
                        .setStatus(OrderStatusEnum.COMPLETED.getValue())
        );
        return CommonResult.success(null);
    }
}
