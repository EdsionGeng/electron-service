package com.people.rent.address;

import com.people.rent.convert.UserAddressConvert;
import com.rent.model.CommonResult;
import com.rent.model.bo.UserAddressBO;
import com.rent.model.constant.DeletedStatusEnum;
import com.rent.model.constant.UserAddressHasDefaultEnum;
import com.rent.model.constant.UserErrorCodeEnum;
import com.rent.model.dataobject.UserAddressDO;
import com.rent.model.dto.UserAddressAddDTO;
import com.rent.model.dto.UserAddressUpdateDTO;
import com.rent.util.utils.ServiceExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;


    @Transactional
    public CommonResult addAddress(UserAddressAddDTO userAddressAddDTO) {
        UserAddressDO userAddressDO = UserAddressConvert.INSTANCE.convert(userAddressAddDTO);
        userAddressDO.setCreateTime(new Date());
        userAddressDO.setDeleted(DeletedStatusEnum.DELETED_NO.getValue());

        // 检查是否设置为默认地址
        if (UserAddressHasDefaultEnum.DEFAULT_ADDRESS_YES.getValue() == userAddressAddDTO.getHasDefault()) {
            UserAddressDO defaultUserAddress = userAddressMapper.selectHasDefault(
                    DeletedStatusEnum.DELETED_NO.getValue(),
                    userAddressAddDTO.getUserId(), UserAddressHasDefaultEnum.DEFAULT_ADDRESS_YES.getValue());

            if (defaultUserAddress != null) {
                userAddressMapper.updateById(defaultUserAddress.getId(),
                        new UserAddressDO()
                                .setHasDefault(UserAddressHasDefaultEnum.DEFAULT_ADDRESS_NO.getValue())
                );
            }
        }

        int result = userAddressMapper.insert(userAddressDO);
        return CommonResult.success(result);
    }


    @Transactional
    public CommonResult updateAddress(UserAddressUpdateDTO userAddressAddDTO) {
        UserAddressDO userAddress = userAddressMapper
                .selectByUserIdAndId(userAddressAddDTO.getUserId(), userAddressAddDTO.getId());

        if (DeletedStatusEnum.DELETED_YES.getValue().equals(userAddress.getDeleted())) {
            return ServiceExceptionUtil.error(UserErrorCodeEnum.USER_ADDRESS_IS_DELETED.getCode());
        }

        if (userAddress == null) {
            return ServiceExceptionUtil.error(UserErrorCodeEnum.USER_ADDRESS_NOT_EXISTENT.getCode());
        }

        // 检查是否设置为默认地址
        if (UserAddressHasDefaultEnum.DEFAULT_ADDRESS_YES.getValue() == userAddressAddDTO.getHasDefault()) {
            UserAddressDO defaultUserAddress = userAddressMapper.selectHasDefault(
                    DeletedStatusEnum.DELETED_NO.getValue(),
                    userAddressAddDTO.getUserId(), UserAddressHasDefaultEnum.DEFAULT_ADDRESS_YES.getValue());

            if (defaultUserAddress != null && !userAddressAddDTO.getId().equals(defaultUserAddress.getId())) {
                userAddressMapper.updateById(defaultUserAddress.getId(),
                        new UserAddressDO()
                                .setHasDefault(UserAddressHasDefaultEnum.DEFAULT_ADDRESS_NO.getValue())
                );
            }
        }

        UserAddressDO defaultUserAddress = userAddressMapper.selectHasDefault(
                DeletedStatusEnum.DELETED_NO.getValue(),
                userAddressAddDTO.getUserId(), UserAddressHasDefaultEnum.DEFAULT_ADDRESS_YES.getValue());

        if (defaultUserAddress != null && !userAddressAddDTO.getId().equals(defaultUserAddress.getId())) {
            userAddressMapper.updateById(defaultUserAddress.getId(),
                    new UserAddressDO()
                            .setHasDefault(UserAddressHasDefaultEnum.DEFAULT_ADDRESS_NO.getValue())
            );
        }

        UserAddressDO userAddressDO = UserAddressConvert.INSTANCE.convert(userAddressAddDTO);
        userAddressDO.setUpdateTime(new Date());
        userAddressMapper.updateById(userAddressDO.getId(), userAddressDO);
        return CommonResult.success(null);
    }


    public CommonResult removeAddress(Integer userId, Integer addressId) {
        UserAddressDO userAddress = userAddressMapper.selectByUserIdAndId(userId, addressId);

        if (DeletedStatusEnum.DELETED_YES.getValue().equals(userAddress.getDeleted())) {
            // skip
            return CommonResult.success(null);
        }

        if (userAddress == null) {
            return ServiceExceptionUtil.error(UserErrorCodeEnum.USER_ADDRESS_NOT_EXISTENT.getCode());
        }

        userAddressMapper.updateById(
                addressId,
                (UserAddressDO) new UserAddressDO()
                        .setDeleted(DeletedStatusEnum.DELETED_YES.getValue())
        );
        return CommonResult.success(null);
    }


    public CommonResult<List<UserAddressBO>> addressList(Integer userId) {

        List<UserAddressDO> userAddressDOList = userAddressMapper
                .selectByUserIdAndDeleted(DeletedStatusEnum.DELETED_NO.getValue(), userId);

        List<UserAddressBO> userAddressBOList = UserAddressConvert
                .INSTANCE.convertUserAddressBOList(userAddressDOList);

        return CommonResult.success(userAddressBOList);
    }

    public CommonResult<UserAddressBO> getAddress(Integer userId, Integer id) {
        UserAddressDO userAddress = userAddressMapper.selectByUserIdAndId(userId, id);
        if (userAddress == null) {
            return ServiceExceptionUtil.error(UserErrorCodeEnum.USER_GET_ADDRESS_NOT_EXISTS.getCode());
        }

        if (DeletedStatusEnum.DELETED_YES.getValue().equals(userAddress.getDeleted())) {
            return ServiceExceptionUtil.error(UserErrorCodeEnum.USER_ADDRESS_IS_DELETED.getCode());
        }

        UserAddressBO userAddressBO = UserAddressConvert.INSTANCE.convert(userAddress);
        return CommonResult.success(userAddressBO);
    }

    public CommonResult<UserAddressBO> getDefaultAddress(Integer userId) {

        UserAddressDO defaultUserAddress = userAddressMapper.selectHasDefault(
                DeletedStatusEnum.DELETED_NO.getValue(),
                userId,
                UserAddressHasDefaultEnum.DEFAULT_ADDRESS_YES.getValue());

        return CommonResult.success(UserAddressConvert.INSTANCE.convert(defaultUserAddress));
    }
}
