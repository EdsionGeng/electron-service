package com.people.rent.banner;

import com.rent.model.dataobject.BannerDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerMapper {

    List<BannerDO> selectListByStatus(@Param("status") Integer status);
}
