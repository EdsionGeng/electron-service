package com.people.rent.address;

import com.people.rent.convert.UserAddressConvert;
import com.rent.model.CommonResult;
import com.rent.model.bo.UserAddressBO;
import com.rent.model.dto.UserAddressAddDTO;
import com.rent.model.dto.UserAddressUpdateDTO;
import com.rent.model.po.UserAddressAddPO;
import com.rent.model.po.UserAddressUpdatePO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户地址
 *
 * @author Sin
 * @time 2019-04-06 14:11
 */
@RestController
@RequestMapping("users/address")
@Api(value = "用户地址API")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @PostMapping("add")
    @ApiOperation(value = "用户地址-添加")
    public CommonResult addAddress(@Validated UserAddressAddPO userAddressAddPO,HttpServletRequest request) {
        Integer userId = null;
        UserAddressAddDTO userAddressAddDTO = UserAddressConvert.INSTANCE.convert(userAddressAddPO);
        userAddressAddDTO.setUserId(userId);
        return userAddressService.addAddress(userAddressAddDTO);
    }

    @PutMapping("update")
    @ApiOperation(value = "用户地址-更新")
    public CommonResult updateAddress(@Validated UserAddressUpdatePO userAddressUpdatePO,HttpServletRequest request) {
        Integer userId = null;
        UserAddressUpdateDTO userAddressUpdateDTO = UserAddressConvert.INSTANCE.convert(userAddressUpdatePO);
        userAddressUpdateDTO.setUserId(userId);
        return userAddressService.updateAddress(userAddressUpdateDTO);
    }

    @DeleteMapping("remove")
    @ApiOperation(value = "用户地址-删除")
    public CommonResult removeAddress(@RequestParam("id") Integer id,HttpServletRequest request) {
        Integer userId = null;
        return userAddressService.removeAddress(userId, id);
    }

    @GetMapping("list")
    @ApiOperation(value = "用户地址列表")
    public CommonResult<List<UserAddressBO>> addressList(HttpServletRequest request) {
        Integer userId = null;
        return userAddressService.addressList(userId);
    }

    @GetMapping("address")
    @ApiOperation(value = "获取地址")
    public CommonResult<UserAddressBO> getAddress(@RequestParam("id") Integer id,HttpServletRequest request) {
        Integer userId = null;
        return userAddressService.getAddress(userId, id);
    }

    @GetMapping("default_address")
    @ApiOperation(value = "获取默认地址")
    public CommonResult<UserAddressBO> getDefaultAddress(HttpServletRequest request) {
        Integer userId = null;
        return userAddressService.getDefaultAddress(userId);
    }
}
