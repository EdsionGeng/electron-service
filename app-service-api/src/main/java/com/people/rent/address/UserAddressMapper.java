package com.people.rent.address;


import com.rent.model.dataobject.UserAddressDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressMapper {

    int insert(UserAddressDO userAddressDO);

    int updateById(
            @Param("id") Integer id,
            @Param("userAddressDO") UserAddressDO userAddressDO
    );

    List<UserAddressDO> selectByUserIdAndDeleted(
            Integer deleted,
            Integer userId
    );

    UserAddressDO selectByUserIdAndId(
            Integer userId,
            Integer id
    );

    UserAddressDO selectHasDefault(
            Integer deleted,
            Integer userId,
            Integer hasDefault
    );
}
