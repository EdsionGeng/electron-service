package com.people.rent.pay;


import com.people.rent.convert.pay.PayNotifyConvert;
import com.rent.model.constant.PayNotifyType;
import com.rent.model.constant.PayTransactionNotifyStatusEnum;
import com.rent.model.dataobject.PayNotifyTaskDO;
import com.rent.model.dataobject.transaction.PayRefundDO;
import com.rent.model.dataobject.transaction.PayTransactionDO;
import com.rent.model.dataobject.transaction.PayTransactionExtensionDO;
import com.rent.util.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class PayNotifyService {


    @Autowired
    private PayNotifyTaskMapper payTransactionNotifyTaskMapper;

//    @Resource
//    private RocketMQTemplate rocketMQTemplate;

    @Deprecated // 参见 PayRefundSuccessConsumer 类的说明
    public void addRefundNotifyTask(PayRefundDO refund) {
        PayNotifyTaskDO payTransactionNotifyTask = this.createBasePayNotifyTaskDO(refund.getAppId(), refund.getNotifyUrl())
                .setType(PayNotifyType.REFUND.getValue());
        // 设置 Refund 属性
        payTransactionNotifyTask.setRefund(new PayNotifyTaskDO.Refund().setRefundId(refund.getId())
                .setTransactionId(refund.getTransactionId()).setOrderId(refund.getOrderId()));
        // 保存到数据库
        payTransactionNotifyTaskMapper.insert(payTransactionNotifyTask);
        // 发送 MQ 消息
       // sendNotifyMessage(payTransactionNotifyTask);
    }



    private PayNotifyTaskDO createBasePayNotifyTaskDO(String appId, String notifyUrl) {
        return new PayNotifyTaskDO()
                .setAppId(appId)
                .setStatus(PayTransactionNotifyStatusEnum.WAITING.getValue())
                .setNotifyTimes(0).setMaxNotifyTimes(PayNotifyTaskDO.NOTIFY_FREQUENCY.length + 1)
                .setNextNotifyTime(DateUtil.addDate(Calendar.SECOND, PayNotifyTaskDO.NOTIFY_FREQUENCY[0]))
                .setNotifyUrl(notifyUrl);
    }

//    public void sendNotifyMessage(PayNotifyTaskDO notifyTask) {
//        if (PayNotifyType.TRANSACTION.getValue().equals(notifyTask.getType())) {
//            rocketMQTemplate.convertAndSend(PayTransactionSuccessMessage.TOPIC,
//                    PayNotifyConvert.INSTANCE.convertTransaction(notifyTask));
//        } else if (PayNotifyType.REFUND.getValue().equals(notifyTask.getType())) {
//            rocketMQTemplate.convertAndSend(PayRefundSuccessMessage.TOPIC,
//                    PayNotifyConvert.INSTANCE.convertRefund(notifyTask));
//        } else {
//            throw new IllegalArgumentException(String.format("通知任务(%s) 无法发送通知消息", notifyTask.toString()));
//        }
//    }

}
