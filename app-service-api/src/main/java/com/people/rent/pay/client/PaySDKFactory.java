package com.people.rent.pay.client;

import com.rent.model.constant.PayChannelEnum;

import java.util.HashMap;
import java.util.Map;

public class PaySDKFactory {

    private static Map<Integer, AbstractPaySDK> SDKS = new HashMap<>();

    static {
      //  SDKS.put(PayChannelEnum.PINGXX.getId(), new PingxxPaySDK());
    }

    public static AbstractPaySDK getSDK(Integer payChannel) {
        AbstractPaySDK sdk = SDKS.get(payChannel);
        if (sdk == null) {
            throw new NullPointerException("找不到合适的 PaySDK ：" + payChannel);
        }
        return sdk;
    }

}