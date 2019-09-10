package com.people.rent.choose;

import com.rent.model.CommonResult;
import com.rent.model.TimeChooseBO;
import com.rent.model.bo.UserAddressBO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/user/time_choose/")
public class TimeChooseController {

    @Autowired
    private TimeChooseService timeChooseService;

    @GetMapping("list")
    @ApiOperation(value = "时间选择列表")
    public CommonResult<List<TimeChooseBO>> timeChooseList() {

        return timeChooseService.timeChooseList();
    }
}
