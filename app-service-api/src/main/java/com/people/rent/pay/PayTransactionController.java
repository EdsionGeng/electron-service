package com.people.rent.pay;

import com.rent.model.CommonResult;
import com.rent.model.bo.PayTransactionBO;
import com.rent.model.bo.PayTransactionSubmitBO;
import com.rent.model.constant.PayChannelEnum;
import com.rent.model.dto.transaction.PayTransactionGetDTO;
import com.rent.model.dto.transaction.PayTransactionSubmitDTO;
import com.rent.util.utils.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@RestController
@RequestMapping("users/transaction")
@Api("【用户】支付交易 API")
public class PayTransactionController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PayTransactionService payTransactionService;

    @GetMapping("/get")
    @ApiOperation("检查支付交易是否成功")
    public CommonResult<PayTransactionBO> get(PayTransactionGetDTO payTransactionGetDTO) {
        payTransactionGetDTO.setUserId(null);
        return CommonResult.success(payTransactionService.getTransaction(payTransactionGetDTO));
    }

    @PostMapping("/submit")
    @ApiOperation("提交支付交易")
    public CommonResult<PayTransactionSubmitBO> submit(HttpServletRequest request,
                                                       PayTransactionSubmitDTO payTransactionSubmitDTO) {
        payTransactionSubmitDTO.setCreateIp(HttpUtil.getIp(request));
        // 提交支付提交
        return CommonResult.success(payTransactionService.submitTransaction(payTransactionSubmitDTO));
    }

    @PostMapping(value = "pingxx_pay_success", consumes = MediaType.APPLICATION_JSON_VALUE)
//  @GetMapping(value = "pingxx_pay_success")
    public String pingxxPaySuccess(HttpServletRequest request) throws IOException {
        logger.info("[pingxxPaySuccess][被回调]");
        // 读取 webhook
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
//        JSONObject bodyObj = JSON.parseObject(sb.toString());
//        bodyObj.put("webhookId", bodyObj.remove("id"));
//        String body = bodyObj.toString();
        payTransactionService.updateTransactionPaySuccess(PayChannelEnum.PINGXX.getId(), sb.toString());
        return "success";
    }

}
