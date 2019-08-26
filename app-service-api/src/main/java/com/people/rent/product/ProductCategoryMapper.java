package com.people.rent.product;

import com.rent.model.dataobject.ProductCategoryDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductCategoryMapper {

    List<ProductCategoryDO> selectListByPidAndStatusOrderBySort(@Param("pid") Integer pid,
                                                                @Param("status") Integer status);
}
