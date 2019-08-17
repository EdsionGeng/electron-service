package com.people.rent.user;


import com.people.rent.convert.UserConvert;
import com.rent.model.bo.UserBO;
import com.rent.model.dto.UserDto;
import com.rent.model.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public UserBO queryUserIsExist(String alipayUserId) {

        User user = this.userMapper.queryUserByAliId(alipayUserId);

        UserBO userBO = UserConvert.INSTANCE.convertUserToBo(user);

        return userBO;

    }

    public void registUser(UserDto userDto) {

        User user = UserConvert.INSTANCE.convertDtoToUser(userDto);

        try {
            this.userMapper.registUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("regist user failed:::{}",user.toString());
        }

    }
}
