package com.people.rent.user;


import com.rent.model.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {


    User queryUserByAliId(@Param("id")String alipayUserId);


    void registUser(User user);
}
