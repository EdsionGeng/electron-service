package com.people.rent.promotion;


import com.people.rent.convert.CouponCardConvert;
import com.people.rent.convert.CouponTemplateConvert;
import com.people.rent.coupon.CouponService;
import com.rent.model.CommonResult;
import com.rent.model.bo.CouponCardBO;
import com.rent.model.bo.CouponCardPageBO;
import com.rent.model.bo.CouponTemplateBO;
import com.rent.model.dto.CouponCardPageDTO;
import com.rent.model.vo.UsersCouponCardPageVO;
import com.rent.model.vo.UsersCouponCardVO;
import com.rent.model.vo.UsersCouponTemplateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users/coupon")
@Api("优惠劵（码）模块")
public class UsersCouponController {

    private CouponService couponService;

    // ========== 优惠劵（码）模板 ==========

    @GetMapping("/template/get")
    @ApiOperation(value = "优惠劵（码）模板信息")
    @ApiImplicitParam(name = "id", value = "优惠劵（码）模板编号", required = true, example = "10")
    public CommonResult<UsersCouponTemplateVO> templateGet(@RequestParam("id") Integer id) {
        CouponTemplateBO template = couponService.getCouponTemplate(id);
        return CommonResult.success(CouponTemplateConvert.USERS.convert2(template));
    }

    // ========== 优惠劵 ==========

    @GetMapping("/card/page")
    @ApiOperation(value = "优惠劵分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态", example = "参考 CouponCardStatusEnum 枚举"),
            @ApiImplicitParam(name = "pageNo", value = "页码，从 1 开始", example = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, example = "10"),
    })
    public CommonResult<UsersCouponCardPageVO> cardPage(@RequestParam(value = "status", required = false) Integer status,
                                                        @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        CouponCardPageBO result = couponService.getCouponCardPage(new CouponCardPageDTO()
                .setStatus(status).setUserId(null)
                .setPageNo(pageNo).setPageSize(pageSize));
        return CommonResult.success(CouponCardConvert.INSTANCE.convert2(result));
    }

    @PostMapping("/card/add")
    @ApiOperation(value = "领取优惠劵")
    @ApiImplicitParam(name = "templateId", value = "优惠劵（码）模板编号", required = true, example = "10")
    public CommonResult<UsersCouponCardVO> cardAdd(@RequestParam("templateId") Integer templateId) {
        CouponCardBO result = couponService.addCouponCard(null, templateId);
        return CommonResult.success(CouponCardConvert.INSTANCE.convert(result));
    }

    // ========== 优惠码 ==========


}
