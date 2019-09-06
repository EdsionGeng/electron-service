package com.people.rent.product;

import com.rent.model.dataobject.ProductSpuDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ProductSpuMapper {

    // TODO 芋艿，需要捉摸下，怎么优化下。参数有点多
    List<ProductSpuDO> selectListByNameLikeOrderBySortAsc(@Param("name") String name,
                                                          @Param("cid") Integer cid,
                                                          @Param("visible") Boolean visible,
                                                          @Param("startSize") Integer offset,
                                                          @Param("pageSize") Integer limit);

    Integer selectCountByNameLike(@Param("name") String name,
                                  @Param("cid") Integer cid,
                                  @Param("visible") Boolean visible);
    ProductSpuDO selectById(Integer id);
}
