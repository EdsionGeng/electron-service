package com.people.rent.pay;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users/transaction")
@Api("【用户】支付交易 API")
public class PayTransactionController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PayTransactionService payTransactionService;

//    @GetMapping("/get")
//    @ApiOperation("获得支付交易")
//    public CommonResult<PayTransactionBO> get(PayTransactionGetDTO payTransactionGetDTO) {
//        payTransactionGetDTO.setUserId(UserSecurityContextHolder.getContext().getUserId());
//        return CommonResult.success(payTransactionService.getTransaction(payTransactionGetDTO));
//    }
//
//    @PostMapping("/submit")
//    @ApiOperation("提交支付交易")
//    public CommonResult<PayTransactionSubmitBO> submit(HttpServletRequest request,
//                                                       PayTransactionSubmitDTO payTransactionSubmitDTO) {
//        payTransactionSubmitDTO.setCreateIp(HttpUtil.getIp(request));
//        // 提交支付提交
//        return CommonResult.success(payTransactionService.submitTransaction(payTransactionSubmitDTO));
//    }
//
//    @PostMapping(value = "pingxx_pay_success", consumes = MediaType.APPLICATION_JSON_VALUE)
////  @GetMapping(value = "pingxx_pay_success")
//    public String pingxxPaySuccess(HttpServletRequest request) throws IOException {
//        logger.info("[pingxxPaySuccess][被回调]");
//        // 读取 webhook
//        StringBuilder sb = new StringBuilder();
//        try (BufferedReader reader = request.getReader()) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//        }
////        JSONObject bodyObj = JSON.parseObject(sb.toString());
////        bodyObj.put("webhookId", bodyObj.remove("id"));
////        String body = bodyObj.toString();
//        payTransactionService.updateTransactionPaySuccess(PayChannelEnum.PINGXX.getId(), sb.toString());
//        return "success";
//    }

}
