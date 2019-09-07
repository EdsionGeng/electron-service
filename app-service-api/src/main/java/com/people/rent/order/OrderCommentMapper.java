package com.people.rent.order;

import com.rent.model.bo.OrderCommentTimeOutBO;
import com.rent.model.dataobject.OrderCommentDO;
import com.rent.model.dto.reply.OrderCommentPageDTO;
import com.rent.model.dto.reply.OrderCommentStateInfoPageDTO;
import com.rent.model.dto.reply.OrderCommentTimeOutPageDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderCommentMapper {
    /**
     * 插入订单评论
     * @param orderCommentDO
     * @return
     */
    void insert(OrderCommentDO orderCommentDO);


    /**
     * 根据 sku id 查询评论总条数
     * @param productSkuId
     * @return
     */
    int selectCommentTotalCountByProductSkuId(@Param("productSkuId") Integer productSkuId);


    /**
     * 分页获取评论
     * @param orderCommentPageDTO
     * @return
     */
    List<OrderCommentDO> selectCommentPage(OrderCommentPageDTO orderCommentPageDTO);


    /**
     * 根据评论 id 查询评论详情
     * @param id
     * @return
     */
    OrderCommentDO selectCommentInfoByCommentId(@Param("id") Integer id);


    /**
     * 订单评论状态信息详情
     * @param orderCommentStateInfoPageDTO
     * @return
     */
    List<OrderCommentDO> selectOrderCommentStateInfoPage(OrderCommentStateInfoPageDTO orderCommentStateInfoPageDTO);


    /**
     * 订单评论状态总数
     * @param userId,commentState
     * @return
     */
    int selectOrderCommentStateInfoTotal(@Param("userId") Integer userId,
                                         @Param("commentState") Integer commentState);


    /**
     * 订单评论超时分页
     * @param orderCommentTimeOutPageDTO
     * @return
     */
    List<OrderCommentDO> selectOrderCommentTimeOutPage(@Param("commentTimeOut") OrderCommentTimeOutPageDTO orderCommentTimeOutPageDTO);

    /**
     * 批量更新订单评论状态
     * @param orderCommentTimeOutBOList
     * @param commentState
     */
    void updateBatchOrderCommentState(@Param("commentState") Integer commentState,
                                      @Param("list") List<OrderCommentTimeOutBO> orderCommentTimeOutBOList);

}
