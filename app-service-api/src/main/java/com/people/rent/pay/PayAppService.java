package com.people.rent.pay;

import com.rent.model.constant.CommonStatusEnum;
import com.rent.model.constant.PayErrorCodeEnum;
import com.rent.model.dataobject.transaction.PayAppDO;
import com.rent.util.utils.ServiceExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayAppService {

    @Autowired
    private PayAppMapper payAppMapper;

    public PayAppDO validPayApp(String appId) {
        PayAppDO payAppDO = payAppMapper.selectById(appId);
        // 校验是否存在
        if (payAppDO == null) {
            throw ServiceExceptionUtil.exception(PayErrorCodeEnum.PAY_APP_NOT_FOUND.getCode());
        }
        // 校验是否禁用
        if (CommonStatusEnum.DISABLE.getValue().equals(payAppDO.getStatus())) {
            throw ServiceExceptionUtil.exception(PayErrorCodeEnum.PAY_APP_IS_DISABLE.getCode());
        }
        return payAppDO;
    }
}
