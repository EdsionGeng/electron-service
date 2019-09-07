package com.rent.model.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 支付通知 App 的任务 DO
 *
 * 目前包括支付通知、退款通知。
 */
@Data
@Accessors(chain = true)
public class PayNotifyTaskDO extends DeletableDO {

    /**
     * 通知频率，单位为秒。
     *
     * 算上首次的通知，实际是一共 1 + 8 = 9 次。
     */
    public static final Integer[] NOTIFY_FREQUENCY = new Integer[]{
            15, 15, 30, 180,
            1800, 1800, 1800, 3600
    };

    /**
     * 编号，自增
     */
    private Integer id;
    /**
     * 应用编号
     */
    private String appId;
    /**
     * 类型
     *
     * @see //cn.iocoder.mall.pay.api.constant.PayNotifyType
     */
    private Integer type;
    /**
     * 通知状态
     *
     * @see //cn.iocoder.mall.pay.api.constant.PayTransactionNotifyStatusEnum
     */
    private Integer status;
    /**
     * 下一次通知时间
     */
    private Date nextNotifyTime;
    /**
     * 最后一次执行时间
     *
     * 这个字段，需要结合 {@link #nextNotifyTime} 一起使用。
     *
     * 1. 初始时，{@link //PayTransactionServiceImpl#updateTransactionPaySuccess(Integer, String)}
     *      nextNotifyTime 为当前时间 + 15 秒
     *      lastExecuteTime 为空
     *      并发送给 MQ ，执行执行
     *
     * 2. MQ 消费时，更新 lastExecuteTime 为当时时间
     *
     * 3. 定时任务，扫描 nextNotifyTime < lastExecuteTime 的任务
     *      nextNotifyTime 为当前时间 + N 秒。具体的 N ，由第几次通知决定
     *      lastExecuteTime 为当前时间
     */
    private Date lastExecuteTime;
    /**
     * 当前通知次数
     */
    private Integer notifyTimes;
    /**
     * 最大可通知次数
     */
    private Integer maxNotifyTimes;
    /**
     * 通知地址
     */
    private String notifyUrl;
    // TODO 芋艿，未来把 transaction 和 refund 优化成一个字段。现在为了方便。
    /**
     * 支付数据
     */
    private Transaction transaction;
    /**
     * 退款数据
     */
    private Refund refund;

    @Data
    @Accessors(chain = true)
    public static class Transaction {

        /**
         * 应用订单编号
         */
        private String orderId;
        /**
         * 交易编号
         *
         * {@link //PayTransactionDO#getId()}
         */
        private Integer transactionId;
        /**
         * 交易拓展编号
         *
         * {@link //PayTransactionExtensionDO#getId()}
         */
        private Integer transactionExtensionId;

    }

    @Data
    @Accessors(chain = true)
    public static class Refund {

        /**
         * 应用订单编号
         */
        private String orderId;
        /**
         * 交易编号
         *
         * {@link //PayTransactionDO#getId()}
         */
        private Integer transactionId;
        /**
         * 退款单编号
         */
        private Integer refundId;

    }

}
