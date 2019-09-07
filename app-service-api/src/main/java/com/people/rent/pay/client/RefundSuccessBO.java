package com.people.rent.pay.client;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 交易退款成功结果
 */
@Data
@Accessors(chain = true)
public class RefundSuccessBO {

    /**
     * 生成传输给第三方的订单号
     *
     * 唯一索引
     */
    private String refundCode;
    /**
     * 第三方的流水号
     */
    private String tradeNo;
    /**
     * 第三方退款成功的时间
     */
    private Date refundTime;
    /**
     * 是否成功
     */
    private Boolean success;

}
