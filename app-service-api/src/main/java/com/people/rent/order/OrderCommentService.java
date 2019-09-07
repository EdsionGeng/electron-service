package com.people.rent.order;

import com.people.rent.convert.OrderCommentConvert;
import com.rent.model.bo.*;
import com.rent.model.constant.OrderCommentStatusEnum;
import com.rent.model.constant.OrderReplyUserTypeEnum;
import com.rent.model.dataobject.OrderCommentDO;
import com.rent.model.dataobject.OrderCommentReplyDO;
import com.rent.model.dto.reply.OrderCommentCreateDTO;
import com.rent.model.dto.reply.OrderCommentPageDTO;
import com.rent.model.dto.reply.OrderCommentStateInfoPageDTO;
import com.rent.model.dto.reply.OrderCommentTimeOutPageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderCommentService {
    @Autowired
    private OrderCommentMapper orderCommentMapper;

    @Autowired
    private OrderCommentReplyMapper orderCommentReplayMapper;


    public OrderCommentCreateBO createOrderComment(OrderCommentCreateDTO orderCommentCreateDTO) {
        OrderCommentDO orderCommentDO= OrderCommentConvert.INSTANCE.convertOrderCommentDO(orderCommentCreateDTO);
        orderCommentDO.setCreateTime(new Date());
        orderCommentMapper.insert(orderCommentDO);
        return OrderCommentConvert.INSTANCE.convertOrderCommentCreateBO(orderCommentDO);
    }


    public OrderCommentPageBO getOrderCommentPage(OrderCommentPageDTO orderCommentPageDTO) {
        OrderCommentPageBO orderCommentPageBO=new OrderCommentPageBO();
        //分页内容
        List<OrderCommentDO> orderCommentDOList=orderCommentMapper.selectCommentPage(orderCommentPageDTO);
        //分页评论的 id
        List<Integer> commentIds=orderCommentDOList.stream().map(x->x.getId()).collect(Collectors.toList());
        //获取商家最新的评论回复
        List<OrderCommentReplyDO> orderCommentReplyDOList=orderCommentReplayMapper.selectCommentNewMerchantReplyByCommentIds(commentIds,
                OrderReplyUserTypeEnum.MERCHANT.getValue());
        //评论组装
        List<OrderCommentPageBO.OrderCommentItem> orderCommentItemList=orderCommentDOList.stream()
                .flatMap(x->orderCommentReplyDOList.stream()
                        .filter(y->x.getId()==y.getCommentId())
                        .map(y->new OrderCommentPageBO.OrderCommentItem(x.getId(),x.getUserAvatar(),x.getUserNickName(),x.getStar(),
                                x.getCommentContent(),x.getCommentPics(),x.getReplayCount(),x.getLikeCount(),x.getCreateTime(),y.getReplyContent()))
                ).collect(Collectors.toList());
        //总数
        int totalCount=orderCommentMapper.selectCommentTotalCountByProductSkuId(orderCommentPageDTO.getProductSkuId());
        orderCommentPageBO.setOrderCommentItems(orderCommentItemList);
        orderCommentPageBO.setTotal(totalCount);
        return orderCommentPageBO;
    }



    public OrderCommentInfoBO getOrderCommentInfo(Integer commentId) {
        //查询评论详情
        OrderCommentDO orderCommentDO=orderCommentMapper.selectCommentInfoByCommentId(commentId);
        return OrderCommentConvert.INSTANCE.convertOrderCommentInfoBO(orderCommentDO);
    }


    public OrderCommentStateInfoPageBO getOrderCommentStateInfoPage(OrderCommentStateInfoPageDTO orderCommentStateInfoPageDTO) {
        OrderCommentStateInfoPageBO orderCommentStateInfoPageBO=new OrderCommentStateInfoPageBO();
        //总数
        int total=orderCommentMapper.selectOrderCommentStateInfoTotal(orderCommentStateInfoPageDTO.getUserId(),
                orderCommentStateInfoPageDTO.getCommentState());
        //查询评论状态详情
        List<OrderCommentDO> orderCommentDOList=orderCommentMapper.selectOrderCommentStateInfoPage(orderCommentStateInfoPageDTO);
        //转化评论状态详情
        List<OrderCommentStateInfoPageBO.OrderCommentStateInfoItem> orderCommentStateInfoItemList=
                OrderCommentConvert.INSTANCE.convertOrderCommentStateInfoItems(orderCommentDOList);
        orderCommentStateInfoPageBO.setTotal(total);
        orderCommentStateInfoPageBO.setOrderCommentStateInfoItems(orderCommentStateInfoItemList);
        return orderCommentStateInfoPageBO;
    }


    public List<OrderCommentTimeOutBO> getOrderCommentTimeOutPage(OrderCommentTimeOutPageDTO orderCommentTimeOutPageDTO) {
        List<OrderCommentDO> orderCommentDOList=orderCommentMapper.selectOrderCommentTimeOutPage(orderCommentTimeOutPageDTO);
        return OrderCommentConvert.INSTANCE.convertOrderCommentTimeOutBOList(orderCommentDOList);
    }


    public void updateBatchOrderCommentState(List<OrderCommentTimeOutBO> orderCommentTimeOutBOList) {
        orderCommentMapper.updateBatchOrderCommentState(OrderCommentStatusEnum.SUCCESS_COMMENT.getValue(),orderCommentTimeOutBOList);
    }
}
