package com.people.rent.pay;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "支付接口", description = "支付接口文档")

public class PayController {

    @Autowired
    private PayService payService;
}
