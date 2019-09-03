package com.people.rent.convert;

import com.rent.model.bo.UserAddressBO;
import com.rent.model.dataobject.UserAddressDO;
import com.rent.model.dto.UserAddressAddDTO;
import com.rent.model.dto.UserAddressUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户地址 convert
 *
 * @author Sin
 * @time 2019-04-06 13:38
 */
@Mapper
public interface UserAddressConvert {

    UserAddressConvert INSTANCE = Mappers.getMapper(UserAddressConvert.class);

    @Mappings({})
    UserAddressDO convert(UserAddressAddDTO userAddressAddDTO);

    @Mappings({})
    UserAddressDO convert(UserAddressUpdateDTO userAddressUpdateDTO);

    @Mappings({})
    UserAddressBO convert(UserAddressDO userAddressDO);

    @Mappings({})
    List<UserAddressBO> convertUserAddressBOList(List<UserAddressDO> userAddressDOList);
}
