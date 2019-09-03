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
    public CommonResult addAddress(@Validated UserAddressAddPO userAddressAddPO) {
        //Integer userId = UserSecurityContextHolder.getContext().getUserId();
        UserAddressAddDTO userAddressAddDTO = UserAddressConvert.INSTANCE.convert(userAddressAddPO);
        userAddressAddDTO.setUserId(userId);
        return userAddressService.addAddress(userAddressAddDTO);
    }

    @PutMapping("update")
    @ApiOperation(value = "用户地址-更新")
    public CommonResult updateAddress(@Validated UserAddressUpdatePO userAddressUpdatePO) {
        //Integer userId = UserSecurityContextHolder.getContext().getUserId();
        UserAddressUpdateDTO userAddressUpdateDTO = UserAddressConvert.INSTANCE.convert(userAddressUpdatePO);
        userAddressUpdateDTO.setUserId(userId);
        return userAddressService.updateAddress(userAddressUpdateDTO);
    }

    @DeleteMapping("remove")
    @ApiOperation(value = "用户地址-删除")
    public CommonResult removeAddress(@RequestParam("id") Integer id) {
        //Integer userId = UserSecurityContextHolder.getContext().getUserId();
        return userAddressService.removeAddress(userId, id);
    }

    @GetMapping("list")
    @ApiOperation(value = "用户地址列表")
    public CommonResult<List<UserAddressBO>> addressList() {
        //Integer userId = UserSecurityContextHolder.getContext().getUserId();
        return userAddressService.addressList(userId);
    }

    @GetMapping("address")
    @ApiOperation(value = "获取地址")
    public CommonResult<UserAddressBO> getAddress(@RequestParam("id") Integer id) {
        //Integer userId = UserSecurityContextHolder.getContext().getUserId();
        return userAddressService.getAddress(userId, id);
    }

    @GetMapping("default_address")
    @ApiOperation(value = "获取默认地址")
    public CommonResult<UserAddressBO> getDefaultAddress() {
        //Integer userId = UserSecurityContextHolder.getContext().getUserId();
        return userAddressService.getDefaultAddress(userId);
    }
}
