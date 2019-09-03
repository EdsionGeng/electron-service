package com.people.rent.product;

import com.rent.model.dataobject.ProductSkuDO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface ProductSkuMapper {

    ProductSkuDO selectById(Integer id);

    List<ProductSkuDO> selectByIds(@Param("ids") Collection<Integer> ids);

    List<ProductSkuDO> selectListBySpuIdAndStatus(@Param("spuId") Integer spuId,
                                                  @Param("status") Integer status);

    void insertList(@Param("productSkuDOs") List<ProductSkuDO> productSkuDOs);

    int update(ProductSkuDO productSkuDO);

    int updateToDeleted(@Param("ids") List<Integer> ids);

}
