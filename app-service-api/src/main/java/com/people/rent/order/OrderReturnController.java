package com.people.rent.order;

import com.people.rent.convert.OrderReturnConvert;
import com.people.rent.datadict.DataDictService;
import com.rent.model.CommonResult;
import com.rent.model.bo.DataDictBO;
import com.rent.model.bo.OrderReturnInfoBO;
import com.rent.model.constant.DictKeyConstants;
import com.rent.model.dto.OrderReturnApplyDTO;
import com.rent.model.po.OrderReturnApplyPO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/order_return")
public class OrderReturnController {

    @Autowired
    private OrderReturnService orderReturnService;

    @Autowired
    private DataDictService dataDictService;

    @GetMapping("reason")
    @ApiOperation("退货原因列表")
    public CommonResult<List<DataDictBO>> orderReturnReason() {
        return dataDictService.getDataDict(DictKeyConstants.ORDER_RETURN_REASON);
    }

    @PostMapping("apply")
    @ApiOperation("订单售后")
    public CommonResult orderReturnApply(@RequestBody OrderReturnApplyPO orderReturnApplyPO) {
        OrderReturnApplyDTO applyDTO = OrderReturnConvert.INSTANCE.convert(orderReturnApplyPO);
        return orderReturnService.orderReturnApply(applyDTO);
    }

    @GetMapping("info")
    @ApiOperation("订单售后详细")
    public CommonResult<OrderReturnInfoBO> orderApplyInfo(@RequestParam("orderId") Integer orderId) {
        CommonResult<OrderReturnInfoBO> commonResult = orderReturnService.orderApplyInfo(orderId);

        // 转换 字典值
        if (commonResult.isSuccess()) {
            CommonResult<DataDictBO> dataDictResult = dataDictService.getDataDict(
                    DictKeyConstants.ORDER_RETURN_SERVICE_TYPE,
                    commonResult.getData().getReturnInfo().getServiceType());
            if (dataDictResult.isSuccess()) {
                commonResult.getData().getReturnInfo().setServiceTypeText(dataDictResult.getData().getDisplayName());
            }
        }
        return commonResult;
    }
}