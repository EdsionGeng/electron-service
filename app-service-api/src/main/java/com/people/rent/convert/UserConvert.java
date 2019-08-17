package com.people.rent.convert;

import com.rent.model.bo.UserBO;
import com.rent.model.dto.UserDto;
import com.rent.model.po.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper

public interface UserConvert {


    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);


    @Mappings({})
    UserBO convertUserToBo(User user);

    @Mappings({})
    User convertDtoToUser(UserDto userDto);

}
