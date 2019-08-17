package com.rent.model.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public final class AliPayConfig {
    @Value("${alipay.app.id}")
    private String aliPayAppId;
    @Value("${aliapy.app.private.key}")
    private String aliPayAppPrivateKey;
    @Value("${alipay.app.public.key}")
    private String aliPayAppPublicKey;
    @Value("${alipay.app.url}")
    private String aliPayUrl;

}
