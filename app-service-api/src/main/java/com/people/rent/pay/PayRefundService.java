package com.people.rent.pay;

import com.rent.model.CommonResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class PayRefundService {


    @Transactional
    public CommonResult<Boolean> updateRefundSuccess(Integer payChannel, String params) {
        // TODO 芋艿，记录回调日志
        // 解析传入的参数，成 TransactionSuccessBO 对象
        AbstractPaySDK paySDK = PaySDKFactory.getSDK(payChannel);
        CommonResult<RefundSuccessBO> paySuccessResult = paySDK.parseRefundSuccessParams(params);
        if (paySuccessResult.isError()) {
            return CommonResult.error(paySuccessResult);
        }
        // TODO 芋艿，先最严格的校验。即使调用方重复调用，实际哪个订单已经被重复回调的支付，也返回 false 。也没问题，因为实际已经回调成功了。
        // 1.1 查询 PayRefundDO
        PayRefundDO payRefund = payRefundMapper.selectByRefundCode(paySuccessResult.getData().getRefundCode());
        if (payRefund == null) {
            return ServiceExceptionUtil.error(PayErrorCodeEnum.PAY_REFUND_NOT_FOUND.getCode());
        }
        if (!PayRefundStatus.WAITING.getValue().equals(payRefund.getStatus())) { // 校验状态，必须是待支付
            return ServiceExceptionUtil.error(PayErrorCodeEnum.PAY_REFUND_STATUS_NOT_WAITING.getCode());
        }
        // 1.2 更新 PayRefundDO
        Integer status = paySuccessResult.getData().getSuccess() ? PayRefundStatus.SUCCESS.getValue() : PayRefundStatus.FAILURE.getValue();
        PayRefundDO updatePayRefundDO = new PayRefundDO()
                .setId(payRefund.getId())
                .setStatus(status)
                .setTradeNo(paySuccessResult.getData().getTradeNo())
                .setExtensionData(params);
        int updateCounts = payRefundMapper.update(updatePayRefundDO, PayRefundStatus.WAITING.getValue());
        if (updateCounts == 0) { // 校验状态，必须是待支付
            throw ServiceExceptionUtil.exception(PayErrorCodeEnum.PAY_REFUND_STATUS_NOT_WAITING.getCode());
        }
        // 2.1 判断 PayTransactionDO ，增加已退款金额
        PayTransactionDO payTransaction = payTransactionService.getTransaction(payRefund.getTransactionId());
        if (payTransaction == null) {
            return ServiceExceptionUtil.error(PayErrorCodeEnum.PAY_TRANSACTION_NOT_FOUND.getCode());
        }
        if (!PayTransactionStatusEnum.SUCCESS.getValue().equals(payTransaction.getStatus())) { // 校验状态，必须是已支付
            throw ServiceExceptionUtil.exception(PayErrorCodeEnum.PAY_TRANSACTION_STATUS_IS_NOT_SUCCESS.getCode());
        }
        if (payRefund.getPrice() + payTransaction.getRefundTotal() > payTransaction.getPrice()) {
            throw ServiceExceptionUtil.exception(PayErrorCodeEnum.PAY_REFUND_PRICE_EXCEED.getCode());
        }
        // 2.2 更新 PayTransactionDO
        updateCounts = payTransactionService.updateTransactionPriceTotalIncr(payRefund.getTransactionId(), payRefund.getPrice());
        if (updateCounts == 0) { // 保证不超退 TODO 这种类型，需要思考下。需要返回错误，但是又要保证事务回滚
            throw ServiceExceptionUtil.exception(PayErrorCodeEnum.PAY_REFUND_PRICE_EXCEED.getCode());
        }
        // 3 新增 PayNotifyTaskDO
        payNotifyService.addRefundNotifyTask(payRefund);
        // 返回结果
        return CommonResult.success(true);
    }
}
