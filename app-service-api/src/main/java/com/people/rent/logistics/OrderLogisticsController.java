package com.people.rent.logistics;

import com.people.rent.datadict.DataDictService;
import com.rent.model.CommonResult;
import com.rent.model.bo.DataDictBO;
import com.rent.model.bo.OrderLogisticsInfoBO;
import com.rent.model.bo.OrderLogisticsInfoWithOrderBO;
import com.rent.model.constant.DictKeyConstants;
import com.rent.model.constant.OrderErrorCodeEnum;
import com.rent.util.utils.ServiceExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users/order_logistics")
@Api(description = "订单物流信息")
public class OrderLogisticsController {

    @Autowired
    private OrderLogisticsService orderLogisticsService;

    @Autowired
    private DataDictService dataDictService;

    @GetMapping("info")
    @ApiOperation("物流详细 - 物流通用")
    public CommonResult<OrderLogisticsInfoBO> logistics(@RequestParam("logisticsId") Integer logisticsId) {
        return orderLogisticsService.getLogisticsInfo(logisticsId);
    }

    @GetMapping("info_order")
    @ApiOperation("物流详细 - 返回订单所关联的所有物流信息(订单用的)")
    public CommonResult<OrderLogisticsInfoWithOrderBO> logisticsInfoWithOrder(@RequestParam("orderId") Integer orderId, HttpServletRequest request) {
        Integer userId = null;
        CommonResult<OrderLogisticsInfoWithOrderBO> commonResult = orderLogisticsService.getOrderLogisticsInfo(userId, orderId);
        if (commonResult.isSuccess()) {
            OrderLogisticsInfoWithOrderBO orderLogisticsInfoBO = commonResult.getData();
            List<OrderLogisticsInfoWithOrderBO.Logistics> logisticsList = orderLogisticsInfoBO.getLogistics();

            // 获取字典值
            Set<Integer> dictValues = logisticsList.stream().map(o -> o.getLogistics()).collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(dictValues)) {
                CommonResult<List<DataDictBO>> dictResult = dataDictService
                        .getDataDictList(DictKeyConstants.ORDER_LOGISTICS_COMPANY, dictValues);

                if (dictResult.isError()) {
                    // 错误情况
                    return ServiceExceptionUtil.error(OrderErrorCodeEnum.DICT_SERVER_INVOKING_FAIL.getCode());
                }

                // 转换结果字典值
                Map<String, DataDictBO> dataDictBOMap = dictResult.getData()
                        .stream().collect(Collectors.toMap(o -> o.getValue(), o -> o));

                logisticsList.stream().map(o -> {
                    String dicValue = o.getLogistics().toString();
                    if (dataDictBOMap.containsKey(dicValue)) {
                        o.setLogisticsText(dataDictBOMap.get(dicValue).getDisplayName());
                    }
                    return o;
                }).collect(Collectors.toList());
            }
        }
        return commonResult;
    }
}