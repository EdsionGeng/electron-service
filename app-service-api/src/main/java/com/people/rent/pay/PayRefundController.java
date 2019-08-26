package com.people.rent.pay;


import com.rent.model.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@RestController
@RequestMapping("users/refund") // TODO 芋艿，理论来说，是用户无关的。这里先酱紫先~
@Slf4j
public class PayRefundController {

//    @Autowired
//    private PayRefundService payRefundService;
//
//    @PostMapping(value = "pingxx_refund_success", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public String pingxxRefundSuccess(HttpServletRequest request) throws IOException {
//        log.info("[pingxxRefundSuccess][被回调]");
//        // 读取 webhook
//        StringBuilder sb = new StringBuilder();
//        try (BufferedReader reader = request.getReader()) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//        }
//
//        CommonResult<Boolean> result = payRefundService.updateRefundSuccess(PayChannelEnum.PINGXX.getId(), sb.toString());
//        if (result.isError()) {
//            log.error("[pingxxRefundSuccess][message({}) result({})]", sb, result);
//            return "failure";
//        }
//        return "success";
//    }

}
