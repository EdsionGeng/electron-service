package com.people.rent.order;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class OrderController {

    @Autowired
    private OrderService orderService;


}
