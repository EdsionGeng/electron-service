package com.people.rent.banner;


import com.people.rent.banner.BannerService;
import com.people.rent.convert.BannerConvert;
import com.rent.model.CommonResult;
import com.rent.model.bo.BannerBO;
import com.rent.model.constant.CommonStatusEnum;
import com.rent.model.vo.UsersBannerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("users/banner")
@Api("Banner 模块")
public class UsersBannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/list")
    @ApiOperation("获得所有 Banner 列表")
    public CommonResult<List<UsersBannerVO>> list() {
        // 查询 Banner 列表
        List<BannerBO> result = bannerService.getBannerListByStatus(CommonStatusEnum.ENABLE.getValue());
        // 排序，按照 sort 升序
        result.sort(Comparator.comparing(BannerBO::getSort));
        // 返回
        return CommonResult.success(BannerConvert.USERS.convertList(result));
    }

}
