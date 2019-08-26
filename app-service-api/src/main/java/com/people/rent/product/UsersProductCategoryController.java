package com.people.rent.product;


import com.people.rent.convert.ProductCategoryConvert;
import com.rent.model.CommonResult;
import com.rent.model.bo.ProductCategoryBO;
import com.rent.model.vo.UsersProductCategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users/category")
@Api("商品分类")
public class UsersProductCategoryController {


    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    @ApiOperation("获得指定编号下的子分类的数组")
    @ApiImplicitParam(name = "pid", value = "指定分类编号", required = true, example = "0")
    public CommonResult<List<UsersProductCategoryVO>> list(@RequestParam("pid") Integer pid) {
        List<ProductCategoryBO> result = productCategoryService.getListByPid(pid);
        return CommonResult.success(ProductCategoryConvert.Users.INSTANCE.convertToVO(result));
    }

}
