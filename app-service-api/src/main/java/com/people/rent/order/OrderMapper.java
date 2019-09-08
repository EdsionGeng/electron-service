package com.people.rent.order;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.model.dataobject.OrderDO;
import com.rent.model.dto.OrderQueryDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper extends BaseMapper<OrderDO> {

    /**
     * 更新 - 根据 id 更新
     *
     * @param orderDO
     * @return
     */
    int updateById(OrderDO orderDO);

    int updateByIdAndStatus(@Param("id") Integer id,
                            @Param("status") Integer status,
                            @Param("updateObj") OrderDO updateObj);



    /**
     * 查询 - 根据id 查询
     *
     * @param id
     * @return
     */
    OrderDO selectById(
            @Param("id") Integer id
    );

    /**
     * 查询 - 后台分页page
     *
     * @param orderQueryDTO
     * @return
     */
    int selectPageCount(OrderQueryDTO orderQueryDTO);

    /**
     * 查询 - 后台分页page
     *
     * @param orderQueryDTO
     * @return
     */
    List<OrderDO> selectPage(OrderQueryDTO orderQueryDTO);
}
