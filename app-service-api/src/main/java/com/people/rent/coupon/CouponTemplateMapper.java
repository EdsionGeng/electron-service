package com.people.rent.coupon;

import com.rent.model.dataobject.CouponTemplateDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CouponTemplateMapper {

    CouponTemplateDO selectById(@Param("id") Integer id);

    List<CouponTemplateDO> selectListByIds(@Param("ids") Collection<Integer> ids);

    List<CouponTemplateDO> selectListByPage(@Param("type") Integer type,
                                            @Param("title") String title,
                                            @Param("status") Integer status,
                                            @Param("preferentialType") Integer preferentialType,
                                            @Param("offset") Integer offset,
                                            @Param("limit") Integer limit);

    Integer selectCountByPage(@Param("type") Integer type,
                              @Param("title") String title,
                              @Param("status") Integer status,
                              @Param("preferentialType") Integer preferentialType);

    void insert(CouponTemplateDO couponTemplate);

    int update(CouponTemplateDO couponTemplate);

    int updateStatFetchNumIncr(@Param("id") Integer id);

}
