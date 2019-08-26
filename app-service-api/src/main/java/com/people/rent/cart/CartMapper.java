package com.people.rent.cart;

import com.rent.model.dataobject.CartItemDO;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface CartMapper {
    CartItemDO selectById(@Param("id") Integer id);

    List<CartItemDO> selectByIds(@Param("ids") Collection<Integer> ids);

    CartItemDO selectByUserIdAndSkuIdAndStatus(@Param("userId") Integer userId,
                                               @Param("skuId") Integer skuId,
                                               @Param("status") Integer status);

    Integer selectQuantitySumByUserIdAndStatus(@Param("userId") Integer userId,
                                               @Param("status") Integer status);

    List<CartItemDO> selectByUserIdAndStatusAndSelected(@Param("userId") Integer userId,
                                                        @Param("status") Integer status,
                                                        @Param("selected") Boolean selected);
//
//    List<CartItemDO> selectListByTitleLike(@Param("title") String title,
//                                         @Param("offset") Integer offset,
//                                         @Param("limit") Integer limit);

//    Integer selectCountByTitleLike(@Param("title") String title);

    void insert(CartItemDO cartItemDO);

    int update(CartItemDO cartItemDO);

    int updateQuantity(@Param("id") Integer id,
                       @Param("quantityIncr") Integer quantityIncr);

    int updateListByUserIdAndSkuId(@Param("userId") Integer userId,
                                   @Param("skuIds") Collection<Integer> skuIds,
                                   @Param("selected") Boolean selected,
                                   @Param("status") Integer status);

}
